package com.example.callblocker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import com.android.internal.telephony.ITelephony

class CallBlockerScreeningService: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        //Create object of Telephony Manager class.
        val telephony =
            context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
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
            Log.d("calling number", ""+callingNumber)
            if (callingNumber!=null && callingNumber.contains("7542939685")) {
                telephonyService.silenceRinger()
                telephonyService.endCall()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}