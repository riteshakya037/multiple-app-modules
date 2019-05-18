package com.riteshakya.ui.components

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.PopupMenu
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.riteshakya.core.model.BaseModel
import com.riteshakya.ui.R
import com.riteshakya.ui.adapters.base.BindableViewHolder
import com.riteshakya.ui.adapters.base.GenericListAdapter
import com.riteshakya.ui.components.base.BaseBottomSheet
import kotlinx.android.synthetic.main.custom_menu_bottom_sheet.view.*
import kotlinx.android.synthetic.main.item_list_popup_menu.view.*


class MenuBottomSheet : BaseBottomSheet() {
    lateinit var builder: Builder

    private var menuAdapter: GenericListAdapter<MenuItem> = GenericListAdapter()
    private lateinit var menus: ArrayList<MenuItem>
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismissAllowingStateLoss()
            }
        }

        override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
        }
    }
    private lateinit var contentView: View

    override fun setupDialog(dialog: Dialog, style: Int) {
        contentView = View.inflate(context, R.layout.custom_menu_bottom_sheet, null)
        dialog.setContentView(contentView)
        val layoutParams =
                (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior
        if (behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
            behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottomsheet_peek_height)
        }
        (contentView.parent as View).setBackgroundColor(
                ContextCompat.getColor(context!!, android.R.color.transparent)
        )
        setRecyclerView()
        setTitle(builder.title)
    }

    private fun setTitle(title: String) {
        if (title.isEmpty()) {
            contentView.titleTxt.visibility = GONE
        } else {
            contentView.titleTxt.visibility = VISIBLE
            contentView.titleTxt.text = title
        }
    }


    private fun setRecyclerView() {
        menus = ArrayList(builder.items.filter { it.enabled })
        menuAdapter.run {
            setItems(menus)
        }
        contentView.menuList.run {
            layoutManager = LinearLayoutManager(context)
            this.adapter = menuAdapter
        }
        menuAdapter.registerViewHolderFactory(
                type = MenuItem::class,
                layout = R.layout.item_list_popup_menu,
                bindViewHolder = { itemView, _ -> MenuViewHolder(itemView) },
                onClick = { item ->
                    complete(item.id)
                }
        )
    }

    private fun complete(id: String) {
        builder.onMenuSelectedListener!!.onMenuSelected(id.toInt())
        dismissAllowingStateLoss()
    }

    override fun onCancel(dialog: DialogInterface?) {
        builder.onMenuSelectedListener!!.onDismiss()
    }

    override fun dismiss() {
        builder.onMenuSelectedListener!!.onDismiss()
    }

    interface MenuSelectedListener {
        fun onMenuSelected(id: Int)
        fun onDismiss()
    }

    class Builder(@param:NonNull var context: Context) {

        internal var onMenuSelectedListener: MenuSelectedListener? = null
        internal var items: List<MenuItem> = arrayListOf()
        internal var title: String = ""

        fun setOnMenuSelectedListener(onMenuSelectedListener: MenuSelectedListener): Builder {
            this.onMenuSelectedListener = onMenuSelectedListener
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMenuItems(menuRes: Int): Builder {
            val menuInflater = MenuInflater(context)
            val popup = PopupMenu(context, null)
            val menu: Menu = popup.menu
            menuInflater.inflate(menuRes, menu)
            val output = ArrayList<MenuItem>()
            for (i in 0 until menu.size()) {
                val item = menu.getItem(i)
                output.add(MenuItem(item.itemId, item.title.toString()))
            }
            this.items = output
            return this
        }

        fun create(): MenuBottomSheet {

            if (onMenuSelectedListener == null) {
                throw RuntimeException(
                        "You have to use setOnMenuSelectedListener() for receive menu events"
                )
            }

            val customBottomSheetDialogFragment = MenuBottomSheet()

            customBottomSheetDialogFragment.builder = this
            return customBottomSheetDialogFragment
        }
    }

    class MenuItem(itemId: Int, val title: String, var enabled: Boolean = true) : BaseModel(
            itemId.toString()
    )


    class MenuViewHolder(itemView: View) : BindableViewHolder<MenuItem>(itemView) {
        override fun bind(item: MenuItem) {
            itemView.text.text = item.title
        }

    }
}
