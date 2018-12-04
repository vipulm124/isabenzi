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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.EditBankDetails;
import com.example.isebenzi.business.objects.EditProfile;
import com.example.isebenzi.business.objects.SignUpResponse;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProBankingDetailActivity extends AppCompatActivity {
    private Button btnNext;
    private EditText etAccountHolderName;
    private EditText etAccountNo;
    private EditText etBrachCode;
    private Spinner spinnerBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_banking_detail);
        init();
        List<String> list=new ArrayList<>();
        list.add("Bank Name");
        list.add("NEDBANK");
        list.add("ABSA");
        list.add("BIDVEST BANK");
        list.add("CAPITEC BANK");
        list.add("FIRST NATIONAL BANK");
        list.add("INVESTEC BANK LIMITED");
        list.add("STANDARD BANK OF SOUTH AFRICA");
        list.add("AFRICAN BANK");
        list.add("ALBARAKA BANK");
        list.add("BNP PARIPAS");
        list.add("CITIBANK");
        list.add("GRINDROD BANK");
        list.add("HABIB OVERSEAS BANK LIMITED");
        list.add("HBZ BANK LIMITED");
        list.add("HSBC BANK PLC");
        list.add("JP MORGAN CHASE");
        list.add("PEOPLES BANK LTD");
        list.add("SA BANK OF ATHENS");
        list.add("SASFIN BANK");
        list.add("UBANK LIMITED");
        if (!CommonObjects.getUser().getBank().getAccountName().equals(""))
            etAccountHolderName.setText(CommonObjects.getUser().getBank().getAccountName());
        if (!CommonObjects.getUser().getBank().getAccountNumber().equals(""))
            etAccountNo.setText(CommonObjects.getUser().getBank().getAccountNumber());
        if (!CommonObjects.getUser().getBank().getBranchCode().equals(""))
            etBrachCode.setText(CommonObjects.getUser().getBank().getBranchCode());
        if (!CommonObjects.getUser().getBank().getName().equals("")){
            for (int i = 0;i<list.size();i++){
                if (list.get(i).equals(CommonObjects.getUser().getBank().getName())){
                    spinnerBank.setSelection(i);
                }
            }
        }

    }


    private void init() {
        etAccountHolderName = (EditText) findViewById(R.id.etAccountHolderName);
        etAccountNo = (EditText) findViewById(R.id.etAccountNo);
//        etBankName = (EditText) findViewById(R.id.etBankName);
        etBrachCode = (EditText) findViewById(R.id.etBrachCode);
        spinnerBank = (Spinner) findViewById(R.id.spinnerBank);
        btnNext = (Button) findViewById(R.id.btnNext);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.select_bank, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_drop);
        spinnerBank.setAdapter(adapter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServer(etAccountNo.getText().toString(), etBrachCode.getText().toString(),spinnerBank.getSelectedItem().toString()
                , etAccountHolderName.getText().toString()
                        , CommonObjects.getUser().getId());
            }
        });
    }

    private void callServer(String account_number,String branch_code,String bank_name,String account_name,String id) {
        if (validate()) {
            CommonMethods.showProgressDialog(ProBankingDetailActivity.this);
            AppHandlerNew.providerEditBankDetail( account_number, branch_code, bank_name, account_name, id, new AppHandlerNew.EditBankDetailsListner() {
                @Override
                public void onEditBankDetails(EditBankDetails editBankDetails) {
                    switch (editBankDetails.getMessage()) {
                        case "success":
                            Toast.makeText(ProBankingDetailActivity.this, "Your banking details are updated successfully", Toast.LENGTH_SHORT).show();
//                                CommonMethods.callAnActivity(ProPersonalInfomationActivity.this, RegisterUploadPhotoActivity.class);
                            break;
                        case "failure":
                            Toast.makeText(ProBankingDetailActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ProBankingDetailActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    CommonMethods.hideProgressDialog();
                }
            });
        }
    }
//    }

    public boolean validate() {
        boolean valid = true;

        String acountName = etAccountHolderName.getText().toString();
        String accountNo = etAccountNo.getText().toString();
//        String bankName = etBankName.getText().toString();
        String branchCode = etBrachCode.getText().toString();

        if (acountName.isEmpty()) {
            etAccountHolderName.setError("enter a valid account name");
            valid = false;
        } else {
            etAccountHolderName.setError(null);
        }
        if (accountNo.isEmpty()) {
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
            Toast.makeText(ProBankingDetailActivity.this,"Please Select Bank name",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (branchCode.isEmpty()) {
            etBrachCode.setError("enter a valid branch code");
            valid = false;
        } else {
            etBrachCode.setError(null);
        }
        return valid;
    }
}
