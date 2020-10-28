package com.example.callblocker

import android.Manifest.permission
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.callblocker.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val MY_PERMISSIONS_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        checkPermission()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(this, permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    permission.READ_PHONE_STATE,
                    permission.READ_CONTACTS
                ),
                MY_PERMISSIONS_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST){
            for (index in permissions.indices){
                Log.d("index","$index")
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val showRationale = shouldShowRequestPermissionRationale(permissions[index])
                        // showRationale - false indicates has checked never show again checkbox so in that we need to close the app or route user to settings
                        // to enable Location permission
                        if (showRationale) {
                            showReasoningForUsingPermission()
                        } else {
                            //block app usage or open settings
                            blockFurtherAppUsage()
                        }
                    }
                }
            }
        }
    }

    private fun blockFurtherAppUsage() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Permissions")
            .setMessage(getString(R.string.permission_denied_again))
            .setPositiveButton(
                "Grant"
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                openPhoneSettings()
            }
            .setNegativeButton(
                "Exit"
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                finish()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setCancelable(false)
        builder.show()
    }

    /**
     * Invokes when the user clicks on the settings button on the snackbar and shows the settings for the app
     */
    private fun openPhoneSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun showReasoningForUsingPermission() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Contact Permissions Required")
            .setMessage(getString(R.string.permission_denied_once))
            .setPositiveButton("Grant") { dialog, which ->
                dialog.dismiss()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        permission.READ_PHONE_STATE,
                        permission.READ_CONTACTS
                    ),
                    MY_PERMISSIONS_REQUEST
                )
            }.setNegativeButton(
                "Exit"
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                finish()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setCancelable(false)
        builder.show()
    }
}