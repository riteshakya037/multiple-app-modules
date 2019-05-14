package com.riteshakya.ui.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mukesh.countrypicker.Country
import com.riteshakya.ui.R
import kotlinx.android.synthetic.main.custom_spinner_items_drop_down.view.*

/**
 * @author Ritesh Shakya
 */

class CountryAdapter(
    applicationContext: Context
) : ArrayAdapter<Country>(applicationContext, R.layout.custom_spinner_items) {
    private val inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    private val countries = Country.getAllCountries()

    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(i: Int): Country {
        return countries[i]
    }

    override fun getItemId(i: Int): Long {
        return getItem(i).hashCode().toLong()
    }

    fun getDialCode(i: Int): String {
        return getItem(i).dialCode
    }

    override fun getPosition(item: Country?): Int {
        return countries.indexOf(item)
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
        holder.icon.setImageResource(getItem(position).flag)
        holder.names.text = getItem(position).name
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
        holder.names.text = getItem(position).dialCode
        return output
    }

    internal class ViewHolder(view: View) {
        var names: TextView = view.textView
    }

    internal class DropDownViewHolder(view: View) {
        var icon: ImageView = view.imageView
        var names: TextView = view.textView
    }
}