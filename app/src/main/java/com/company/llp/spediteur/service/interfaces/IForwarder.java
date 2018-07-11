package com.company.llp.spediteur.service.interfaces;

import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.company.llp.spediteur.velodepiste.AthleteLocationRequest;

public interface IForwarder {

    void sendHttpRequest(AthleteLocationRequest locationRequestObj);
    AthleteLocationRequest parseSMS(SmsMessage smsObj);

}
