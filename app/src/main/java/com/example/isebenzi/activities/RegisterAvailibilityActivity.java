package com.example.isebenzi.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.Calendar;

public class RegisterAvailibilityActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button btnNext;
    public static TextView tvDatePicekrFrom;
    public static TextView tvTimePickerFrom;
    public static TextView tvTimePickerTwoFrom;
    public static TextView tvDatePicekrTo;
    public static TextView tvTimePickerTo;
    public static TextView tvTimePickerTwoTo;
    private SeekBar radiusSeekBar;

    public static String dateFrom;
    public static String timeFrom;
    public static String dateTo;
    public static String timeTo;

    private EditText etDailyRateTo;
    private EditText etDailyRateFrom;
    //    private EditText etLocation;
//    private EditText etAddress;
    private boolean isDatePicked = false;
    private boolean isTimePicked = false;
    private String lOng;
    private String lat;

    private GoogleMap mMap;
    private String mapRadius;


    public static TextView getTvDatePicekrFrom() {
        return tvDatePicekrFrom;
    }

    public static TextView getTvTimePickerFrom() {
        return tvTimePickerFrom;
    }

    public static TextView getTvTimePickerTwoFrom() {
        return tvTimePickerTwoFrom;
    }

    public static TextView getTvDatePicekrTo() {
        return tvDatePicekrTo;
    }

    public static TextView getTvTimePickerTo() {
        return tvTimePickerTo;
    }

    public static TextView getTvTimePickerTwoTo() {
        return tvTimePickerTwoTo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_availibility);
        init();
        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(RegisterAvailibilityActivity.this);
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
                final Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(point.latitude, point.longitude))
                        .radius(10000)
                        .strokeWidth(4)
                        .strokeColor(Color.RED)
                        .fillColor(0x550000FF));
                radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        circle.setRadius(progress*100);
                        seekBar.setCameraDistance(10000);
                        mapRadius=String.valueOf(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                lOng = String.valueOf(point.longitude);
                lat = String.valueOf(point.latitude);

                System.out.println(point.latitude + "---" + point.longitude);
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

    private void init() {

        Gson gson = new Gson();
        final String s = getIntent().getStringExtra("userLocal");
        final UserLocal userLocal = gson.fromJson(s, UserLocal.class);
        btnNext = (Button) findViewById(R.id.btnNext);
        etDailyRateTo = (EditText) findViewById(R.id.etDailyRateTo);
        etDailyRateFrom = (EditText) findViewById(R.id.etDailyRateFrom);
        radiusSeekBar = (SeekBar) findViewById(R.id.radiusSeekBar);
//        etLocation = (EditText) findViewById(R.id.etLocation);
//        etAddress = (EditText) findViewById(R.id.etAddress);
        tvDatePicekrFrom = (TextView) findViewById(R.id.tvDatePicekrFrom);
        tvTimePickerFrom = (TextView) findViewById(R.id.tvTimePickerFrom);
        tvTimePickerTwoFrom = (TextView) findViewById(R.id.tvTimePickerTwoFrom);
        tvDatePicekrTo = (TextView) findViewById(R.id.tvDatePicekrTo);
        tvTimePickerTo = (TextView) findViewById(R.id.tvTimePickerTo);
        tvTimePickerTwoTo = (TextView) findViewById(R.id.tvTimePickerTwoTo);

        radiusSeekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    userLocal.setAddress("No");
                    userLocal.setDailyRate(etDailyRateTo.getText().toString());
                    userLocal.setTo(dateTo + " " + timeTo);
                    userLocal.setFrom(dateFrom + " " + timeFrom);
                    userLocal.setLatitude(lat);
                    userLocal.setLongitude(lOng);
                    userLocal.setRadius(mapRadius);
                    userLocal.setToken("1");
                    CommonMethods.showProgressDialog(RegisterAvailibilityActivity.this);
                    AppHandlerNew.signUpProvider(userLocal, new AppHandlerNew.SignUpListener() {
                        @Override
                        public void onSignUp(SignUpResponse signUpResponse) {
                            switch (signUpResponse.getMessage()) {
                                case "success":
                                    CommonObjects.setUser(signUpResponse.getUser());
                                    CommonObjects.getUser().setPhoto(signUpResponse.getPath());
                                    CommonMethods.callAnActivity(RegisterAvailibilityActivity.this, AssessmentSelectActivity.class);
                                    finish();
                                    CommonMethods.setBooleanPreference(RegisterAvailibilityActivity.this, "isebenzi", "isSignUp", true);
                                    break;
                                case "failure":
                                    Toast.makeText(RegisterAvailibilityActivity.this, "This email is already exist", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(RegisterAvailibilityActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            CommonMethods.hideProgressDialog();
                        }
                    });
                }
            }
        });
        tvDatePicekrFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerFrom();
            }
        });
        tvTimePickerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerFrom();
            }
        });
        tvTimePickerTwoFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerFrom();
            }
        });
        tvDatePicekrTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerTo();
            }
        });
        tvTimePickerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerTo();
            }
        });
        tvTimePickerTwoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerTo();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String dailyRate = etDailyRateTo.getText().toString();
        String dailyRateFrom = etDailyRateFrom.getText().toString();

//        String address = etAddress.getText().toString();
//        String location = etLocation.getText().toString();
        if (dailyRateFrom.isEmpty()) {
            etDailyRateFrom.setError("enter a valid daily rate");
            valid = false;
        } else {
            etDailyRateFrom.setError(null);
        }
        if (dailyRate.isEmpty()) {
            etDailyRateTo.setError("enter a valid daily rate");
            valid = false;
        } else {
            etDailyRateTo.setError(null);
        }
        if (lOng.equals("")) {
            Toast.makeText(RegisterAvailibilityActivity.this, "Please Select your location", Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        if (address.isEmpty()) {
//            etAddress.setError("enter a valid address");
//            valid = false;
//        } else {
//            etAddress.setError(null);
//        }
//        if (location.isEmpty()) {
//            etLocation.setError("enter a valid location");
//            valid = false;
//        } else {
//            etLocation.setError(null);
//        }
        if (!isDatePicked) {
            valid = false;
            Toast.makeText(RegisterAvailibilityActivity.this, "Please set your availability", Toast.LENGTH_SHORT).show();
        }
        if (!isTimePicked) {
            Toast.makeText(RegisterAvailibilityActivity.this, "Please set your availability", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void showDatePickerFrom() {
        DialogFragment newFragment = new DatePickerFragmentFrom();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        isDatePicked = true;
    }

    private void showDatePickerTo() {
        DialogFragment newFragment = new DatePickerFragmentTo();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        isDatePicked = true;
    }

    private void showTimePickerFrom() {
        DialogFragment newFragment = new TimePickerFragmentFrom();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        isTimePicked = true;
    }

    private void showTimePickerTo() {
        DialogFragment newFragment = new TimePickerFragmentTo();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        isTimePicked = true;
    }

    public static class DatePickerFragmentFrom extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            RegisterAvailibilityActivity.getTvDatePicekrFrom().setText(day + "/" + month + "/" + year);
            dateFrom = year + "-" + month + "-" + day;
        }
    }

    public static class DatePickerFragmentTo extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            RegisterAvailibilityActivity.getTvDatePicekrTo().setText(day + "/" + month + "/" + year);
            dateTo = year + "-" + month + "-" + day;

        }
    }

    public static class TimePickerFragmentFrom extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), R.style.DialogTheme, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String minutes = String.valueOf(minute);
            if (minutes.equals("0"))
                minutes = "00";
            RegisterAvailibilityActivity.getTvTimePickerFrom().setText(hourOfDay + " : " + minutes);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            RegisterAvailibilityActivity.getTvTimePickerTwoFrom().setText(AM_PM);
            timeFrom = hourOfDay + ":" + minute + ":00";
        }
    }

    public static class TimePickerFragmentTo extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), R.style.DialogTheme, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String minutes = String.valueOf(minute);
            if (minutes.equals("0"))
                minutes = "00";
            RegisterAvailibilityActivity.getTvTimePickerTo().setText(hourOfDay + " : " + minutes);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            RegisterAvailibilityActivity.getTvTimePickerTwoTo().setText(AM_PM);
            timeTo = hourOfDay + ":" + minute + ":00";

        }
    }
}
