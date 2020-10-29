package com.example.callblocker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.android.internal.telephony.ITelephony
import com.example.callblocker.data.DataRepository
import java.util.concurrent.Executors
import javax.inject.Inject

class CallBlockerScreeningService: BroadcastReceiver(){
    @Inject
    private lateinit var dataRepository: DataRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val incomingNumber:String? = intent!!.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        endCallIfBlocked(incomingNumber, context)
    }

    private fun endCallIfBlocked(callingNumber: String?, context: Context?) {
        try {
            // Java reflection to gain access to TelephonyManager's
            // ITelephony getter
            val tm =
                context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val c = Class.forName(tm.javaClass.name)
            val m = c.getDeclaredMethod("getITelephony")
            m.isAccessible = true
            var telephonyService = m.invoke(tm) as ITelephony
            telephonyService = m.invoke(tm) as ITelephony
            if (callingNumber!=null) {
                Executors.newSingleThreadExecutor().submit{
                    val blockedContact = dataRepository.getBlockedContactByNumber(callingNumber)
                    if (blockedContact != null){
                        telephonyService.silenceRinger()
                        telephonyService.endCall()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}