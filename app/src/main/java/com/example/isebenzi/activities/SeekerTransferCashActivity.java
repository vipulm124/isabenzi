package com.example.isebenzi.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.AcceptJob;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class SeekerTransferCashActivity extends AppCompatActivity {
    private Button btnCompleteBooking;
    private Button btnCancelBooking;
    private TextView tvTopName;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvAmount;
    int position;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("AbK3Amc-i7SRP4Y1-4IyCGolU3taADpllDD_E_HU42-oeVBQA63RIPG7TsXLjfcFoSKfSIKBY16WppoI");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_transfer_cash);
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
        init();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void init() {
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
        btnCompleteBooking = (Button) findViewById(R.id.btnCompleteBooking);
        btnCancelBooking = (Button) findViewById(R.id.btnCancelBooking);
        tvTopName = (TextView) findViewById(R.id.tvTopName);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        try {
            tvTopName.setText(job.getProvider()+" ACCEPTED");
            tvName.setText(job.getProvider()+" has accepted and confirmed for,");
            Geocoder geocoder;
            List<Address> addresses;
            String address = null;
            geocoder = new Geocoder(SeekerTransferCashActivity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(job.getLatitude()), Double.parseDouble(job.getLongitude()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                address = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvAddress.setText("Address :"+address);
            String[] dateTime=job.getFromDate().split(" ");
            String date=dateTime[0];
            String time=dateTime[1];
            tvDate.setText("Date :"+date);
            tvTime.setText("Time :"+time);
            tvAmount.setText("An amount of "+job.getPerDay()+"R will be\ndeducted from your paypal account");
        }catch (Exception e ){
            e.printStackTrace();
        }
        final Double currencyRand=Integer.valueOf(job.getPerDay())/14.25;
        btnCompleteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
                // Change PAYMENT_INTENT_SALE to
                //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
                //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
                //     later via calls from your server.

                PayPalPayment payment = new PayPalPayment(new BigDecimal(currencyRand), "USD", "sample item",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(SeekerTransferCashActivity.this, PaymentActivity.class);

                // send the same configuration for restart resiliency
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

                startActivityForResult(intent, 0);
            }
        });
        btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callServer(String id, String status) {

        CommonMethods.showProgressDialog(SeekerTransferCashActivity.this);
        AppHandlerNew.providerAcceptJob(id, status, new AppHandlerNew.AcceptJobListner() {
            @Override
            public void onAcceptJob(AcceptJob acceptJob) {
                switch (acceptJob.getMessage()) {
                    case "success":
                        Toast.makeText(SeekerTransferCashActivity.this, "Job is accepted successfully", Toast.LENGTH_SHORT).show();
                        CommonMethods.callAnActivityWithParameter(SeekerTransferCashActivity.this, SeekerCandidatesArrivedActivity.class, "position", getIntent().getStringExtra("position"));
                        break;
                    case "failure":
                        Toast.makeText(SeekerTransferCashActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(SeekerTransferCashActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    callServer(job.getId(), "2");
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}
