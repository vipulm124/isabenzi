package com.example.isebenzi.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.SignUpResponse;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SeekerRegisterActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button btnNext;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassportNumber;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private GoogleMap mMap;
    private LinearLayout llPhoto;
    private ImageView ivProfilePic;
    private TextView tvUploadPhoto;
    private Button btnSignUp;
    private LinearLayout llStepOne;
    private String path;
    private String imgPath;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String lOng = "";
    private String lat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_seeker);
        init();
        initMap();
        CommonObjects.setContext(getApplicationContext());
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(SeekerRegisterActivity.this);
    }

    private void init() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassportNumber = (EditText) findViewById(R.id.etPassportNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnNext = (Button) findViewById(R.id.btnNext);
        llStepOne = (LinearLayout) findViewById(R.id.llStepOne);
        llPhoto = (LinearLayout) findViewById(R.id.llPhoto);
        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
        tvUploadPhoto = (TextView) findViewById(R.id.tvUploadPhoto);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    llPhoto.setVisibility(View.VISIBLE);
                    llStepOne.setVisibility(View.GONE);
                }
            }
        });
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.verifyStoragePermissions(SeekerRegisterActivity.this);
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivProfilePic.getDrawable() != null) {
                    Bitmap bitmap = ((BitmapDrawable) ivProfilePic.getDrawable()).getBitmap();
                    if (bitmap != null) {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Image.png");
                        try {
                            file.createNewFile();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                            byte[] bitmapData = stream.toByteArray();
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(bitmapData);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        callServer(etFirstName.getText().toString(), etLastName.getText().toString(), etPhone.getText().toString()
                                , etPassportNumber.getText().toString()
                                , "1", etEmail.getText().toString(), etPassword.getText().toString(), lOng, lat, "Location"
                                , "bakName", "0", "expiry", "stc", "number", file);
                    }
                } else {
                    Toast.makeText(SeekerRegisterActivity.this, "Please Upload Profile Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                MarkerOptions marker;
                marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("New Marker");

                mMap.addMarker(marker);
                lOng = String.valueOf(point.longitude);
                lat = String.valueOf(point.latitude);
//                System.out.println(point.latitude + "---" + point.longitude);
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public String getImagePath() {
        return imgPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                path = CommonMethods.getRealPathFromURI(data.getData(), this);
                CommonMethods.setImage(ivProfilePic, path, new Point(720, 1280), false, 0);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                path = getImagePath();
                CommonMethods.setImage(ivProfilePic, path, new Point(720, 1280), false, 0);
            } else {
                super.onActivityResult(requestCode, resultCode,
                        data);
            }
        }
    }

    private void callServer(String firstname, String lastname, String phone, String passport, String type, String email, String password
            , String longitude, String latitue, String location, String bankName, String bankType, String expiry, String stc, String number, File photo) {
        UserLocal userLocal = new UserLocal();
        userLocal.setFirstname(firstname);
        userLocal.setLastname(lastname);
        userLocal.setPhone(phone);
        userLocal.setPassport(passport);
        userLocal.setType(type);
        userLocal.setEmail(email);
        userLocal.setPassword(password);
        userLocal.setLongitude(longitude);
        userLocal.setLatitude(latitue);
        userLocal.setLocation(location);
        userLocal.setBankName(bankName);
        userLocal.setBank_type(bankType);
        userLocal.setExpiry(expiry);
        userLocal.setStc(stc);
        userLocal.setNumber(number);
        userLocal.setProfilePic(photo);
        userLocal.setToken("");
        CommonMethods.showProgressDialog(SeekerRegisterActivity.this);
        AppHandlerNew.seekerSignUp(userLocal, new AppHandlerNew.SeekerSignUpListener() {
            @Override
            public void onSignUp(SignUpResponse signUpResponse) {
                switch (signUpResponse.getMessage()) {
                    case "success":
                        CommonObjects.setUser(signUpResponse.getUser());
                        CommonObjects.getUser().setPhoto(signUpResponse.getPath());
                        CommonMethods.callAnActivity(SeekerRegisterActivity.this, SeekerDashboardActivity.class);
                        finish();
                        break;
                    case "failure":
                        Toast.makeText(SeekerRegisterActivity.this, "This email is already exist", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(SeekerRegisterActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
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
        if (lOng.equals("")) {
            Toast.makeText(SeekerRegisterActivity.this, "Please select your location by taping the map", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        if (llPhoto.getVisibility() == View.VISIBLE) {
            llPhoto.setVisibility(View.GONE);
            llStepOne.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }
}
