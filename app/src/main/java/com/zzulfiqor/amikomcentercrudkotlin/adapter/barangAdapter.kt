package com.zzulfiqor.amikomcentercrudkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.zzulfiqor.amikomcentercrudkotlin.*
import com.zzulfiqor.amikomcentercrudkotlin.db.BarangHelper
import com.zzulfiqor.amikomcentercrudkotlin.models.BarangModel
import kotlinx.android.synthetic.main.item_barang.view.*

class BarangAdapter(private val activity: Activity) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {
    private lateinit var barangHelper: BarangHelper

    companion object {
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_DELETE = 301
    }

    var listBarang = ArrayList<BarangModel>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listBarang.clear()
            }
            this.listBarang.addAll(listNotes)
            notifyDataSetChanged()
        }

    fun addItem(note: BarangModel) {
        this.listBarang.add(note)
        notifyItemInserted(this.listBarang.size - 1)
    }
    fun updateItem(position: Int, note: BarangModel) {
        this.listBarang[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listBarang.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listBarang.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(view)
    }
    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    override fun getItemCount(): Int = this.listBarang.size

    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(barang: BarangModel) {
            with(itemView){

                barangHelper = BarangHelper.getInstance(context)
                barangHelper.open()

                barangNama.text = barang.nama
                barangStok.text = barang.jumlah.toString()


                cv_barang.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, BarangUpdate::class.java)
                        intent.putExtra(BarangUpdate.EXTRA_POSITION, position)
                        intent.putExtra(BarangUpdate.EXTRA_BARANG, barang)
                        activity.startActivityForResult(intent, BarangUpdate.REQUEST_UPDATE)
                    }
                }))

                barangBtnDelete.setOnClickListener {
                    val dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
                    val dialogTitle = "Hapus Note"
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    alertDialogBuilder.setTitle(dialogTitle)
                    alertDialogBuilder
                        .setMessage(dialogMessage)
                        .setCancelable(false)
                        .setPositiveButton("Ya") { dialog, id ->
                                val result = barangHelper.deleteById(barang?.id.toString()).toLong()
                                if (result > 0) {
                                    val i = Intent(context, DeleteActivity::class.java)
                                    activity.startActivity(i)

                                } else {
                                    Toast.makeText(context, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()

                }
            }
        }
    }
}