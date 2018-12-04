package com.example.isebenzi.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.EditAvailability;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by takhleeqmacpro on 11/29/16.
 */
public class ProviderAvailibilityFragment extends Fragment {
    private Button btnNext;
    public static TextView tvDateFrom;
    public static TextView tvTimeFrom;
    public static TextView tvTimeTwoFrom;
    public static TextView tvDateTo;
    public static TextView tvTimeTo;
    public static TextView tvTimeTwoTo;

    public static String dateFrom;
    public static String timeFrom;
    public static String dateTo;
    public static String timeTo;

    private EditText etDailyRateTo;
    private EditText etDailyRateFrom;
    //    private EditText etLocation;
//    private EditText etAddress;
//    private boolean isDatePicked = false;
//    private boolean isTimePicked = false;

    public static TextView getTvDateFrom() {
        return tvDateFrom;
    }

    public static TextView getTvTimeFrom() {
        return tvTimeFrom;
    }

    public static TextView getTvTimeTwoFrom() {
        return tvTimeTwoFrom;
    }

    public static TextView getTvDateTo() {
        return tvDateTo;
    }

    public static TextView getTvTimeTo() {
        return tvTimeTo;
    }

    public static TextView getTvTimeTwoTo() {
        return tvTimeTwoTo;
    }

    private GoogleMap mGoogleMap;
    MapView mMapView;
    private String lOng;
    private String lat;
    private String mapRadius;
    private SeekBar radiusSeekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_availibilty, container, false);
        init(view);
        initMap(view, savedInstanceState);
        return view;
    }

    private void initMap(View view, Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;

                Double latitue = Double.valueOf(CommonObjects.getUser().getAvailibility().getLatitude());
                Double longitude = Double.valueOf(CommonObjects.getUser().getAvailibility().getLongitude());
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latitue, longitude)).title("New Marker");
                mGoogleMap.addMarker(marker);
//                mGoogleMap.setMinZoomPreference(1000);
                final Circle circle = mGoogleMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitue, longitude))
                        .radius(10000)
                        .strokeWidth(4)
                        .strokeColor(Color.RED)
                        .fillColor(0x550000FF));
                radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        circle.setRadius(progress * 100);
                        seekBar.setCameraDistance(10000);
                        mapRadius = String.valueOf(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitue, longitude), 14.0f));
                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {
                        mGoogleMap.clear();
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(point.latitude, point.longitude)).title("New Marker");

                        mGoogleMap.addMarker(marker);
                        final Circle circle = mGoogleMap.addCircle(new CircleOptions()
                                .center(new LatLng(point.latitude, point.longitude))
                                .radius(10000)
                                .strokeWidth(4)
                                .strokeColor(Color.RED)
                                .fillColor(0x550000FF));
                        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                circle.setRadius(progress * 100);
                                seekBar.setCameraDistance(10000);
                                mapRadius = String.valueOf(progress);
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
                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private void init(View view) {
        btnNext = (Button) view.findViewById(R.id.btnNext);
        etDailyRateTo = (EditText) view.findViewById(R.id.etDailyRateTo);
        etDailyRateFrom = (EditText) view.findViewById(R.id.etDailyRateFrom);
//        etLocation = (EditText) view.findViewById(R.id.etLocation);
//        etAddress = (EditText) view.findViewById(R.id.etAddress);
        radiusSeekBar = (SeekBar) view.findViewById(R.id.radiusSeekBar);
        tvDateFrom = (TextView) view.findViewById(R.id.tvDateFrom);
        tvTimeFrom = (TextView) view.findViewById(R.id.tvTimeFrom);
        tvTimeTwoFrom = (TextView) view.findViewById(R.id.tvTimeTwoFrom);
        tvDateTo = (TextView) view.findViewById(R.id.tvDateTo);
        tvTimeTo = (TextView) view.findViewById(R.id.tvTimeTo);
        tvTimeTwoTo = (TextView) view.findViewById(R.id.tvTimeTwoTo);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    UserLocal userLocal = new UserLocal();
                    userLocal.setAddress("No");
//                    userLocal.setLocation(etLocation.getText().toString());
                    userLocal.setDailyRate(etDailyRateTo.getText().toString());
                    userLocal.setTo(dateTo + " " + timeTo);
                    userLocal.setFrom(dateFrom + " " + timeFrom);
                    userLocal.setLatitude(lat);
                    userLocal.setLongitude(lOng);
                    userLocal.setRadius(mapRadius);
                    userLocal.setExpiry(CommonObjects.getUser().getAvailibility().getId());
                    CommonMethods.showProgressDialog(getActivity());
                    AppHandlerNew.editAvailabiltiy(userLocal, new AppHandlerNew.EditAvailabilityListner() {
                        @Override
                        public void onEditAvailability(EditAvailability editAvailability) {
                            switch (editAvailability.getMessage()) {
                                case "success":
                                    Toast.makeText(getActivity(), "Your Availability is updated successfully", Toast.LENGTH_SHORT).show();
                                    CommonObjects.getUser().getAvailibility().setLongitude(lOng);
                                    CommonObjects.getUser().getAvailibility().setLatitude(lat);
                                    CommonObjects.getUser().getAvailibility().setRadius(mapRadius);
                                    CommonObjects.getUser().getAvailibility().setRate(etDailyRateTo.getText().toString());
                                    CommonObjects.getUser().getAvailibility().setFromDate(dateFrom + " " + timeFrom);
                                    CommonObjects.getUser().getAvailibility().setToDate(dateTo + " " + timeTo);
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
            }
        });
        tvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerFrom();
            }
        });
        tvTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerFrom();
            }
        });
        tvTimeTwoFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerFrom();
            }
        });
        tvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerTo();
            }
        });
        tvTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerTo();
            }
        });
        tvTimeTwoTo.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(getActivity(), "Please Select your location", Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        if (location.isEmpty()) {
//            etLocation.setError("enter a valid location");
//            valid = false;
//        } else {
//            etLocation.setError(null);
//        }
//        if (!isDatePicked) {
//            valid = false;
//            Toast.makeText(getActivity(), "Please set your availability", Toast.LENGTH_SHORT).show();
//        }
//        if (!isTimePicked) {
//            Toast.makeText(getActivity(), "Please set your availability", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
        return valid;
    }

    private void showDatePickerFrom() {
        DialogFragment newFragment = new DatePickerFragmentFrom();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//        isDatePicked = true;
    }

    private void showDatePickerTo() {
        DialogFragment newFragment = new DatePickerFragmentTo();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//        isDatePicked = true;
    }

    private void showTimePickerFrom() {
        DialogFragment newFragment = new TimePickerFragmentFrom();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
//        isTimePicked = true;
    }

    private void showTimePickerTo() {
        DialogFragment newFragment = new TimePickerFragmentTo();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
//        isTimePicked = true;
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
            int monthLocal = month + 1;
            ProviderAvailibilityFragment.getTvDateFrom().setText(day + "/" + monthLocal + "/" + year);
            dateFrom = year + "-" + monthLocal + "-" + day;
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
            int monthLocal = month + 1;
            ProviderAvailibilityFragment.getTvDateTo().setText(day + "/" + monthLocal + "/" + year);
            dateTo = year + "-" + monthLocal + "-" + day;

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
            ProviderAvailibilityFragment.getTvTimeFrom().setText(hourOfDay + " : " + minutes);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            ProviderAvailibilityFragment.getTvTimeTwoFrom().setText(AM_PM);
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
            ProviderAvailibilityFragment.getTvTimeTo().setText(hourOfDay + " : " + minutes);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            ProviderAvailibilityFragment.getTvTimeTwoTo().setText(AM_PM);
            timeTo = hourOfDay + ":" + minute + ":00";

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            etDailyRateFrom.setText(CommonObjects.getUser().getAvailibility().getRate());
            etDailyRateTo.setText(CommonObjects.getUser().getAvailibility().getRate());
            radiusSeekBar.setProgress(Integer.valueOf(CommonObjects.getUser().getAvailibility().getRadius()));
            lat = CommonObjects.getUser().getAvailibility().getLatitude();
            lOng = CommonObjects.getUser().getAvailibility().getLongitude();
            String dateTimeTo = CommonObjects.getUser().getAvailibility().getToDate();
            String[] temp=dateTimeTo.split(" ");
            dateTo = temp[0];
            timeTo  = temp[1];
            String dateTimeFrom = CommonObjects.getUser().getAvailibility().getFromDate();
            String[] temp1=dateTimeFrom.split(" ");
            dateFrom = temp1[0];
            timeFrom  = temp1[1];
            mapRadius = CommonObjects.getUser().getAvailibility().getRadius();
            String dateFromLocal = CommonObjects.getUser().getAvailibility().getFromDate();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy' 'KK:mm a",Locale.getDefault());
            String AM_FROM_DATE=outputFormat.format(inputFormat.parse(dateFromLocal));
            String[] parts = AM_FROM_DATE.split(" ");
            String dateFrom = parts[0]; // 004
            String timeFrom = parts[1]; // 034556
            String AM_PM_FROM = parts[2]; // 034556

            tvDateFrom.setText(dateFrom);
            tvTimeFrom.setText(timeFrom);
            tvTimeTwoFrom.setText(AM_PM_FROM);
            String dateToLocal = CommonObjects.getUser().getAvailibility().getToDate();
            SimpleDateFormat inputFormatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            SimpleDateFormat outputFormatTO = new SimpleDateFormat("dd-MM-yyyy' 'KK:mm a",Locale.getDefault());
            String AM_FROM_TO=outputFormatTO.format(inputFormatTo.parse(dateToLocal));
            Log.d("**Date",AM_FROM_TO);
            String[] partss = AM_FROM_TO.split(" ");
            String dateTo = partss[0]; // 004
            String timeTo = partss[1]; // 034556
            String AM_TO = partss[2];
            tvDateTo.setText(dateTo);
            tvTimeTo.setText(timeTo);
            tvTimeTwoTo.setText(AM_TO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}