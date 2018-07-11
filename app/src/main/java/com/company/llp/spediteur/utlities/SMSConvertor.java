package com.company.llp.spediteur.utlities;

import android.telephony.SmsMessage;

import com.company.llp.spediteur.common.model.AthleteLocation;

import java.util.HashMap;
import java.util.Map;

public class SMSConvertor {
    public static Map<String, Object> convertToAthleteLocation(SmsMessage smsMessage) {
        Map<String, Object> map = new HashMap<>();
        String message = smsMessage.getDisplayMessageBody();
        String[] keys = message.split(",");
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                String[] params = key.split(":");
                map.put(params[0], params[1]);
                //System.out.println("line " + key);
           }
        }
        return map;
    }

    /*public static void main(String ags[]) {
        String sms = "spediteur:velo,lt:250,lg:50,bib:1824,rid:1";
        String[] keys = sms.split(",");
        Map<String, Object> map = new HashMap<>();

        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                String[] params = key.split(":");
                map.put(params[0], params[1]);
                System.out.println("line " + key);

            }
        }

        System.out.println(map);
    }*/

}
