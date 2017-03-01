package com.skyeng.skyengtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.skyeng.skyengtest.core.BaseActivity;
import com.skyeng.skyengtest.core.UpdateService;
import com.skyeng.skyengtest.managers.PreferenceManager;

/**
 * Created by noosle on 01.03.2017.
 */

@SuppressWarnings("ConstantConditions")
public class FinalActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Button logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuitDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showQuitDialog();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!UpdateService.isMyServiceRunning(UpdateService.class, self))
            startService(new Intent(self, UpdateService.class));
    }

    @Override
    public void onBackPressed() {
        showQuitDialog();
    }

    private void showQuitDialog() {
        AlertDialog alertDialog;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(self, R.style.AppCompatAlertDialogStyle);
        alertDialogBuilder.setMessage(getString(R.string.quit_question));


        alertDialogBuilder.setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                PreferenceManager.set(PreferenceManager.TOKEN, "", self);
                stopService(new Intent("UpdateService"));
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });


        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

}
