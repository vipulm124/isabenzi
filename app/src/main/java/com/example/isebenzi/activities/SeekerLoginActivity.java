package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.SignInResponse;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

public class SeekerLoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvRegister;
    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seeker);
        CommonObjects.setContext(getApplicationContext());
        init();

    }

    private void init() {
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        isLoginAlready();
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(SeekerLoginActivity.this, SeekerRegisterActivity.class);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonMethods.isNetworkAvailable(SeekerLoginActivity.this)) {
                    if (validate())
                        callServer(etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    private void isLoginAlready() {
        if (CommonMethods.getBooleanPreference(SeekerLoginActivity.this, "isebenzi", "isLogin", false)) {
            String email = CommonMethods.getStringPreference(SeekerLoginActivity.this, "isebenzi", "email", "");
            String password = CommonMethods.getStringPreference(SeekerLoginActivity.this, "isebenzi", "password", "");
            cbRememberMe.setChecked(true);
            if (CommonMethods.isNetworkAvailable(SeekerLoginActivity.this)) {
                callServer(email, password);
            }
        }
    }

    private void callServer(final String email, final String password) {
        CommonMethods.showProgressDialog(SeekerLoginActivity.this);
//        String token = FirebaseInstanceId.getInstance().getToken();
        AppHandlerNew.signIn(email, password,"","1", new AppHandlerNew.SignInListener() {
            @Override
            public void onSignIn(SignInResponse signInResponse) {
                switch (signInResponse.getMessage()) {
                    case "success":
                        if (cbRememberMe.isChecked()) {
                            CommonMethods.setStringPreference(SeekerLoginActivity.this, "isebenzi", "email", email);
                            CommonMethods.setStringPreference(SeekerLoginActivity.this, "isebenzi", "password", password);
                            CommonMethods.setBooleanPreference(SeekerLoginActivity.this, "isebenzi", "isLogin", true);
                        }
                        CommonObjects.setUser(signInResponse.getUser());
                        CommonMethods.callAnActivity(SeekerLoginActivity.this, SeekerDashboardActivity.class);
                        break;
                    case "failure":
                        Toast.makeText(SeekerLoginActivity.this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(SeekerLoginActivity.this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
                        break;
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty()) {
            etPassword.setError("enter a valid password");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        return valid;
    }
}
