package com.zzulfiqor.amikomcentercrudkotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_crud_yoga.*
import kotlinx.coroutines.InternalCoroutinesApi

class CrudYoga : AppCompatActivity() {
    private lateinit var noteAdapter: NoteAdapter
    private val noteCode = 1
    private lateinit var dao: NoteDao
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_yoga)
        getNote()
        btnAdd.setOnClickListener{
            val i = Intent(this, EditCrud::class.java)
            startActivityForResult(i, noteCode)
        }
    }

    @InternalCoroutinesApi
    fun getNote(){
        val noteDB = NoteDb.getDbNotes(applicationContext)
        dao = noteDB.getNoteDao()
        val listnote = arrayListOf<NoteModel>()
        listnote.addAll(dao.getAll())
        setupRecycler(listnote)
        if (listnote.isNotEmpty()){
            tv_empty.visibility = View.GONE
        } else {
            tv_empty.visibility = View.VISIBLE
        }
    }

    private fun setupRecycler(list: List<NoteModel>){
        rv_note.apply {
            adapter = NoteAdapter(list, object : NoteAdapter.NoteListener{
                override fun OnItemClicked(noteModel: NoteModel) {
                    val i = Intent(this@CrudYoga, EditCrud::class.java).apply {
                        putExtra(EditCrud.EDIT_NOTE_EXTRA, noteModel)
                    }
                    startActivity(i)
                }
            })
            layoutManager = LinearLayoutManager(this@CrudYoga)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == noteCode && resultCode == Activity.RESULT_OK){
           data?.getParcelableExtra<NoteModel>(EditCrud.EDIT_NOTE_EXTRA)?.let {
                dao.insert(NoteModel(name = it.name, desc = it.desc))
            }
        }
    }
}