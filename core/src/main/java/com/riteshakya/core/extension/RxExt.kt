package com.riteshakya.core.extension

import io.reactivex.Observable

@Suppress("UNCHECKED_CAST")
inline fun <T : Any, R : Any> Iterable<Observable<T>>.combineLatest(
        crossinline combineFunction: (args: List<T>) -> R
)
        : Observable<R> = Observable.combineLatest(this) {
    combineFunction(it.asList().map { item -> item as T })
}