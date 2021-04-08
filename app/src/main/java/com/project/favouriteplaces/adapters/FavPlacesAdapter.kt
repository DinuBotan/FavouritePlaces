package com.project.favouriteplaces.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.favouriteplaces.R
import com.project.favouriteplaces.database.FavPlace
import kotlinx.android.synthetic.main.item_favourite_place.view.*
import java.security.AccessControlContext

open class FavPlacesAdapter(
        private val context: Context,
        private var list: ArrayList<FavPlace>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_favourite_place,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if(holder is MyViewHolder){
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.placeImage))
            holder.itemView.tvTitle.text = model.placeTitle
            holder.itemView.tvDescription.text = model.placeDescription

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
