package com.example.isebenzi.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.utils.CommonMethods;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class RegisterUploadPhotoActivity extends AppCompatActivity {
    private Button btnNext;
    private TextView tvUploadProfilePic;
    private ImageView ivProfilePic;
    private EditText etAccountHolderName;
    private EditText etAccountNo;
    private EditText etBrachCode;
    private String path;
    private String imgPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Spinner spinnerBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_upload_photo);
        init();

    }
    public String getImagePath() {
        return imgPath;
    }

    private void init() {
        Gson gson = new Gson();
        final String s = getIntent().getStringExtra("userLocal");
        final UserLocal userLocal = gson.fromJson(s, UserLocal.class);
        tvUploadProfilePic = (TextView) findViewById(R.id.tvUploadProfilePic);
        spinnerBank = (Spinner) findViewById(R.id.spinnerBank);
        etAccountHolderName = (EditText) findViewById(R.id.etAccountHolderName);
        etAccountNo = (EditText) findViewById(R.id.etAccountNo);
        etBrachCode = (EditText) findViewById(R.id.etBrachCode);
        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.select_bank, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinnerBank.setAdapter(adapter);
        tvUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                CommonMethods.verifyStoragePermissions(RegisterUploadPhotoActivity.this);
//                if (checkSelfPermission(Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    requestPermissions(new String[]{Manifest.permission.CAMERA},
//                            1);
//                }
                final Dialog dialog = CommonMethods.showConfirmationDialog(RegisterUploadPhotoActivity.this, R.layout.popup_take_picture, R.style.NewDialog);
                Button btnCapture = (Button) dialog.findViewById(R.id.btnCapture);
                Button btnGallary = (Button) dialog.findViewById(R.id.btnGallary);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                btnCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                setImageUri());
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
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
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
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
                            userLocal.setAccountName(etAccountHolderName.getText().toString());
                            userLocal.setAccountNumber(etAccountNo.getText().toString());
                            userLocal.setBankName(spinnerBank.getSelectedItem().toString());
                            userLocal.setBranchCode(etBrachCode.getText().toString());
                            userLocal.setProfilePic(file);
                            Gson gson = new Gson();
                            String string = gson.toJson(userLocal);
                            CommonMethods.callAnActivityWithParameter(RegisterUploadPhotoActivity.this, RegisterAvailibilityActivity.class,"userLocal",string);

                        }
                    } else {
                        Toast.makeText(RegisterUploadPhotoActivity.this, "Please Upload Profile Image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory()
                + "/DCIM/", "image" + new Date().getTime() + ".png");
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Image.png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonMethods.getBooleanPreference(RegisterUploadPhotoActivity.this,"isebenzi","isSignUp",false)){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            try {
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean validate() {
        boolean valid = true;

        String accountName = etAccountHolderName.getText().toString();
        String accountNO = etAccountNo.getText().toString();
//        String bankName = etBankName.getText().toString();
        String code = etBrachCode.getText().toString();

        if (accountName.isEmpty()) {
            etAccountHolderName.setError("enter a valid account holder name");
            valid = false;
        } else {
            etAccountHolderName.setError(null);
        }
        if (accountNO.isEmpty()) {
            etAccountNo.setError("enter a valid account number");
            valid = false;
        } else {
            etAccountNo.setError(null);
        }
//        if (bankName.isEmpty()) {
//            etBankName.setError("enter a valid bank name");
//            valid = false;
//        } else {
//            etBankName.setError(null);
//        }
        if (spinnerBank.getSelectedItemPosition()==0) {
            Toast.makeText(RegisterUploadPhotoActivity.this,"Please Select Bank name",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (code.isEmpty()) {
            etBrachCode.setError("enter a valid branch code");
            valid = false;
        } else {
            etBrachCode.setError(null);
        }

        return valid;
    }
}
