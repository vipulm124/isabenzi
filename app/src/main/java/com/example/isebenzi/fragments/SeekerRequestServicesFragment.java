package com.example.isebenzi.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.activities.AssessmentSelectActivity;
import com.example.isebenzi.activities.RegisterAvailibilityActivity;
import com.example.isebenzi.activities.SeekerSearchResultsActivity;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.SearchResultsResponse;
import com.example.isebenzi.business.objects.SignUpResponse;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.Calendar;

public class SeekerRequestServicesFragment extends Fragment {
    public static TextView tvDatePicekrFrom;
    public static TextView tvTimePickerFrom;
    public static TextView tvTimePickerTwoFrom;
    public static TextView tvDatePicekrTo;
    public static TextView tvTimePickerTo;
    public static TextView tvTimePickerTwoTo;
    private Button btnPlus;
    private Button btnMinus;
    private TextView tvNoOfPersonRequired;
    private Button btnSearch;
    private GoogleMap mGoogleMap;
    MapView mMapView;
    private RadioGroup rGQuestionOne;
    private EditText etFeesPerDay;
//    private EditText etFeesPerHour;
    private String lOng = "";
    private String lat = "";

    public static String dateFrom;
    public static String timeFrom;
    public static String dateTo;
    public static String timeTo;
    private boolean isDatePicked = false;
    private boolean isTimePicked = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seeker_request_services, container, false);
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
                        System.out.println(point.latitude + "---" + point.longitude);
                        lOng = String.valueOf(point.longitude);
                        lat = String.valueOf(point.latitude);
//                        System.out.println(point.latitude+"---"+ point.longitude);
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

    private void init(final View view) {
        btnPlus = (Button) view.findViewById(R.id.btnPlus);
        btnMinus = (Button) view.findViewById(R.id.btnMinus);
        tvNoOfPersonRequired = (TextView) view.findViewById(R.id.tvNoOfPersonRequired);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        rGQuestionOne = (RadioGroup) view.findViewById(R.id.rGQuestionOne);
        etFeesPerDay = (EditText) view.findViewById(R.id.etFeesPerDay);
//        etFeesPerHour = (EditText) view.findViewById(R.id.etFeesPerHour);

        tvDatePicekrFrom = (TextView) view.findViewById(R.id.tvDatePicekrFrom);
        tvTimePickerFrom = (TextView) view.findViewById(R.id.tvTimePickerFrom);
        tvTimePickerTwoFrom = (TextView) view.findViewById(R.id.tvTimePickerTwoFrom);
        tvDatePicekrTo = (TextView) view.findViewById(R.id.tvDatePicekrTo);
        tvTimePickerTo = (TextView) view.findViewById(R.id.tvTimePickerTo);
        tvTimePickerTwoTo = (TextView) view.findViewById(R.id.tvTimePickerTwoTo);

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String radiovalue = ((RadioButton)view.findViewById(rGQuestionOne.getCheckedRadioButtonId())).getText().toString();
                    final UserLocal userLocal = new UserLocal();
                    userLocal.setPerDayFees(etFeesPerDay.getText().toString());
                    userLocal.setPerHourFees("0");
                    userLocal.setTo(dateTo + " " + timeTo);
                    userLocal.setFrom(dateFrom + " " + timeFrom);
                    userLocal.setLongitude(lOng);
                    userLocal.setLatitude(lat);
                    userLocal.setNoOfPersons(tvNoOfPersonRequired.getText().toString());
                    userLocal.setOccupation(radiovalue);
                    CommonMethods.showProgressDialog(getActivity());
                    AppHandlerNew.seekerJobSearch(userLocal, new AppHandlerNew.SearchJobListener() {
                        @Override
                        public void onSearchJobs(SearchResultsResponse searchResultsResponse) {
                            if (searchResultsResponse != null) {
                                CommonObjects.setProviders(searchResultsResponse.getProviders());
                                Gson gson=new Gson();
                                String userLocalString=gson.toJson(userLocal);
                                CommonMethods.callAnActivityWithParameter(getActivity(), SeekerSearchResultsActivity.class,"userLocalString",userLocalString);
                            }
                            CommonMethods.hideProgressDialog();
                        }
                    });
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvNoOfPersonRequired.getText().toString());
                count++;
                if (count != 10)
                    tvNoOfPersonRequired.setText(String.valueOf(count));
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvNoOfPersonRequired.getText().toString());
                count--;
                if (count != 0)
                    tvNoOfPersonRequired.setText(String.valueOf(count));
            }
        });
    }

    private void showDatePickerFrom() {
        DialogFragment newFragment = new DatePickerFragmentFrom();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        isDatePicked = true;
    }

    private void showDatePickerTo() {
        DialogFragment newFragment = new DatePickerFragmentTo();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        isDatePicked = true;
    }

    private void showTimePickerFrom() {
        DialogFragment newFragment = new TimePickerFragmentFrom();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
        isTimePicked=true;
    }

    private void showTimePickerTo() {
        DialogFragment newFragment = new TimePickerFragmentTo();
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
        isTimePicked=true;
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
            int time = c.get(Calendar.HOUR);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),R.style.DialogThemeSeeker, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            SeekerRequestServicesFragment.getTvDatePicekrFrom().setText(day + "/" + month + "/" + year);
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
            int time = c.get(Calendar.HOUR);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),R.style.DialogThemeSeeker, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            SeekerRequestServicesFragment.getTvDatePicekrTo().setText(day + "/" + month + "/" + year);
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
            return new TimePickerDialog(getActivity(),R.style.DialogThemeSeeker, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String minutes = String.valueOf(minute);
            if (minutes.equals("0"))
                minutes = "00";
            SeekerRequestServicesFragment.getTvTimePickerFrom().setText(hourOfDay + " : " + minutes);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            SeekerRequestServicesFragment.getTvTimePickerTwoFrom().setText(AM_PM);
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
            return new TimePickerDialog(getActivity(), R.style.DialogThemeSeeker, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String minutes = String.valueOf(minute);
            if (minutes.equals("0"))
                minutes = "00";
            SeekerRequestServicesFragment.getTvTimePickerTo().setText(hourOfDay + " : " + minutes);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            SeekerRequestServicesFragment.getTvTimePickerTwoTo().setText(AM_PM);
            timeTo = hourOfDay + ":" + minute + ":00";

        }
    }

    public boolean validate() {
        boolean valid = true;

        String perDay = etFeesPerDay.getText().toString();
//        String perHour = etFeesPerHour.getText().toString();

        if (perDay.isEmpty()) {
            etFeesPerDay.setError("enter per day fees");
            valid = false;
        } else {
            etFeesPerDay.setError(null);
        }
//        if (perHour.isEmpty()) {
//            etFeesPerHour.setError("enter per hour fees");
//            valid = false;
//        } else {
//            etFeesPerHour.setError(null);
//        }
        if (!isDatePicked) {
            valid = false;
            Toast.makeText(getActivity(), "Please set your date of job", Toast.LENGTH_SHORT).show();
        }
        if (!isTimePicked) {
            Toast.makeText(getActivity(), "Please set your time of job", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(rGQuestionOne.getCheckedRadioButtonId()==-1){
            Toast.makeText(getActivity(), "Please select occupation", Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        if (lOng.equals("")){
//            Toast.makeText(getActivity(), "Please select your location by taping the map", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
        return valid;
    }
}
