package com.skyeng.skyengtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.skyeng.skyengtest.core.BaseActivity;
import com.skyeng.skyengtest.core.CallBack;
import com.skyeng.skyengtest.core.Constants;
import com.skyeng.skyengtest.managers.PreferenceManager;
import com.skyeng.skyengtest.managers.UserManager;

import org.json.JSONObject;

@SuppressWarnings("ConstantConditions")
public class LoginActivity extends BaseActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, loginStateButton;
    private TextView passHint;
    private Handler handler = new Handler();
    private boolean withPhone = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PreferenceManager.get(PreferenceManager.TOKEN, self).length() > 0)
            startActivity(new Intent(self, FinalActivity.class));

        setContentView(R.layout.activity_login);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        loginStateButton = (Button) findViewById(R.id.loginStateButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        passHint = (TextView) findViewById(R.id.passHint);

        emailEditText.addTextChangedListener(loginWatcher);
        passwordEditText.addTextChangedListener(loginWatcher);

        loginStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (withPhone) {
                    withPhone = false;
                    loginStateButton.setText(getString(R.string.enter_without_password));
                    passHint.setText(getString(R.string.pass_not_easy));
                    loginButton.setText(getString(R.string.enter));
                    passwordEditText.setVisibility(View.VISIBLE);
                    emailEditText.setText("");
                    passwordEditText.setText("");
                } else {
                    withPhone = true;
                    loginStateButton.setText(getString(R.string.enter_with_password));
                    passHint.setText(getString(R.string.pass_easy));
                    loginButton.setText(getString(R.string.get_key));
                    passwordEditText.setVisibility(View.GONE);
                    emailEditText.setText("");
                    passwordEditText.setText("");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (withPhone) {
                    showProgress(getString(R.string.progress_ccde));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UserManager.sendCode(self, emailEditText.getText().toString(), new CallBack<String>() {

                                @Override
                                public void onSuccess(String code) {
                                    Intent intent = new Intent(self, CodeAccessActivity.class);
                                    intent.putExtra("email", emailEditText.getText().toString());
                                    startActivity(intent);
                                }

                                @Override
                                public void onFail(String error) {
                                    showShortSnackbar(passHint, error);
                                }
                            });
                            hideProgress();
                        }
                    }, Constants.TIME_PROGRESS);
                } else {
                    showProgress(getString(R.string.progress_login));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UserManager.auth(self, emailEditText.getText().toString(), passwordEditText.getText().toString(), new CallBack<JSONObject>() {

                                @Override
                                public void onSuccess() {
                                    startActivity(new Intent(self, FinalActivity.class));
                                }

                                @Override
                                public void onFail(String error) {
                                    showShortSnackbar(passHint, error);
                                }
                            });
                            hideProgress();
                        }
                    }, Constants.TIME_PROGRESS);
                }
            }
        });


    }

    private final TextWatcher loginWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!withPhone) {
                if (emailEditText.getText().length() > 0
                        && passwordEditText.getText().length() > 0
                        && !loginButton.isEnabled()) {
                    loginButton.setEnabled(true);
                    return;
                }
                if (emailEditText.getText().length() == 0
                        || passwordEditText.getText().length() == 0)
                    loginButton.setEnabled(false);
            } else {
                if (emailEditText.getText().length() > 0
                        && !loginButton.isEnabled()) {
                    loginButton.setEnabled(true);
                    return;
                }
                if (emailEditText.getText().length() == 0)
                    loginButton.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {

        }
    };
}
