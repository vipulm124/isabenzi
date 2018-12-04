package com.example.isebenzi.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.activities.AssessmentSelectActivity;
import com.example.isebenzi.activities.ProBankingDetailActivity;
import com.example.isebenzi.activities.ProMyReviewsActivity;
import com.example.isebenzi.activities.ProPersonalInfomationActivity;
import com.example.isebenzi.activities.ProRegisterActivity;
import com.example.isebenzi.activities.RegisterUploadPhotoActivity;
import com.example.isebenzi.activities.SalectModuleActivity;
import com.example.isebenzi.activities.SplashActivity;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.UploadPhoto;
import com.example.isebenzi.loopj.android.image.SmartImageView;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.example.isebenzi.utils.Constants;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by takhleeqmacpro on 11/29/16.
 */
public class ProviderProfileFragment extends Fragment {
    private TextView tvPersonalInfo;
    private TextView tvReviews;
    private TextView tvBankingDetails;
    private TextView tvAssessments;
    private TextView tvMyAccount;
    private SmartImageView ivProfilePic;
    private TextView tvUploadPhoto;
    private String path;
    private String imgPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private TextView tvName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_profile, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ivProfilePic= (SmartImageView) view.findViewById(R.id.ivProfilePicture);
        tvPersonalInfo = (TextView) view.findViewById(R.id.tvPersonalInfo);
        tvUploadPhoto= (TextView) view.findViewById(R.id.tvUploadPhoto);
        tvReviews = (TextView) view.findViewById(R.id.tvReviews);
        tvBankingDetails = (TextView) view.findViewById(R.id.tvBankingDetails);
        tvAssessments = (TextView) view.findViewById(R.id.tvAssessments);
        tvMyAccount = (TextView) view.findViewById(R.id.tvMyAccount);
        tvName= (TextView) view.findViewById(R.id.tvName);
        tvUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.verifyStoragePermissions(getActivity());
                final Dialog dialog = CommonMethods.showConfirmationDialog(getActivity(), R.layout.popup_take_picture, R.style.NewDialog);
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

        tvPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(getActivity(), ProPersonalInfomationActivity.class);
            }
        });
        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(getActivity(), ProMyReviewsActivity.class);
            }
        });
        tvBankingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(getActivity(), ProBankingDetailActivity.class);
            }
        });
        tvAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.setBooleanPreference(getActivity(), "isebenzi", "isLogin", true);
                CommonMethods.callAnActivity(getActivity(), AssessmentSelectActivity.class);
            }
        });
        tvMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(getActivity(), ProPersonalInfomationActivity.class);
            }
        });
    }

    public String getImagePath() {
        return imgPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                path = CommonMethods.getRealPathFromURI(data.getData(), getActivity());
                CommonMethods.setImage(ivProfilePic, path, new Point(720, 1280), false, 0);
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            delay();
                        }
                    }
                };
                timer.start();
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                path = getImagePath();
                CommonMethods.setImage(ivProfilePic, path, new Point(720, 1280), false, 0);
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            delay();
                        }
                    }
                };
                timer.start();
            } else {
                super.onActivityResult(requestCode, resultCode,
                        data);
            }
        }
    }

    private void delay() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callServer(CommonObjects.getUser().getId());
                        }
                    });
                }
            }
        };
        timer.start();
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
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Profile");
        try{

        if (CommonObjects.getUser().getFirstname()!=null&&CommonObjects.getUser().getLastname()!=null){
            String name=CommonObjects.getUser().getFirstname()+" "+CommonObjects.getUser().getLastname();
            tvName.setText(name);
        }
        if(CommonObjects.getUser().getPhoto()!=null){
            ivProfilePic.setImageUrl(Constants.NETWORK_SERVICE_BASE_URL+CommonObjects.getUser().getPhoto());
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void callServer(String id) {
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
                CommonMethods.showProgressDialog(getActivity());
                AppHandlerNew.providerupLoadPhoto(file,id, new AppHandlerNew.UploadPhotoListner() {
                    @Override
                    public void onUploadPhoto(UploadPhoto uploadPhoto) {
                        switch (uploadPhoto.getMessage()) {
                            case "success":
                                CommonObjects.getUser().setPhoto(uploadPhoto.getPath());
                                Toast.makeText(getActivity(), "Your profile picture is updated successfully", Toast.LENGTH_SHORT).show();
                                break;
                            case "failure":
                                Toast.makeText(getActivity(), "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        CommonMethods.hideProgressDialog();
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "Please Upload Profile Image", Toast.LENGTH_SHORT).show();
        }
    }

}