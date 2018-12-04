package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.EditProfile;
import com.example.isebenzi.business.objects.SignUpResponse;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

public class SeekerPersonalInfomationActivity extends AppCompatActivity {
    private Button btnNext;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etPassportNumber;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_personal_info);
        init();
        if (!CommonObjects.getUser().getFirstname().equals(""))
            etFirstName.setText(CommonObjects.getUser().getFirstname());
        if (!CommonObjects.getUser().getLastname().equals(""))

            etLastName.setText(CommonObjects.getUser().getLastname());
        if (!CommonObjects.getUser().getPhone().equals(""))

            etPhone.setText(CommonObjects.getUser().getPhone());
        if (!CommonObjects.getUser().getPassport().equals(""))
            etPassportNumber.setText(CommonObjects.getUser().getPassport());
    }
    private void init() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassportNumber = (EditText) findViewById(R.id.etPassportNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServer(etFirstName.getText().toString(), etLastName.getText().toString(), etPhone.getText().toString()
                        , etPassportNumber.getText().toString()
                        , etPassword.getText().toString(), CommonObjects.getUser().getId());
            }
        });
    }

    private void callServer(final String firstname, final String lastname, String phone, String passport, String password, String id) {
        if (validate()) {
            CommonMethods.showProgressDialog(SeekerPersonalInfomationActivity.this);
            AppHandlerNew.providerEditProfile(firstname, lastname, phone, passport, password, id, new AppHandlerNew.EditProfileListner() {
                @Override
                public void onEditProfile(EditProfile editProfile) {
                    switch (editProfile.getMessage()) {
                        case "success":
                            Toast.makeText(SeekerPersonalInfomationActivity.this, "Your profile is updated successfully", Toast.LENGTH_SHORT).show();
//                                CommonMethods.callAnActivity(ProPersonalInfomationActivity.this, RegisterUploadPhotoActivity.class);
                            CommonObjects.getUser().setFirstname(firstname);
                            CommonObjects.getUser().setLastname(lastname);
                            break;
                        case "failure":
                            Toast.makeText(SeekerPersonalInfomationActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(SeekerPersonalInfomationActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    CommonMethods.hideProgressDialog();
                }
            });
        }
    }
    public boolean validate() {
        boolean valid = true;

        String password = etPassword.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phone = etPhone.getText().toString();
        String passport = etPassportNumber.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();

        if (firstName.isEmpty()) {
            etFirstName.setError("enter a valid first name");
            valid = false;
        } else {
            etFirstName.setError(null);
        }
        if (lastName.isEmpty()) {
            etLastName.setError("enter a valid last name");
            valid = false;
        } else {
            etLastName.setError(null);
        }
        if (phone.isEmpty()) {
            etPhone.setError("enter a valid phone number");
            valid = false;
        } else {
            etPhone.setError(null);
        }
        if (passport.isEmpty()) {
            etPassportNumber.setError("enter a valid passport Id");
            valid = false;
        } else {
            etPassportNumber.setError(null);
        }
        if (confirmPass.isEmpty()) {
            etConfirmPassword.setError("enter a valid password");
            valid = false;
        } else {
            etConfirmPassword.setError(null);
        }
        if (!password.equals(confirmPass)) {
            etPassword.setError("password does not match");
            etConfirmPassword.setError("password does not match");
            valid = false;
        } else {
            etPassword.setError(null);
            etConfirmPassword.setError(null);
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
