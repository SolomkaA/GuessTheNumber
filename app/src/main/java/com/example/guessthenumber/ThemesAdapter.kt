package com.example.guessthenumber

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ThemesAdapter(private val mList: List<ThemesList>, context: Context) : RecyclerView.Adapter<ThemesAdapter.ViewHolder>() {


    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int, themeName: TextView, themePrice: TextView, checkBox: CheckBox)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_layout, parent, false)




        return ViewHolder(view, mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val themesList = mList[position]


        // sets the text to the textview from our itemHolder class
        holder.themeName.text = themesList.text
        holder.themePrice.text = themesList.price.toString()
        holder.checkBox.isChecked = themesList.checkBox
        val state = themesList.state
        if (state != 0){
            holder.themePrice.visibility = View.GONE
            holder.checkBox.visibility = View.VISIBLE
            if (state == 2){
                holder.checkBox.isChecked = true
            }
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val themeName: TextView = itemView.findViewById(R.id.nameTheme)
        val themePrice: TextView = itemView.findViewById(R.id.priceTheme)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, themeName, themePrice, checkBox)
            }
        }
    }
}
