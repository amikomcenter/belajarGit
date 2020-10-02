package com.zzulfiqor.amikomcentercrudkotlin

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zzulfiqor.amikomcentercrudkotlin.db.BarangHelper
import com.zzulfiqor.amikomcentercrudkotlin.db.DatabaseContract
import com.zzulfiqor.amikomcentercrudkotlin.models.BarangModel
import kotlinx.android.synthetic.main.activity_barang_update.*
import kotlinx.android.synthetic.main.item_barang.*

class BarangUpdate : AppCompatActivity() {

    private var isEdit = false
    private var barang: BarangModel? = null
    private var position: Int = 0
    private lateinit var barangHelper: BarangHelper


    companion object {
        const val EXTRA_BARANG = "extra_barang"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang_update)

        barangHelper = BarangHelper.getInstance(applicationContext)
        barangHelper.open()

        barang = intent.getParcelableExtra(EXTRA_BARANG)
        if (barang != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            barang = BarangModel()
        }

        if (isEdit) {
            barang?.let {
                barangUpdateNama.setText(it.nama )
                barangUpdateStok.setText(it.jumlah.toString())
            }
        }


        barangUpdateBtnUpdate.setOnClickListener {
            if (barangUpdateNama.text.toString().isEmpty()){
                barangUpdateNama.error = "Field nama barang tidak boleh kosong"
                return@setOnClickListener
            }else if (barangUpdateStok.text.toString().isEmpty()){
                barangUpdateStok.error = "Field stok tidak boleh kosong"
            }

            barang?.nama = barangUpdateNama.text.toString()
            barang?.jumlah = barangUpdateStok.text.toString().toInt()

            val values = ContentValues()
            values.put(DatabaseContract.BarangColumns.NAMA, barangUpdateNama.text.toString())
            values.put(DatabaseContract.BarangColumns.JUMLAH, barangUpdateStok.text.toString().toInt())

            val result = barangHelper.update(barang?.id.toString(), values).toLong()
            if (result > 0) {
                setResult(RESULT_UPDATE, intent)
                finish()
                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
            }


        }
    }
}