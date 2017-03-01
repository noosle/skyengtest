package com.skyeng.skyengtest.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by noosle on 10.11.2016.
 */
public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        updateSomeData(context);
    }

    private void updateSomeData(Context context) {
        //update here and then recall
        Server.registerUpdateFromServer(context);
    }
}