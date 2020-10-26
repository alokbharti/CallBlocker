package com.example.callblocker;

import java.lang.reflect.Method;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.ITelephony;


public class CustomPhoneStateListener extends PhoneStateListener {
    Context context;

    public CustomPhoneStateListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String callingNumber)
    {
        super.onCallStateChanged(state, callingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
            case TelephonyManager.CALL_STATE_RINGING:
                //handle in coming call
                //handle out going call
                endCallIfBlocked(callingNumber);
                break;
            default:
                break;
        }
    }

    private void endCallIfBlocked(String callingNumber) {
        try {
            // Java reflection to gain access to TelephonyManager's
            // ITelephony getter
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            com.android.internal.telephony.ITelephony telephonyService = (ITelephony) m.invoke(tm);
            telephonyService = (ITelephony) m.invoke(tm);
            //
            if (callingNumber.contains("7542939685")) {
                telephonyService.silenceRinger();
                telephonyService.endCall();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}