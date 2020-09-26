package com.zzulfiqor.amikomcentercrudkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val listItem: List<NoteModel>, private val listener : NoteListener):
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {


    interface NoteListener{
        fun OnItemClicked(noteModel: NoteModel)
    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(note: NoteModel){
            itemView.tv_title.text = note.name
            itemView.tv_body.text = note.desc

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteHolder(LayoutInflater.from(parent.context). inflate(R.layout.item_note, parent, false))

    override fun getItemCount()= listItem.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindView(listItem[position])
        holder.itemView.setOnClickListener {
            listener.OnItemClicked(listItem[position])
        }
    }
}