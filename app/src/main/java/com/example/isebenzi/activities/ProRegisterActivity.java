package com.example.isebenzi.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ProRegisterActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Button btnNext;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etPassportNumber;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnUpload;
    private String path;
    private String imgPath;
    private ImageView dp_new_image;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pro);
        init();
        CommonObjects.setContext(getApplicationContext());
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                path = CommonMethods.getRealPathFromURI(data.getData(), this);
                CommonMethods.setImage(dp_new_image, path, new Point(720, 1280), false, 0);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
//                path = getImagePath();
//                CommonMethods.setImage(dp_new_image, path, new Point(720, 1280), false, 0);
                Uri imageUri = Uri.parse(mCurrentPhotoPath);
                File file = new File(imageUri.getPath());
                try {
                    InputStream ims = new FileInputStream(file);
                    dp_new_image.setImageBitmap(BitmapFactory.decodeStream(ims));
                } catch (FileNotFoundException e) {
                    return;
                }

                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(ProRegisterActivity.this,
                        new String[]{imageUri.getPath()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
            } else {
                super.onActivityResult(requestCode, resultCode,
                        data);
            }
        }
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
        btnUpload = (Button) findViewById(R.id.btnUpload);
        dp_new_image = (ImageView) findViewById(R.id.dp_new_image);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.verifyStoragePermissions(ProRegisterActivity.this);
                final Dialog dialog = CommonMethods.showConfirmationDialog(ProRegisterActivity.this, R.layout.popup_take_picture, R.style.NewDialog);
                Button btnCapture = (Button) dialog.findViewById(R.id.btnCapture);
                Button btnGallary = (Button) dialog.findViewById(R.id.btnGallary);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                btnCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        final Intent intent = new Intent(
//                                MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                setImageUri());
//                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                        ProRegisterActivityPermissionsDispatcher.startCameraWithCheck(ProRegisterActivity.this);
                        dialog.dismiss();
                    }
                });
                btnGallary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, REQUEST_IMAGE_PICK);
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommonMethods.callAnActivity(ProRegisterActivity.this, RegisterUploadPhotoActivity.class);
                callServer(etFirstName.getText().toString(), etLastName.getText().toString(), etPhone.getText().toString()
                        , etPassportNumber.getText().toString()
                        , "0", etEmail.getText().toString()
                        , etPassword.getText().toString());
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProRegisterActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void startCamera() {
        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
        }
    }
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Access to External Storage is required")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
//    public Uri setImageUri() {
//        // Store image in dcim
//        File file = new File(Environment.getExternalStorageDirectory()
//                + "/DCIM/", "image" + new Date().getTime() + ".png");
////        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Image.png");
//        Uri imgUri = Uri.fromFile(file);
//        this.imgPath = file.getAbsolutePath();
//        return imgUri;
//    }

    public String getImagePath() {
        return imgPath;
    }

    private void callServer(String firstname, String lastname, String phone, String passport, String type, String email, String password) {
        if (validate()) {
            if (dp_new_image.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) dp_new_image.getDrawable()).getBitmap();
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
                    UserLocal userLocal = new UserLocal();
                    userLocal.setFirstname(firstname);
                    userLocal.setLastname(lastname);
                    userLocal.setPhone(phone);
                    userLocal.setPassport(passport);
                    userLocal.setType(type);
                    userLocal.setPassword(password);
                    userLocal.setFile(file);
                    userLocal.setEmail(email);
                    Gson gson = new Gson();
                    String string = gson.toJson(userLocal);
                    CommonMethods.callAnActivityWithParameter(ProRegisterActivity.this, RegisterUploadPhotoActivity.class, "userLocal", string);
                }
            } else {
                Toast.makeText(ProRegisterActivity.this, "Please Upload Passport Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonMethods.getBooleanPreference(ProRegisterActivity.this, "isebenzi", "isSignUp", false)) {
            finish();
            CommonMethods.setBooleanPreference(ProRegisterActivity.this, "isebenzi", "isSignUp", false);
        }
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
        return valid;
    }
}
