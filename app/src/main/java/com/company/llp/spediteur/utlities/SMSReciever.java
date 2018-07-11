package com.company.llp.spediteur.utlities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.company.llp.spediteur.R;
import com.company.llp.spediteur.ServerSetttings;
import com.company.llp.spediteur.common.APP_CONSTANTS;
import com.company.llp.spediteur.common.model.AthleteLocation;
import com.company.llp.spediteur.service.ForwarderService;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class SMSReciever extends BroadcastReceiver {
    private static final String TAG = SMSReciever.class.getSimpleName();
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    SharedPreferences sharedPref;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPref = context.getSharedPreferences(APP_CONSTANTS.SpediteurPrefs, Context.MODE_PRIVATE);
        String url = sharedPref.getString(APP_CONSTANTS.URL, "");

        Boolean status = sharedPref.getBoolean(APP_CONSTANTS.SERVER_STATUS, false);

        Log.e("SmsReceiver", "Message Recieved: ");

        // Retrieves a map of extended data from the intent.
        if (status != null && status) {

            final Bundle bundle = intent.getExtras();

            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();
                        Log.e("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                        if (message.contains("spediteur")) {
                            Map<String, Object> params = SMSConvertor.convertToAthleteLocation(currentMessage);
                            AthleteLocation loc = createAthleteObject(params);
                            ForwarderService f = ForwarderService.getInstance();
                            if (url != null && url.startsWith("http"))
                                f.sendDataGet(loc, url);

                            // Show Alert
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context,
                                    "senderNum: " + senderNum + ", message: " + message, duration);
                            toast.show();
                        }
                    } // end for loop
                } // bundle is null

            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" + e);

            }
        }
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    private AthleteLocation createAthleteObject(Map<String, Object> params) {
        AthleteLocation loc = new AthleteLocation();
        if (params.get("spediteur").equals("velo")) {
            loc.setBibNo((String) params.get("bib"));
            loc.setAthleteId((String) params.get("did"));
            loc.setLongitude(Double.valueOf((String) params.get("lg")));
            loc.setLat(Double.valueOf((String) params.get("lt")));
            loc.setRiderName((String) params.get("rName"));
            String ut = (String) params.get("ut");
            if (ut != null && ut.length() > 0) {
                long l = Long.valueOf(ut);
                Date d = new Date(l);
                loc.setLastUpdated(d);
            }


        }
        return loc;
    }
}
