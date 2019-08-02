package com.matrix.mypawa;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    private PowerViewModel powerViewModel;

    public static final String KEY_BODY = "key_body";


    @TargetApi(M)
    @Override
    public void onReceive(final Context context, Intent intent) {


        Log.i(TAG, "Intent Received: " + intent.getAction());
        if (intent.getAction() == SMS_RECEIVED) {
            // Get the SMS message.
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String strMessage = "";
            String format = bundle.getString("format");

            // Retrieve the SMS message received.
            // Object[] pdus = (Object[]) bundle.get(pdu_type);
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                // Check the Android version.
                boolean isVersionM =
                        (Build.VERSION.SDK_INT >= M);
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (isVersionM) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }

                    String strMsgBody = msgs[i].getMessageBody();
                    String strMsgSrc = msgs[i].getOriginatingAddress();
                    // Build the message to show.
                    //  strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                    //strMessage += " :" + msgs[i].getMessageBody() + "\n";
                    // Log and display the SMS message.


                    if (strMsgSrc.equals("+254774")) {

                        Data data = new Data.Builder()
                                .putString(KEY_BODY, strMsgBody)
                                .build();

                        OneTimeWorkRequest messageWorkRequest = new OneTimeWorkRequest.Builder(MessagesWorker.class)
                                .setInputData(data)
                                .build();


                        Intent in = new Intent("com.matrix.mypawa");
                        Bundle extras = new Bundle();
                        extras.putString("com.matrix.mypawa.sender", strMsgSrc);
                        extras.putString("com.matrix.mypawa.body", strMsgBody);
                        in.putExtras(extras);
                        context.sendBroadcast(in);

                        //strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;
                        Toast.makeText(context, "yeah", Toast.LENGTH_SHORT).show();

                        if (strMsgBody.equalsIgnoreCase("TF")) {


                           // WorkManager.getInstance().enqueue(messageWorkRequest);

                            //powerViewModel = ViewModelProviders.of((FragmentActivity) context).get(PowerViewModel.class);
                        } else if (strMsgBody.equalsIgnoreCase("TO")) {
                            // Power power = new Power("Device 1","+254702565559", "TO", null, null,null);
                            // power.setId(1);

                            // WorkManager.getInstance().enqueue(messageWorkRequest);
                           // powerViewModel = ViewModelProviders.of((FragmentActivity) context).get(PowerViewModel.class);


                            // abortBroadcast();
                        }
                        // Log.d(TAG, "onReceive: " + strMessage);
                        //Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                    }

                }//intent


            }
        }
    }

}
