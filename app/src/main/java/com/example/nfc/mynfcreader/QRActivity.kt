package com.example.nfc.mynfcreader


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_qr.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec
import com.example.nfc.mynfcreader.utils.AESUtils




class QRActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    //Builder show()
    override fun handleResult(rawResult:Result?){

        //progressBar.visibility = View.VISIBLE


        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("QR Code is not Valid")
        builder.setPositiveButton("Ok", {dialogInterface:DialogInterface?, which: Int ->
            var intent = Intent(this,QRActivity::class.java)
            finish()
            startActivity(intent)
        })
        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle("Successful")
        builder1.setMessage("QR Code Scanned")
        builder1.setPositiveButton("Ok", {dialogInterface:DialogInterface?, which: Int ->
            var intent = Intent(this,MainActivity::class.java)
            finish()
            startActivity(intent)
        })



    //QR Code Result
        txt_result.text = rawResult!!.text
//        val textRes:String
//        textRes = txt_result.text.toString()
//        val encrypted = textRes
//        var decrypted = ""
//        try {
//            decrypted = AESUtils.decrypt(encrypted)
//            Log.d("TEST", "decrypted:$decrypted")
//            txt_result.setText("$decrypted")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        var encrypted = ""
//        val sourceStr = textRes
//        try {
//            encrypted = AESUtils.encrypt(sourceStr)
//            Log.d("TEST", "encrypted:$encrypted")
//            txt_result.setText("$encrypted")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        //QR Code Result

        if(txt_result.text.contains(txt_result.text.toString()))
        {
            builder1.show()
        }

        else if(txt_result.text.contains("Result"))
        {
            //NetworkTask(this).execute()
            builder.show()
            Toast.makeText(this@QRActivity,"You must scan the QR Code First.",Toast.LENGTH_LONG).show()
        }
    }
    //Builder show() end

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)


    NetworkTask(this).execute()

        //RequestPermission
        Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.CAMERA)
                .withListener(object: PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        zxscan.setResultHandler(this@QRActivity)
                        zxscan.startCamera()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@QRActivity,"You must accept this Permission",Toast.LENGTH_SHORT).show()
                    }

                }).check()

        btn_refresh.setOnClickListener{
            var intent = Intent(this,QRActivity::class.java)
            finish()
            startActivity(intent)
        }

    }
    //Loading Circular Implementation
    class NetworkTask(var activity:QRActivity): AsyncTask<Void, Void, Void>(){

        var dialog = Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar)

        override fun onPreExecute(){
            val view = activity.layoutInflater.inflate(R.layout.full_screen_progress_bar,null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            Thread.sleep(3000)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            dialog.dismiss()
        }
    }
    //Loading Circular Implementation
}
