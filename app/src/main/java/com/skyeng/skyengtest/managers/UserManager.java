package com.skyeng.skyengtest.managers;

import android.content.Context;

import com.skyeng.skyengtest.R;
import com.skyeng.skyengtest.core.CallBack;
import com.skyeng.skyengtest.core.Constants;
import com.skyeng.skyengtest.core.Server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by noosle on 01.03.2017.
 */

public class UserManager {

    @SuppressWarnings("unchecked")
    public static void auth(Context context, String email, String password, CallBack callBack) {

        if (!Server.isOnline(context)) {
            callBack.onFail(context.getString(R.string.internet_error));
            return;
        }

        if (!email.equals(Constants.ACCESS_EMAIL)
                || !password.equals(Constants.ACCESS_PASSWORD)) {
            callBack.onFail(context.getString(R.string.incorrect_login_or_password));
            return;
        }

        PreferenceManager.set(PreferenceManager.TOKEN, Constants.TOKEN, context);
        callBack.onSuccess();


    }

    public static void sendCode(Context context, String email, CallBack callBack) {

        if (!Server.isOnline(context)) {
            callBack.onFail(context.getString(R.string.internet_error));
            return;
        }

        if (!email.equals(Constants.ACCESS_EMAIL)) {
            callBack.onFail(context.getString(R.string.incorrect_login));
            return;
        }

        callBack.onSuccess(Constants.ACCESS_CODE);

    }

    public static void authWithCode(Context context, String code, CallBack callBack) {

        if (!Server.isOnline(context)) {
            callBack.onFail(context.getString(R.string.internet_error));
            return;
        }

        if (!code.equals(Constants.ACCESS_CODE)) {
            callBack.onFail(context.getString(R.string.incorrect_code));
            return;
        }

        PreferenceManager.set(PreferenceManager.TOKEN, Constants.TOKEN, context);
        callBack.onSuccess();

    }
}
