# Multiple application modules in one Android project with shared common modules

This project demonstrates how two different application can share different modules and be listed into a single project. 

To get started with this project some prior knowledge to [Clean Architecture](https://fernandocejas.com/2018/05/07/architecting-android-reloaded) is required as the application architecture is based on this principle. I have linked some great resource if you aren't familiar with it. 

## Application feature modules

We can create multiple application modules in one project, so that:

- every application module represents a single application, with shares a bunch of logic and views with each other;
- each team/developer only compiles the code they’re currently working on. Even if other application has been changed, they're simply not in your compilation pipeline;
- the teams are encouraged to work only on the feature(module)/app they're assigned to. The modules can even be placed in different repositories with different access rights, so you can be sure that a junior developer from another team will not accidentally add a few bugs into your module.

# Project Implementation 

This project has two separate application student and teacher. While both the application only provides a simple login/register for their respective types, this project is more of a demonstration of how you would go about implementing such an app, that would share most of the UI and business logics without having much code duplication. 
The logics and modules can be further separated, but thats a whole other topic. 

## Structure of the application 

### Teacher/Student module

We’ll start here as it’s likely a module that you’ve already encountered or used at some point during your development career. And if not, then it’s the most important place to start! These are essentially the main module of your application, and use the application plugin in the modules build.gradle file to signify this:

> apply plugin: 'com.android.application'

One of these module will still be the installed module. These module in turn depends on the others i.e. `core`, `ui`, `businesslogic` to provide various functions and UI for the application. A brief description can be found for each of the modules. 

### Core module
You can essentially use these module to separate out related areas of your project so that they are decoupled from the main module of your project. This consists of the core function that are mostly used in any android application and thus doesn't have any application specific logic. Basically it consists of some platform specific helpers, extensions, etc. This is the module that I would use in any or every android application that follows this design application. 

### Business logic module  
This is where the bulk of the logic to make the application functional lies. Basically if you have gone through [Clean Architecture](https://fernandocejas.com/2018/05/07/architecting-android-reloaded) this is somewhat a combination of Domain and Data Layers. And yes we can separate them as such, but this is a relatively simple project so I didn't find it necessary. 

### UI module
This layer is the implementation of the components from your design guide documents. Weather you have custom components with specific behaviours, styles and such, this is the place you would put it.  

## General architecture

The overall architecture consists of three layers:

1. **Data layer**: where the data is fetched from some source (i.e. network, database, file system). This is where the *Repository* classes reside
2. **Domain layer**: where data from the data layer is brought into a form suitable for the app - this is mainly done by single-purpose *Interactors* which often fetch data from multiple Repositories
3. **View layer**: where domain data is presented to the user (after optional conversion by means of the ViewModel), here we find *ViewDataProvider*s, *ViewModel*s and our usual Android suspects like Fragments and Activities.

Most dependencies are resolved by means of the dagger DI framework (don't worry, the plugin generates most of the modules and components for you). Usually data flows by means of RxJava 2 streams between the layers.

### Data layer

The data layer is the central source for data not originating from within the app. It is our interface to the outside world.

Repositories are the main components of the data layer. They take care of fetching and sending data from/to outside data sources.

As the data layer is the bottom-most layer repositories don't know anything about the layers above. They are usually contacted directly by Interactors from the domain layers.

Instead of sending raw source data to the interactors, Repositories *map* data to *Domain Objects*. This helps at abstracting the actual sources from the rest of the app and allows for relatively simple replacements or changes of sources.

A simple repository implementation might look like this. 

```kotlin 
@Singleton
class FirestoreStudentRepository
@Inject constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val imageRepository: ImageRepository

) : StudentRepository {

    private val studentsCollection by lazy {
        FirebaseFirestore.getInstance().collection(
                DatabaseName.TABLE_STUDENTS
        )
    }

    override fun createStudent(student: StudentModel): Completable {
        val document = studentsCollection.document()
        return authRepository.createAuth(
                student.phoneNo.fullNumber, student.school, student.password
        )
                .flatMapCompletable { userId ->
                    uploadImage(student, userId)
                            .flatMapCompletable { studentDto ->
                                userRepository.createUser(userId, studentDto)
                                        .andThen {
                                            document.set(studentDto.transform(userId, document.id))
                                            it.onComplete()
                                        }
                            }
                }
    }

    private fun uploadImage(student: StudentModel, userId: String): Single<StudentDto> {
        return imageRepository.uploadProfileImage(student.profilePicture, userId).map {
            val studentDto = student.transform()
            studentDto.profile_picture = it
            studentDto
        }
    }

}
```

### Domain Layer

The domain layer is where our business rules are defined - usually in the form of Interactors.

Interactors usually fetch data from multiple Repositories, merge that data and return it as more complex Domain Models. Often they also implement business rules like: "only return data here when the user is logged in, otherwise throw an error" or "only enable the feature when the user has completed an IAP".

Even if an Interactor just fetches data straight from one Repository don't feel tempted to skip implementation of that Interactor and directly access the repository from the above View layer. You will loose the benefit of having all business rules defined in an encapsulated way.

Interactors are usually quite tightly scoped and thus very reusable. It is not uncommon for ViewDataProviders to access multiple interactors to get the data needed by its ViewModel.

An Interactor can be as simple as the following, but it can get much more complex when user handling, dynamic feature flags and merging of data from different sources (usually repositories) are involved:

```kotlin
class SignUpStudentInteractor
@Inject constructor(
        private val repository: StudentRepository
) {
    operator fun invoke(
            student: StudentModel
    ): Completable {
        return repository.createStudent(student)
    }
}
```

### View layer

The View layer is the topmost layer in our Architecture. As such it is the user facing layer. The View layer is where most of our features are sitting.

The interface between the View layer and the Domain layer is defined by ViewModels. As such they remain in place even across configuration changes. However, their sole purpose is to fetch (and possibly post) data, not to handle any UI logic.

The ViewModel subscribes to an Observable supplied by the ViewDataProvider to receive data from the lower layers.

This is also where you would implement refresh handling and where data from multiple Interactors is merged. Thus the ViewModels decides *which streams are provided to the View depending on what the ViewModel asks for*.

A simple ViewModels might look like this:
 
```kotlin
class LoginViewModel(
        val loginUserInteractor: LoginUserInteractor,
        val getSchoolInteractor: GetSchoolsInteractor,
        val getCurrentUserInteractor: GetCurrentUserInteractor,
        val logoutUserInteractor: LogoutUserInteractor,
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {

    val phoneNo = MutableLiveData<PhoneModel>()
    val password = MutableLiveData<String>()
    val school = MutableLiveData<String>()

    private val schoolsSubject by lazy {
        PublishSubject.create<Boolean>()
    }

    val schools = schoolsSubject
            .startWith(true)
            .flatMapSingle { getSchoolInteractor() }
            .replay()
            .autoConnect(1)

    fun loginUser(): Completable {
        return loginUserInteractor(
                phoneNo.value!!.fullNumber,
                school.value ?: "",
                password.value ?: ""
        )
    }

    fun getCurrentUser(): Single<BaseUser> {
        return getCurrentUserInteractor()
    }

    fun logout(): Completable {
        return logoutUserInteractor()
    }

}
```

The last part of the chain is the Fragment that injects the ViewModel. This is likely the most boring part.

The Fragment subscribes to the data stream provided by the ViewModel (usually in `onCreate()`). If the Fragment hosts a RecyclerView it might then forward the data to the Adapter once it arrives.

# Future Extensions

## Add ViewDataProvider to provide data to ViewModels instead of accessing them directly 

The interface between the View layer and the Domain layer can be defined by ViewDataProviders.

ViewDataProviders are regular Android ViewModels. As such they remain in place even across configuration changes. However, their sole purpose is to fetch (and possibly post) data, not to handle any UI logic.

This is done by the ViewModel (again, not the Android ViewModel!) in collaboration with the Fragment.

Every ViewModel has access to a ViewDataProvider. The ViewModel subscribes to an Observable supplied by the ViewDataProvider to receive data from the lower layers.

The ViewDataProvider is also where you would implement refresh handling and where data from multiple Interactors is merged. Thus the ViewDataProvider decides *which streams are provided to the ViewModel depending on what the ViewModel asks for*.

When the ViewModel receives data from the ViewDataProvider it may map it again to data objects suitable for whatever UI the data shall be presented in (e.g. by applying filters). The mapped objects can in turn be ViewModels (e.g. when presenting data in a RecyclerView, we usually suffix those with _Item_) or just data classes. In both cases data is bound to the UI by means of `android.databinding.Observable*` properties. 

## Splitting by feature

As you might have realized there are still a lot of code duplication in the main two applications. This can be further removed by `Splitting by feature`, wherein each feature can be separate into its own separate module and implemented based on need. 

With this a feature what is common between both the apps e.g. `login` can be separated into its own module which in turn can be imported into out main application.  

I have linked to a great resource on this in [further reading](#further-reading) section. 


# License 

    MIT License
    
    Copyright (c) 2019 Ritesh Shakya
    
    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
    
    
# Further Reading

[Modularizing Android Applications](https://medium.com/google-developer-experts/modularizing-android-applications-9e2d18f244a0)

[Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

[Architecting Android…The evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)

[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)