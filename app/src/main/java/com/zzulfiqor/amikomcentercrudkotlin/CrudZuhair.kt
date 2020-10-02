package com.zzulfiqor.amikomcentercrudkotlin

import android.content.ContentValues
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zzulfiqor.amikomcentercrudkotlin.adapter.BarangAdapter
import com.zzulfiqor.amikomcentercrudkotlin.db.BarangHelper
import com.zzulfiqor.amikomcentercrudkotlin.db.DatabaseContract
import com.zzulfiqor.amikomcentercrudkotlin.helper.MappingHelper
import com.zzulfiqor.amikomcentercrudkotlin.models.BarangModel
import kotlinx.android.synthetic.main.activity_barang_update.*
import kotlinx.android.synthetic.main.activity_crud_zuhair.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CrudZuhair : AppCompatActivity() {

    private var barang: BarangModel? = null
    private lateinit var barangHelper: BarangHelper
    private lateinit var adapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_zuhair)

        barangHelper = BarangHelper.getInstance(applicationContext)
        barangHelper.open()


        rvBarang.layoutManager = LinearLayoutManager(this)
        rvBarang.setHasFixedSize(true)
        adapter = BarangAdapter(this)
        rvBarang.adapter = adapter

        // proses ambil data
        loadBarangAsync()

        barangBtnAddData.setOnClickListener {

            Log.e("dbError","@@@ Error Database : "+etNamaBarang.text.toString())

            if (etNamaBarang.text.toString().isEmpty()){
                etNamaBarang.error = "Field nama barang tidak boleh kosong"
                return@setOnClickListener
            }else if (etStokBarang.text.toString().isEmpty()){
                etStokBarang.error = "Field stok tidak boleh kosong"
            }

            barang?.nama = etNamaBarang.text.toString()
            barang?.jumlah = etStokBarang.text.toString().toInt()

            val values = ContentValues()
            values.put(DatabaseContract.BarangColumns.NAMA, etNamaBarang.text.toString())
            values.put(DatabaseContract.BarangColumns.JUMLAH, etStokBarang.text.toString())

            val result = barangHelper.insert(values)

            if (result > 0) {
                barang?.id = result.toInt()
                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun loadBarangAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredBarang = async(Dispatchers.IO) {
                val cursor = barangHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val barang = deferredBarang.await()
            if (barang.size > 0) {
                adapter.listBarang = barang
                layoutAnimEmpty.visibility = View.GONE
                rvBarang.visibility = View.VISIBLE
            } else {
                adapter.listBarang = ArrayList()
                layoutAnimEmpty.visibility = View.VISIBLE
                rvBarang.visibility = View.GONE
            }
        }    }

    override fun onDestroy() {
        super.onDestroy()
        barangHelper.close()
    }
}