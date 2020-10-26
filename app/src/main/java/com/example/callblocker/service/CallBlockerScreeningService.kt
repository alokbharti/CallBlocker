package com.example.callblocker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.example.callblocker.CustomPhoneStateListener

class CallBlockerScreeningService: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        //Create object of Telephony Manager class.
        val telephony =
            context!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //Assign a phone state listener.
        val customPhoneListener = CustomPhoneStateListener(context)
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

}