package com.example.userlist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.part_list_item.view.*

class PartAdapter(var items: List<User>, val callback: Callback) : RecyclerView.Adapter<PartAdapter.MainHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.part_list_item, parent, false))

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnCreateContextMenuListener { menu, v, info ->
            menu.add(0, position, 0, "Open")
            menu.add(1, position, 0, "Edit")
            menu.add(2, position, 0, "Delete")
        }
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.item_name)
        private val phone = itemView.findViewById<TextView>(R.id.item_phone)
        private val image = itemView.findViewById<ImageView>(R.id.item_image)

        fun bind(item: User) {
            name.text = item.name
            phone.text = item.phone

            if(item.image != null) {
                image.setImageBitmap(convertToBitmap(item.image!!))
            }
            else{
                image.setImageResource(R.drawable.unknown)
            }

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    private fun convertToBitmap(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    interface Callback {
        fun onItemClicked(item: User)
    }
}