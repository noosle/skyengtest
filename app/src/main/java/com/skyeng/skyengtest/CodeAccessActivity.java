package com.skyeng.skyengtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.skyeng.skyengtest.core.BaseActivity;
import com.skyeng.skyengtest.core.CallBack;
import com.skyeng.skyengtest.core.Constants;
import com.skyeng.skyengtest.managers.UserManager;


@SuppressWarnings("ConstantConditions")
public class CodeAccessActivity extends BaseActivity {

    private TextView infoText;
    private EditText codeEditText;
    private Button submitButton, resendCodeButton;
    String email, code;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_access_code);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        infoText = (TextView) findViewById(R.id.infoText);
        codeEditText = (EditText) findViewById(R.id.codeEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
        resendCodeButton = (Button) findViewById(R.id.resendCodeButton);
        codeEditText.addTextChangedListener(codeWatcher);


        email = getIntent().getStringExtra("email");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(getString(R.string.progress_login));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UserManager.authWithCode(self, codeEditText.getText().toString(), new CallBack() {

                            @Override
                            public void onSuccess() {
                                finish();
                                startActivity(new Intent(self, FinalActivity.class));
                            }

                            @Override
                            public void onFail(String error) {
                                showShortSnackbar(submitButton, error);
                            }
                        });
                        hideProgress();
                    }
                }, Constants.TIME_PROGRESS);
            }
        });

        resendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(getString(R.string.progress_ccde));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UserManager.sendCode(self, email, new CallBack<String>() {

                            @Override
                            public void onSuccess(String code) {
                                infoText.setText(getString(R.string.enter_code_sended_on_email)
                                        + " "
                                        + email
                                        + ")");
                                startCodeCountDownTimer();
                            }

                            @Override
                            public void onFail(String error) {
                                showShortSnackbar(submitButton, error);
                            }
                        });
                        hideProgress();
                    }
                }, Constants.TIME_PROGRESS);

            }
        });

        startCodeCountDownTimer();


    }

    private void startCodeCountDownTimer() {
        resendCodeButton.setEnabled(false);
        resendCodeButton.setTextColor(ContextCompat.getColor(self, R.color.blue_disabled));
        new CountDownTimer(Constants.COUNTOWN_CODE_TIMER, Constants.COUNTSOWN_TICK) {

            public void onTick(long millisUntilFinished) {
                resendCodeButton.setText(getString(R.string.resend_code) + " (" + millisUntilFinished / Constants.COUNTSOWN_TICK + "с)");
            }

            public void onFinish() {
                resendCodeButton.setEnabled(true);
                resendCodeButton.setText(getString(R.string.resend_code));
                resendCodeButton.setTextColor(ContextCompat.getColor(self, R.color.blue_light));
            }

        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher codeWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (codeEditText.getText().length() > 0
                    && !submitButton.isEnabled()) {
                submitButton.setEnabled(true);
                return;
            }
            if (codeEditText.getText().length() == 0)
                submitButton.setEnabled(false);
        }

        public void afterTextChanged(Editable s) {

        }
    };

}
