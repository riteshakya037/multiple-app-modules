package com.riteshakya.ui.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.riteshakya.ui.R
import kotlinx.android.synthetic.main.custom_spinner_items_drop_down.view.*

/**
 * @author Ritesh Shakya
 */

class SpinnerAdapter(
        applicationContext: Context
) : ArrayAdapter<SpinnerAdapter.SpinnerModel>(applicationContext, R.layout.custom_spinner_items) {
    private val inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    private val items: ArrayList<SpinnerModel> = ArrayList()

    internal var selectedTextAppearance: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(i: Int): SpinnerModel {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return getItem(i).hashCode().toLong()
    }

    override fun getPosition(item: SpinnerModel?): Int {
        return items.indexOf(item)
    }

    fun setItems(items: List<SpinnerModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getDropDownView(position: Int, view: View?, parent: ViewGroup): View {
        var output = view
        val holder: DropDownViewHolder
        if (output == null) {
            output = inflater.inflate(R.layout.custom_spinner_items_drop_down, parent, false)
            holder = DropDownViewHolder(output!!)
            output.tag = holder
        } else {
            holder = output.tag as DropDownViewHolder
        }
        holder.names.text = getItem(position).text
        return output
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var output = view
        val holder: ViewHolder
        if (output == null) {
            output = inflater.inflate(R.layout.custom_spinner_items, viewGroup, false)
            holder = ViewHolder(output!!)
            output.tag = holder
        } else {
            holder = output.tag as ViewHolder
        }
        holder.names.text = getItem(position).text
        holder.names.setTextAppearance(context, selectedTextAppearance)
        return output
    }

    fun getValue(i: Int): String {
        return items[i].value
    }

    internal class ViewHolder(view: View) {
        var names: TextView = view.textView
    }

    internal class DropDownViewHolder(view: View) {
        var icon: ImageView = view.imageView
        var names: TextView = view.textView
    }

    class SpinnerModel(val value: String, val text: String)

}