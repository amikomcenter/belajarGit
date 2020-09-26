package com.zzulfiqor.amikomcentercrudkotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_crud.*
import kotlinx.coroutines.InternalCoroutinesApi

class EditCrud : AppCompatActivity() {
    companion object{
        const val EDIT_NOTE_EXTRA = "EXTRA_NOTE"
        const val EDIT_NOTE_DESC = "EXTRA_DESC"
    }
    private lateinit var noteModel: NoteModel
    private var isUpdate = false
    private lateinit var database: NoteDb
    private lateinit var dao: NoteDao

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_crud)

        database = NoteDb.getDbNotes(applicationContext)
        dao = database.getNoteDao()

        if (intent.getParcelableExtra<NoteModel>(EDIT_NOTE_EXTRA) != null){
            btnDelete.visibility = View.VISIBLE
            isUpdate = true
            noteModel = intent.getParcelableExtra(EDIT_NOTE_EXTRA)!!

            edt_title.setText(noteModel.name)
            edt_body.setText(noteModel.desc)

            edt_title.setSelection(noteModel.name.length)
        }

        btnSave.setOnClickListener {
            val title = edt_title.text.toString()
            val body = edt_body.text.toString()

            val i = Intent()

            if (title.isEmpty() && body.isEmpty()){
                Toast.makeText(applicationContext, "Tidak Bisa Kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (isUpdate){
                    noteModel = NoteModel(id = noteModel.id,name = title, desc = body)
                } else {
                    noteModel = NoteModel(name = title, desc = body)
                }
                i.putExtra(EDIT_NOTE_EXTRA, noteModel)
                setResult(Activity.RESULT_OK, i)
            }
            finish()
        }

        btnDelete.setOnClickListener {
            deleteNote(noteModel)
        }
    }

    private fun deleteNote(noteModel: NoteModel) {
        dao.delete(noteModel)
        Toast.makeText(applicationContext, "Note removed", Toast.LENGTH_SHORT).show()
    }

    /*private fun saveNote(noteModel: NoteModel) {
        if (dao.getById(noteModel.id).isEmpty()){
            dao.insert(noteModel)
        } else {
            dao.update(noteModel)
        }
        Toast.makeText(applicationContext, "Note saved", Toast.LENGTH_SHORT).show()
    }*/
}