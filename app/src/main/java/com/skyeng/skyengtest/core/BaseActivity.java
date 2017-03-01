package com.skyeng.skyengtest.core;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by noosle on 01.03.2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected BaseActivity self = null;
    protected ProgressDialog progressDialog = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
    }

    public void showShortSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }


    public void showLongSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showProgress(String text) {

        if (progressDialog == null) {
            try {
                progressDialog = ProgressDialog.show(this, "", text);
                progressDialog.setCancelable(false);
            } catch (Exception e) {

            }

        }

    }

    public void hideProgress() {

        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
