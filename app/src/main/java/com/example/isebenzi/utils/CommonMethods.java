package com.example.isebenzi.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethods {
    private static ProgressDialog dialog;
    private static Toast toast;
    private static final double ASSUMED_INIT_LATLNG_DIFF = 1.0;
    private static final float ACCURACY = 0.01f;
    private static Bitmap mask = null;
    private static Bitmap original = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
//    public static LatLngBounds boundsWithCenterAndLatLngDistance(LatLng center, float latDistanceInMeters, float lngDistanceInMeters) {
//        latDistanceInMeters /= 2;
//        lngDistanceInMeters /= 2;
//        LatLngBounds.Builder builder = LatLngBounds.builder();
//        float[] distance = new float[1];
//        {
//            boolean foundMax = false;
//            double foundMinLngDiff = 0;
//            double assumedLngDiff = ASSUMED_INIT_LATLNG_DIFF;
//            do {
//                Location.distanceBetween(center.latitude, center.longitude, center.latitude, center.longitude + assumedLngDiff, distance);
//                float distanceDiff = distance[0] - lngDistanceInMeters;
//                if (distanceDiff < 0) {
//                    if (!foundMax) {
//                        foundMinLngDiff = assumedLngDiff;
//                        assumedLngDiff *= 2;
//                    } else {
//                        double tmp = assumedLngDiff;
//                        assumedLngDiff += (assumedLngDiff - foundMinLngDiff) / 2;
//                        foundMinLngDiff = tmp;
//                    }
//                } else {
//                    assumedLngDiff -= (assumedLngDiff - foundMinLngDiff) / 2;
//                    foundMax = true;
//                }
//            } while (Math.abs(distance[0] - lngDistanceInMeters) > lngDistanceInMeters * ACCURACY);
//            LatLng east = new LatLng(center.latitude, center.longitude + assumedLngDiff);
//            builder.include(east);
//            LatLng west = new LatLng(center.latitude, center.longitude - assumedLngDiff);
//            builder.include(west);
//        }
//        {
//            boolean foundMax = false;
//            double foundMinLatDiff = 0;
//            double assumedLatDiffNorth = ASSUMED_INIT_LATLNG_DIFF;
//            do {
//                Location.distanceBetween(center.latitude, center.longitude, center.latitude + assumedLatDiffNorth, center.longitude, distance);
//                float distanceDiff = distance[0] - latDistanceInMeters;
//                if (distanceDiff < 0) {
//                    if (!foundMax) {
//                        foundMinLatDiff = assumedLatDiffNorth;
//                        assumedLatDiffNorth *= 2;
//                    } else {
//                        double tmp = assumedLatDiffNorth;
//                        assumedLatDiffNorth += (assumedLatDiffNorth - foundMinLatDiff) / 2;
//                        foundMinLatDiff = tmp;
//                    }
//                } else {
//                    assumedLatDiffNorth -= (assumedLatDiffNorth - foundMinLatDiff) / 2;
//                    foundMax = true;
//                }
//            } while (Math.abs(distance[0] - latDistanceInMeters) > latDistanceInMeters * ACCURACY);
//            LatLng north = new LatLng(center.latitude + assumedLatDiffNorth, center.longitude);
//            builder.include(north);
//        }
//        {
//            boolean foundMax = false;
//            double foundMinLatDiff = 0;
//            double assumedLatDiffSouth = ASSUMED_INIT_LATLNG_DIFF;
//            do {
//                Location.distanceBetween(center.latitude, center.longitude, center.latitude - assumedLatDiffSouth, center.longitude, distance);
//                float distanceDiff = distance[0] - latDistanceInMeters;
//                if (distanceDiff < 0) {
//                    if (!foundMax) {
//                        foundMinLatDiff = assumedLatDiffSouth;
//                        assumedLatDiffSouth *= 2;
//                    } else {
//                        double tmp = assumedLatDiffSouth;
//                        assumedLatDiffSouth += (assumedLatDiffSouth - foundMinLatDiff) / 2;
//                        foundMinLatDiff = tmp;
//                    }
//                } else {
//                    assumedLatDiffSouth -= (assumedLatDiffSouth - foundMinLatDiff) / 2;
//                    foundMax = true;
//                }
//            } while (Math.abs(distance[0] - latDistanceInMeters) > latDistanceInMeters * ACCURACY);
//            LatLng south = new LatLng(center.latitude - assumedLatDiffSouth, center.longitude);
//            builder.include(south);
//        }
//        return builder.build();
//    }

    public static void showProgressDialog(Context nContext) {
        try {
            hideProgressDialog();
            dialog = new ProgressDialog(nContext, R.style.NewDialog);
//            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            dialog.setContentView(R.layout.dialog_loading);
        } catch (Exception e) {
        }
    }

    public static void hideProgressDialog() {
        try {
            if (dialog != null)
                dialog.dismiss();
        } catch (Exception e) {

        }
    }

    public static Dialog showConfirmationDialog(Context nContext, int layout, int style
    ) {
        View view = createView(nContext,
                layout, null);
//        Button btnRestart=(Button)view.findViewById(R.id.btnRestart);
        Dialog nDialog = new Dialog(nContext, style);

        nDialog.setContentView(view);
        nDialog.show();
        return nDialog;
    }

    public static void callFragmentWithParameter(Fragment nFragment, int view, Bundle bundle, int enterAnim, int exitAnim, Context context) {
        FragmentManager fm;
//        if (nFragment.getArguments()==null)
        nFragment.setArguments(bundle);
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        fragmentTransaction.replace(view, nFragment);
        fragmentTransaction.addToBackStack("fragA");
        fragmentTransaction.commit();
    }

    public static void callFragmentWithParameterNew(Fragment nFragment, int view, Bundle bundle, int enterAnim, int exitAnim, Context context) {
        FragmentManager fm;
        nFragment.setArguments(bundle);
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        fragmentTransaction.add(view, nFragment);
        fragmentTransaction.addToBackStack("fragA");
        fragmentTransaction.commit();
    }
//    public static void callFragment(Fragment nFragment, int view,int enterAnim,int exitAnim,int popEnterAnim,int popExitAnim,Context context,boolean isBack) {
//        FragmentManager fm;
//        fm = ((FragmentActivity)context).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
//        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim,popEnterAnim,popExitAnim);
//        fragmentTransaction.replace(view, nFragment);
//        if(isBack)
//        {
//            fragmentTransaction.addToBackStack(null);
//        }
//        fragmentTransaction.commit();
//    }

    public static void callFragment(Fragment nFragment, int view, int enterAnim, int exitAnim, Context context) {
        FragmentManager fm;
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        fragmentTransaction.replace(view, nFragment);
        fragmentTransaction.commit();
    }

    public static void callAddFragment(Fragment nFragment, int view, int enterAnim, int exitAnim, Context context) {
        FragmentManager fm;
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        fragmentTransaction.add(view, nFragment);
        fragmentTransaction.addToBackStack("fragA");
        fragmentTransaction.commit();
    }

    public static void callAddFragmentWithParameters(Fragment nFragment, int view, Bundle bundle, int enterAnim, int exitAnim, Context context) {
        FragmentManager fm;
        nFragment.setArguments(bundle);
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        fragmentTransaction.add(view, nFragment);
        fragmentTransaction.addToBackStack("fragA");
        fragmentTransaction.commit();
    }

    public static void removeFragment(Fragment nFragment, int enterAnim, int exitAnim, Context context) {
        FragmentManager fm;
        fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        fragmentTransaction.remove(nFragment);
        fragmentTransaction.commit();
    }

    public static String loadJSONFromAsset(String fileName, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

//    public static void showToast(String message, int toastDuration) {
//        try {
//            cancelToast();
////            View v = createView(CommonObjects.getContext(), R.layout.toast_layout, null);
////            TextView nTextView = (TextView) v.findViewById(R.id.tvToast);
////            nTextView.setText(message);
//            toast = Toast.makeText(CommonObjects.getContext(), message, toastDuration);
//            toast.setGravity(Gravity.CENTER, 0, 0);
////            toast.setView(v);
//            toast.show();
//        } catch (Exception e) {
//        }
//    }

    public static void cancelToast() {
        try {
            if (toast != null) {
                toast.cancel();
            }
        } catch (Exception e) {
        }
    }

    public static void showNotification(Context nContext,
                                        Class<?> nActivityClass, String title, String message) {
        try {
//			Intent intent = new Intent(nContext, nActivityClass);
//			NotificationManager notificationManager = (NotificationManager) nContext
//					.getSystemService(Context.NOTIFICATION_SERVICE);
//			PendingIntent pendingIntent = PendingIntent.getActivity(nContext,
//					0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//			NotificationCompat.Builder builder = new NotificationCompat.Builder(
//					nContext).setSmallIcon(R.drawable.ic_launcher)
//					.setContentTitle(title).setContentText(message)
//					.setContentIntent(pendingIntent).setAutoCancel(true);
//			notificationManager.notify(1, builder.build());
        } catch (Exception e) {
        }
    }

    public static void hideSoftKeyboard(View v, Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static void callAnActivity(Context newContext,
                                      Class<?> newActivityClass) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newContext.startActivity(newIntent);
        } catch (Exception e) {
        }
    }

    public static void callAnActivityNew(Context newContext,
                                         Class<?> newActivityClass) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            newContext.startActivity(newIntent);
        } catch (Exception e) {
        }
    }

    public static void callAnActivityForResult(Context newContext,
                                               Class<?> newActivityClass, int requestCode) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {
        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag, String value) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag, value);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag1, String value1, String tag2, String value2) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag1, String value1, String tag2, String value2, String tag3, String value3) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag1, String value1, String tag2, String value2, String tag3, String value3, String tag4, String value4) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            newIntent.putExtra(tag4, value4);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag1, String value1, String tag2, String value2, String tag3, String value3, String tag4, String value4, String tag5, String value5) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            newIntent.putExtra(tag4, value4);
            newIntent.putExtra(tag5, value5);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag1, String value1, String tag2, String value2, String tag3, String value3, String tag4, String value4, String tag5, String value5, String tag6, String value6) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            newIntent.putExtra(tag4, value4);
            newIntent.putExtra(tag5, value5);
            newIntent.putExtra(tag6, value6);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityForResultWithParameter(Context newContext,
                                                            Class<?> newActivityClass, int requestCode, String tag1, String value1, String tag2, String value2, String tag3, String value3, String tag4, String value4
            , String tag5, String value5, String tag6, String value6, String tag7, String value7) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            newIntent.putExtra(tag4, value4);
            newIntent.putExtra(tag5, value5);
            newIntent.putExtra(tag6, value6);
            newIntent.putExtra(tag7, value7);
            ((Activity) newContext).startActivityForResult(newIntent, requestCode);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityWithParameter(Context newContext,
                                                   Class<?> newActivityClass, String tag, String value) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag, value);
            newContext.startActivity(newIntent);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityWithParameter(Context newContext,
                                                   Class<?> newActivityClass, String tag1, String value1, String tag2, String value2) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newContext.startActivity(newIntent);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityWithParameter(Context newContext,
                                                   Class<?> newActivityClass, String tag1, String value1, String tag2, String value2, String tag3, String value3) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            newContext.startActivity(newIntent);
        } catch (Exception e) {

        }
    }

    public static void callAnActivityWithParameter(Context newContext,
                                                   Class<?> newActivityClass, String tag1, String value1, String tag2, String value2, String tag3, String value3, String tag4, String value4) {
        try {
            Intent newIntent = new Intent(newContext, newActivityClass);
            newIntent.putExtra(tag1, value1);
            newIntent.putExtra(tag2, value2);
            newIntent.putExtra(tag3, value3);
            newIntent.putExtra(tag4, value4);
            newContext.startActivity(newIntent);
        } catch (Exception e) {

        }
    }

    public static View createView(Context context, int layout, ViewGroup parent) {
        try {
            LayoutInflater newLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return newLayoutInflater.inflate(layout, parent, false);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getStringPreference(Context nContext,
                                             String preferenceName, String preferenceItem, String defaultValue) {
        try {
            SharedPreferences nPreferences;
            nPreferences = nContext.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
            return nPreferences.getString(preferenceItem, defaultValue);
        } catch (Exception e) {
            return "";
        }
    }

    public static int getIntPreference(Context nContext,
                                       String preferenceName, String preferenceItem, int deafaultValue) {
        try {
            SharedPreferences nPreferences;
            nPreferences = nContext.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
            return nPreferences.getInt(preferenceItem, deafaultValue);
        } catch (Exception e) {
            return deafaultValue;
        }
    }

    public static Boolean getBooleanPreference(Context nContext,
                                               String preferenceName, String preferenceItem,
                                               Boolean defaultValue) {
        try {
            SharedPreferences nPreferences;
            nPreferences = nContext.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
            return nPreferences.getBoolean(preferenceItem, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void setStringPreference(Context nContext,
                                           String preferenceName, String preferenceItem,
                                           String preferenceItemValue) {
        try {
            SharedPreferences nPreferences;
            nPreferences = nContext.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
            Editor nEditor = nPreferences.edit();
            nEditor.putString(preferenceItem, preferenceItemValue);
            nEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setIntPreference(Context nContext,
                                        String preferenceName, String preferenceItem,
                                        int preferenceItemValue) {
        try {
            SharedPreferences nPreferences;
            nPreferences = nContext.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
            Editor nEditor = nPreferences.edit();
            nEditor.putInt(preferenceItem, preferenceItemValue);
            nEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBooleanPreference(Context nContext,
                                            String preferenceName, String preferenceItem,
                                            Boolean preferenceItemValue) {
        try {
            SharedPreferences nPreferences;
            nPreferences = nContext.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
            Editor nEditor = nPreferences.edit();
            nEditor.putBoolean(preferenceItem, preferenceItemValue);
            nEditor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String removeSpacing(String phoneNumber) {
        try {
            phoneNumber = phoneNumber.replace("-", "");
            phoneNumber = phoneNumber.replace(" ", "");
            removeNonDigits(phoneNumber);
            if (phoneNumber.length() >= 11) {
                phoneNumber = phoneNumber.substring(phoneNumber.length() - 11);
            }
            return phoneNumber;
        } catch (Exception e) {
            return phoneNumber;
        }
    }

    public static String removeNonDigits(String text) {
        try {
            int length = text.length();
            StringBuffer buffer = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                char ch = text.charAt(i);
                if (Character.isDigit(ch)) {
                    buffer.append(ch);
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            return text;
        }
    }

    public static int getBooleanInt(boolean bol) {
        try {
            if (bol)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean getBoolean(int bolInt) {
        try {
            if (bolInt == 1)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context nContext) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) nContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isWifiConnected(Context nContext) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) nContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            }
            return networkInfo == null ? false : networkInfo.isConnected();
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static int getRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static int getFontSize(Activity activity, float var) {

        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);

        // lets try to get them back a font size realtive to the pixel width of the screen
        final float WIDE = activity.getResources().getDisplayMetrics().heightPixels;
        int valueWide = (int) (WIDE / 32.0f / (dMetrics.scaledDensity));
        return (int) ((float) (valueWide * var));
    }

    public static int getDeviceWidth(Context nConext) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) nConext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getDeviceHeight(Context nConext) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) nConext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static boolean isEmailValid(String email) {
        try {
            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    public static void threadSleep(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void fadeOut(final View view) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            AlphaAnimation alpha = new AlphaAnimation(1f, 0.5f);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        } else {
            view.animate()
                    .alpha(0.5f)
                    .setDuration(0)
                    .setListener(null);
        }
    }

    public static void fadeIn(View view) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation alpha = new AlphaAnimation(0.5f, 1f);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        } else {
            view.setAlpha(0.5f);
            view.animate()
                    .alpha(1f)
                    .setDuration(0)
                    .setListener(null);
        }
    }

    public static boolean checkForNetworkProvider(
            LocationManager nLocationManager, Context nContext) {
        if (nLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || (CommonMethods.isNetworkAvailable(nContext) || CommonMethods
                .isWifiConnected(nContext))) {
            return true;
        }
        return false;
    }


    public static String getPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String code = tm.getSimCountryIso();
        if (code.equals("")) {
            code = tm.getNetworkCountryIso();
        }
        if (country2phone.get(code.toUpperCase()) != null) {
            return country2phone.get(code.toUpperCase());
        }
        return "+";
    }

    public static Map<String, String> getAll() {
        return country2phone;
    }

    private static Map<String, String> country2phone = new HashMap<String, String>();

    static {
        country2phone.put("AF", "+93");
        country2phone.put("AL", "+355");
        country2phone.put("DZ", "+213");
        country2phone.put("AD", "+376");
        country2phone.put("AO", "+244");
        country2phone.put("AG", "+1-268");
        country2phone.put("AR", "+54");
        country2phone.put("AM", "+374");
        country2phone.put("AU", "+61");
        country2phone.put("AT", "+43");
        country2phone.put("AZ", "+994");
        country2phone.put("BS", "+1-242");
        country2phone.put("BH", "+973");
        country2phone.put("BD", "+880");
        country2phone.put("BB", "+1-246");
        country2phone.put("BY", "+375");
        country2phone.put("BE", "+32");
        country2phone.put("BZ", "+501");
        country2phone.put("BJ", "+229");
        country2phone.put("BT", "+975");
        country2phone.put("BO", "+591");
        country2phone.put("BA", "+387");
        country2phone.put("BW", "+267");
        country2phone.put("BR", "+55");
        country2phone.put("BN", "+673");
        country2phone.put("BG", "+359");
        country2phone.put("BF", "+226");
        country2phone.put("BI", "+257");
        country2phone.put("KH", "+855");
        country2phone.put("CM", "+237");
        country2phone.put("CA", "+1");
        country2phone.put("CV", "+238");
        country2phone.put("CF", "+236");
        country2phone.put("TD", "+235");
        country2phone.put("CL", "+56");
        country2phone.put("CN", "+86");
        country2phone.put("CO", "+57");
        country2phone.put("KM", "+269");
        country2phone.put("CD", "+243");
        country2phone.put("CG", "+242");
        country2phone.put("CR", "+506");
        country2phone.put("CI", "+225");
        country2phone.put("HR", "+385");
        country2phone.put("CU", "+53");
        country2phone.put("CY", "+357");
        country2phone.put("CZ", "+420");
        country2phone.put("DK", "+45");
        country2phone.put("DJ", "+253");
        country2phone.put("DM", "+1-767");
        country2phone.put("DO", "+1-809and1-829");
        country2phone.put("EC", "+593");
        country2phone.put("EG", "+20");
        country2phone.put("SV", "+503");
        country2phone.put("GQ", "+240");
        country2phone.put("ER", "+291");
        country2phone.put("EE", "+372");
        country2phone.put("ET", "+251");
        country2phone.put("FJ", "+679");
        country2phone.put("FI", "+358");
        country2phone.put("FR", "+33");
        country2phone.put("GA", "+241");
        country2phone.put("GM", "+220");
        country2phone.put("GE", "+995");
        country2phone.put("DE", "+49");
        country2phone.put("GH", "+233");
        country2phone.put("GR", "+30");
        country2phone.put("GD", "+1-473");
        country2phone.put("GT", "+502");
        country2phone.put("GN", "+224");
        country2phone.put("GW", "+245");
        country2phone.put("GY", "+592");
        country2phone.put("HT", "+509");
        country2phone.put("HN", "+504");
        country2phone.put("HU", "+36");
        country2phone.put("IS", "+354");
        country2phone.put("IN", "+91");
        country2phone.put("ID", "+62");
        country2phone.put("IR", "+98");
        country2phone.put("IQ", "+964");
        country2phone.put("IE", "+353");
        country2phone.put("IL", "+972");
        country2phone.put("IT", "+39");
        country2phone.put("JM", "+1-876");
        country2phone.put("JP", "+81");
        country2phone.put("JO", "+962");
        country2phone.put("KZ", "+7");
        country2phone.put("KE", "+254");
        country2phone.put("KI", "+686");
        country2phone.put("KP", "+850");
        country2phone.put("KR", "+82");
        country2phone.put("KW", "+965");
        country2phone.put("KG", "+996");
        country2phone.put("LA", "+856");
        country2phone.put("LV", "+371");
        country2phone.put("LB", "+961");
        country2phone.put("LS", "+266");
        country2phone.put("LR", "+231");
        country2phone.put("LY", "+218");
        country2phone.put("LI", "+423");
        country2phone.put("LT", "+370");
        country2phone.put("LU", "+352");
        country2phone.put("MK", "+389");
        country2phone.put("MG", "+261");
        country2phone.put("MW", "+265");
        country2phone.put("MY", "+60");
        country2phone.put("MV", "+960");
        country2phone.put("ML", "+223");
        country2phone.put("MT", "+356");
        country2phone.put("MH", "+692");
        country2phone.put("MR", "+222");
        country2phone.put("MU", "+230");
        country2phone.put("MX", "+52");
        country2phone.put("FM", "+691");
        country2phone.put("MD", "+373");
        country2phone.put("MC", "+377");
        country2phone.put("MN", "+976");
        country2phone.put("ME", "+382");
        country2phone.put("MA", "+212");
        country2phone.put("MZ", "+258");
        country2phone.put("MM", "+95");
        country2phone.put("NA", "+264");
        country2phone.put("NR", "+674");
        country2phone.put("NP", "+977");
        country2phone.put("NL", "+31");
        country2phone.put("NZ", "+64");
        country2phone.put("NI", "+505");
        country2phone.put("NE", "+227");
        country2phone.put("NG", "+234");
        country2phone.put("NO", "+47");
        country2phone.put("OM", "+968");
        country2phone.put("PK", "+92");
        country2phone.put("PW", "+680");
        country2phone.put("PA", "+507");
        country2phone.put("PG", "+675");
        country2phone.put("PY", "+595");
        country2phone.put("PE", "+51");
        country2phone.put("PH", "+63");
        country2phone.put("PL", "+48");
        country2phone.put("PT", "+351");
        country2phone.put("QA", "+974");
        country2phone.put("RO", "+40");
        country2phone.put("RU", "+7");
        country2phone.put("RW", "+250");
        country2phone.put("KN", "+1-869");
        country2phone.put("LC", "+1-758");
        country2phone.put("VC", "+1-784");
        country2phone.put("WS", "+685");
        country2phone.put("SM", "+378");
        country2phone.put("ST", "+239");
        country2phone.put("SA", "+966");
        country2phone.put("SN", "+221");
        country2phone.put("RS", "+381");
        country2phone.put("SC", "+248");
        country2phone.put("SL", "+232");
        country2phone.put("SG", "+65");
        country2phone.put("SK", "+421");
        country2phone.put("SI", "+386");
        country2phone.put("SB", "+677");
        country2phone.put("SO", "+252");
        country2phone.put("ZA", "+27");
        country2phone.put("ES", "+34");
        country2phone.put("LK", "+94");
        country2phone.put("SD", "+249");
        country2phone.put("SR", "+597");
        country2phone.put("SZ", "+268");
        country2phone.put("SE", "+46");
        country2phone.put("CH", "+41");
        country2phone.put("SY", "+963");
        country2phone.put("TJ", "+992");
        country2phone.put("TZ", "+255");
        country2phone.put("TH", "+66");
        country2phone.put("TL", "+670");
        country2phone.put("TG", "+228");
        country2phone.put("TO", "+676");
        country2phone.put("TT", "+1-868");
        country2phone.put("TN", "+216");
        country2phone.put("TR", "+90");
        country2phone.put("TM", "+993");
        country2phone.put("TV", "+688");
        country2phone.put("UG", "+256");
        country2phone.put("UA", "+380");
        country2phone.put("AE", "+971");
        country2phone.put("GB", "+44");
        country2phone.put("US", "+1");
        country2phone.put("UY", "+598");
        country2phone.put("UZ", "+998");
        country2phone.put("VU", "+678");
        country2phone.put("VA", "+379");
        country2phone.put("VE", "+58");
        country2phone.put("VN", "+84");
        country2phone.put("YE", "+967");
        country2phone.put("ZM", "+260");
        country2phone.put("ZW", "+263");
        country2phone.put("GE", "+995");
        country2phone.put("TW", "+886");
        country2phone.put("AZ", "+374-97");
        country2phone.put("CY", "+90-392");
        country2phone.put("MD", "+373-533");
        country2phone.put("SO", "+252");
        country2phone.put("GE", "+995");
        country2phone.put("CX", "+61");
        country2phone.put("CC", "+61");
        country2phone.put("NF", "+672");
        country2phone.put("NC", "+687");
        country2phone.put("PF", "+689");
        country2phone.put("YT", "+262");
        country2phone.put("GP", "+590");
        country2phone.put("GP", "+590");
        country2phone.put("PM", "+508");
        country2phone.put("WF", "+681");
        country2phone.put("CK", "+682");
        country2phone.put("NU", "+683");
        country2phone.put("TK", "+690");
        country2phone.put("GG", "+44");
        country2phone.put("IM", "+44");
        country2phone.put("JE", "+44");
        country2phone.put("AI", "+1-264");
        country2phone.put("BM", "+1-441");
        country2phone.put("IO", "+246");
        country2phone.put("", "+357");
        country2phone.put("VG", "+1-284");
        country2phone.put("KY", "+1-345");
        country2phone.put("FK", "+500");
        country2phone.put("GI", "+350");
        country2phone.put("MS", "+1-664");
        country2phone.put("SH", "+290");
        country2phone.put("TC", "+1-649");
        country2phone.put("MP", "+1-670");
        country2phone.put("PR", "+1-787and1-939");
        country2phone.put("AS", "+1-684");
        country2phone.put("GU", "+1-671");
        country2phone.put("VI", "+1-340");
        country2phone.put("HK", "+852");
        country2phone.put("MO", "+853");
        country2phone.put("FO", "+298");
        country2phone.put("GL", "+299");
        country2phone.put("GF", "+594");
        country2phone.put("GP", "+590");
        country2phone.put("MQ", "+596");
        country2phone.put("RE", "+262");
        country2phone.put("AX", "+358-18");
        country2phone.put("AW", "+297");
        country2phone.put("AN", "+599");
        country2phone.put("SJ", "+47");
        country2phone.put("AC", "+247");
        country2phone.put("TA", "+290");
        country2phone.put("CS", "+381");
        country2phone.put("PS", "+970");
        country2phone.put("EH", "+212");
    }

    public static Bitmap createImageFromView(View view, Point size) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(size.x, size.y);
        view.layout(0, 0, size.x, size.y);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    public static Bitmap createImageFromMask(Context context, int maskImage, int originalImage) {
        if (mask == null) {
            mask = BitmapFactory.decodeResource(context.getResources(), maskImage);
        }
        if (original == null) {
            original = BitmapFactory.decodeResource(context.getResources(), originalImage);
        }
        original = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    public static Bitmap createImageFromMask(Context context, int maskImage, Bitmap original) {
        if (mask == null) {
            mask = BitmapFactory.decodeResource(context.getResources(), maskImage);
        }
        original = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    public static void setImage(ImageView nImageView, byte[] nImageBytes, Point size, boolean isRoundedCorners, int pixels) {
        new LoadImageTask(nImageView, nImageBytes, size, isRoundedCorners, pixels).execute();
    }

    public static void setImage(ImageView nImageView, byte[] nImageBytes, Point size) {
        new LoadImageTask(nImageView, nImageBytes, size).execute();
    }


    private static class LoadImageTask extends AsyncTask<String, Void, Boolean> {
        private ImageView nImageView;
        private byte[] mCameraData;
        private Bitmap nBitmap;
        private Point nSize;
        private boolean isRoundedCorners;
        private boolean isRounded;
        private int pixels;
        private String nImageString;
        private int rotate;

        public LoadImageTask(ImageView nImageView, String nImageString, Point nSize, boolean isRounded, int rotate) {
            this.nImageView = nImageView;
            this.nImageString = nImageString;
            this.nSize = nSize;
            this.isRounded = isRounded;
            this.rotate = rotate;
        }

        public LoadImageTask(ImageView nImageView, byte[] mCameraData, Point nSize, boolean isRoundedCorners, int pixels) {
            this.nImageView = nImageView;
            this.mCameraData = mCameraData;
            this.nSize = nSize;
            this.isRoundedCorners = isRoundedCorners;
            this.pixels = pixels;
        }

        public LoadImageTask(ImageView nImageView, byte[] mCameraData, Point nSize, boolean isRounded) {
            this.nImageView = nImageView;
            this.mCameraData = mCameraData;
            this.nSize = nSize;
            this.isRounded = isRounded;
        }

        public LoadImageTask(ImageView nImageView, byte[] mCameraData, Point nSize) {
            this.nImageView = nImageView;
            this.mCameraData = mCameraData;
            this.nSize = nSize;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (mCameraData != null) {
                nBitmap = loadBitmap(mCameraData);
            } else {
                nBitmap = loadBitmap(nImageString);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (nBitmap != null) {
                nImageView.setImageBitmap(nBitmap);
            }
        }

        private Bitmap loadBitmap(byte[] cameraData) {
            Bitmap bitmap = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length), nSize.y, nSize.x, true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            if (isRoundedCorners) {
                bitmap = getRoundedCornerBitmap(bitmap, pixels);
            } else if (isRounded) {
                bitmap = getRoundedShapeBitmap(bitmap);
            }
            Matrix nMatrix = new Matrix();
            nMatrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), nMatrix, true);
            return bitmap;
        }

        private Bitmap loadBitmap(String imageString) {
            Bitmap bitmap = null;
            if (!imageString.contains("http")) {
//                bitmap = Bitmap.createScaledBitmap(
//                        BitmapFactory.decodeFile(imageString), nSize.x, nSize.y, true);
                bitmap = lessResolution(imageString, nSize.x, nSize.y);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                bitmap = rotateBitmap(bitmap, getCameraPhotoOrientation(imageString));
                bitmap = rotateBitmap(bitmap, rotate);
            } else {
                try {
                    InputStream in = new URL(imageString).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    bitmap = Bitmap.createScaledBitmap(bitmap, nSize.x, nSize.y, true);
//                    bitmap = rotateBitmap(bitmap, 360f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bitmap != null) {
                if (isRoundedCorners) {
                    bitmap = getRoundedCornerBitmap(bitmap, pixels);
                } else if (isRounded) {
                    bitmap = getRoundedShapeBitmap(bitmap);
                }
                Matrix nMatrix = new Matrix();
//                nMatrix.postRotate(getCameraPhotoOrientation(imageString));
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), nMatrix, true);
            }
            return bitmap;
        }

        private Bitmap lessResolution(String filePath, int width, int height) {
            int reqHeight = height;
            int reqWidth = width;
            BitmapFactory.Options options = new BitmapFactory.Options();

            // First decode with inJustDecodeBounds=true to check dimensions
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeFile(filePath, options);
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                // Calculate ratios of height and width to requested height and width
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);

                // Choose the smallest ratio as inSampleSize value, this will guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            return inSampleSize;
        }

        public int getCameraPhotoOrientation(String imagePath) {
            int rotate = 0;
            try {
//                context.getContentResolver().notifyChange(imageUri, null);
//                File imageFile = new File(imagePath);

                ExifInterface exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rotate;
        }

        public Bitmap rotateBitmap(Bitmap source, float angle) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }

        private Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }

        private Bitmap getRoundedShapeBitmap(Bitmap bitmap) {
            int targetWidth = nSize.x;
            int targetHeight = nSize.y;
            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                    targetHeight, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(targetBitmap);
            Path path = new Path();
            path.addCircle(((float) targetWidth - 1) / 2,
                    ((float) targetHeight - 1) / 2,
                    (Math.min(((float) targetWidth),
                            ((float) targetHeight)) / 2),
                    Path.Direction.CCW);
            canvas.clipPath(path);
            Bitmap sourceBitmap = bitmap;
            canvas.drawBitmap(sourceBitmap,
                    new Rect(0, 0, sourceBitmap.getWidth(),
                            sourceBitmap.getHeight()),
                    new Rect(0, 0, targetWidth, targetHeight), null);
            return targetBitmap;
        }
    }

    public static void setImage(ImageView nImageView, String nImageString, Point size, boolean isRounded, int rotate) {
        new LoadImageTask(nImageView, nImageString, size, isRounded, rotate).execute();
    }

    public static void setImage(ImageView nImageView, byte[] mCameraData, Point nSize, boolean isRounded) {
        new LoadImageTask(nImageView, mCameraData, nSize, isRounded).execute();
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

//    public static boolean isValidUrl(String url) {
//        UrlValidator defaultValidator = new UrlValidator(); // default schemes
//        if (defaultValidator.isValid(url)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTodaysDate() {
        Calendar date = Calendar.getInstance();
        String hour = date.get(Calendar.HOUR) + "";
        String minute = date.get(Calendar.MINUTE) + "";
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        String dateToday = getDay(date.get(Calendar.DAY_OF_WEEK)) + ", " + date.get(Calendar.DAY_OF_MONTH) + " " + getMonthForInnerClass(date.get(Calendar.MONTH)) + " " + hour + ":" + minute + ", " + date.get(Calendar.YEAR);
        return dateToday;
    }

    public static String getDay(int id) {
        switch (id) {
            case 1:
                return "Sunday";

            case 2:
                return "Monday";

            case 3:
                return "Tuesday";

            case 4:
                return "Wednesday";

            case 5:
                return "Thursday";

            case 6:
                return "Friday";

            case 7:
                return "Saturday";

        }

        return "";
    }

    public static String getMonth(int id) {
        switch (id) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }

        return "";
    }

    public static String getMonthForInnerClass(int id) {
        switch (id + 1) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }

        return "";
    }

    public static String getDateFromTimestamp(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm / M dd");
        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((int) milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getDateFromTimestamp2(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((int) milliSeconds);
        return formatter.format(calendar.getTime());
    }


//    public static  double calculationByDistance(LatLng StartP, LatLng EndP) {
////        int Radius = 6371;// radius of earth in Km
//        double lat1 = StartP.latitude;
//        double lat2 = EndP.latitude;
//        double lon1 = StartP.longitude;
//        double lon2 = EndP.longitude;
//
////        double lat1 = 31.516968;
////        double lat2 = 31.524256;
////        double lon1 = 74.344373;
////        double lon2 = 74.346178;
//
////        double dLat = Math.toRadians(lat2 - lat1);
////        double dLon = Math.toRadians(lon2 - lon1);
////        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
////                + Math.cos(Math.toRadians(lat1))
////                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
////                * Math.sin(dLon / 2);
////        double c = 2 * Math.asin(Math.sqrt(a));
////        double valueResult = Radius * c;
////        double km = valueResult / 1;
////        DecimalFormat newFormat = new DecimalFormat("####");
////        int kmInDec = Integer.valueOf(newFormat.format(km));
////        double meter = valueResult % 1000;
////        return meter;
////        int meterInDec = Integer.valueOf(newFormat.format(meter));
////        float[] results = new float[1];
////        Location.distanceBetween(lat1, lon1,
////                lat2, lon2, results);
////        float meter=0;
////        if(results.length==1)
////        {
////            meter=results[0];
////        }
////        else if(results.length==2)
////        {
////            meter=results[1];
////        }
////        else if(results.length==3)
////        {
////            meter=results[2];
////        }
////        return meter;
////        Location l1 = new Location("");
////        Location l2 = new Location("");
////        l1.setLatitude(lat1);
////        l1.setLongitude(lon1);
////        l2.setLatitude(lat2);
////        l2.setLongitude(lat2);
////        return Math.round(l1.distanceTo(l2));
//
//        double earthRadius = 6371000; //meters
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLng = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        float dist = (float) (earthRadius * c);
//
//        return dist;
////        return meters;
//    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static Bitmap getImageFromText(String text, Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80, 55);
        tv.setLayoutParams(layoutParams);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tv.setMaxLines(1);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.TRANSPARENT);

        Bitmap testB;

        testB = Bitmap.createBitmap(80, 55, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(testB);
        tv.layout(0, 0, 80, 55);
        tv.draw(c);
        return testB;
    }

    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }

//    public static String getNullAsEmptyString(JsonElement jsonElement) {
//        return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
//    }
//
//    public static void loadBitmapFromUrl(String url, FutureCallback<Bitmap> bitmapLoadCallBack)
//    {
//        LoadBuilder<Builders.Any.B> loadBuilder = Ion.with(CommonObjects.getContext());
//        Builders.Any.B b = loadBuilder.load("POST",url);
//        b.uploadProgressBar(new ProgressBar(CommonObjects.getContext()));
//        b.asBitmap().setCallback(bitmapLoadCallBack);
//    }

//    public static ArrayList<Contact> readPhoneContacts(Context context) {
//        ArrayList<Contact> contactArrayList = new ArrayList<>();
//        try {
//            ContentResolver cr = context.getContentResolver();
//            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//            HashMap<String,String> contactEmails=new HashMap<>();
//            Contact contact;
//            while (phones.moveToNext()) {
//                String contactId = phones
//                        .getString(phones
//                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
//                String contactName = phones
//                        .getString(phones
//                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String contactNumber = phones
//                        .getString(phones
//                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                contact = new Contact(contactId,contactNumber, contactName);
//                contactArrayList.add(contact);
//            }
//            phones.close();
//            return contactArrayList;
//        } catch (Exception e) {
//            return contactArrayList;
//        }
//    }

//    public static Bitmap retrieveContactPhoto(Context context, String number) {
//        ContentResolver contentResolver = context.getContentResolver();
//        String contactId = null;
//        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
//
//        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};
//
//        Cursor cursor =
//                contentResolver.query(
//                        uri,
//                        projection,
//                        null,
//                        null,
//                        null);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
//            }
//            cursor.close();
//        }
//
//        Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.ic_contact_place_holder);
//
//        try {
//            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
//                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));
//
//            if (inputStream != null) {
//                photo = BitmapFactory.decodeStream(inputStream);
//                inputStream.close();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return photo;
//    }

    public static boolean setListViewHeightBasedOnItems(ListView listView, int numberChange) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int numberOfItems = 0;
            if (numberChange == 0) {
                numberOfItems = listAdapter.getCount();
            } else {
                numberOfItems = numberChange;
            }

            // Get total height of all items.
            if (numberOfItems != 0) {
                int totalItemsHeight = 0;
                View item = listAdapter.getView(0, null, listView);
                if (item != null)
                    item.measure(0, 0);
                totalItemsHeight = item.getMeasuredHeight() * numberOfItems;


                // Get total height of all item dividers.
                int totalDividersHeight = listView.getDividerHeight() *
                        (numberOfItems - 1);

                // Set list height.
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = totalItemsHeight + totalDividersHeight;
                listView.setLayoutParams(params);
                listView.requestLayout();
            }
            return true;
        } else {
            return false;
        }

    }


    public static void setupUI(final View view, final Context context) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(view, context);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView, context);
            }
        }
    }

    public static String getThumbnailPathForLocalFile(Activity context,
                                                      Uri fileUri) {
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA};

        long fileId = getFileId(context, fileUri);
        MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
        Cursor thumbCursor = null;
        try {
            thumbCursor = context.managedQuery(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                    thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + " = "
                            + fileId, null, null);

            if (thumbCursor.moveToFirst()) {
                String thumbPath = thumbCursor.getString(thumbCursor
                        .getColumnIndex(MediaStore.Video.Thumbnails.DATA));

                return thumbPath;
            }
        } finally {
        }
        return null;
    }

    public static long getFileId(Activity context, Uri fileUri) {
        String[] mediaColumns = {MediaStore.Video.Media._ID};

        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null,
                null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int id = cursor.getInt(columnIndex);

            return id;
        }

        return 0;
    }

//    public static void createNotification(Context context,int messageId, String title, String message, String alert) {
//
//        //PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, ReceiptsListActivity.class), 0);
//        //PendingIntent notificationIntent = PendingIntent.getBroadcast(context, Integer.parseInt(receipt.getReceiptId()), new Intent(context, ReceiptsListActivity.class), 0);
//        Intent intent = new Intent(context, ProviderDashboardActivity.class);
//        intent.putExtra(Constants.NotificationKeys.LOCAL_NOTIFICATION_MESSAGE_ID,messageId);
//        PendingIntent notificationIntent = PendingIntent.getActivity(context,(int) System.currentTimeMillis() ,intent, 0);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                /*.setSmallIcon(R.mipmap.receipt_notification_icon).*/
////                .setContent(new RemoteViews(context.getPackageName(), R.layout.activity_notification_layout))
//                .setSmallIcon(R.mipmap.ic_launcher).
//                        setContentTitle(title).
//                        setTicker(alert).
//                        setContentText(message);
//
//        mBuilder.setContentIntent(notificationIntent);
//        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
//        mBuilder.setAutoCancel(true);
//
//        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        mNotificationManager.notify(1, mBuilder.build());
//
//
//    }

    public static ArrayList<ArrayList<String>> getPackageValues() {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        list.add(new ArrayList<String>() {{
            add("3.29");
            add("Parcel");
            add("12");
        }});
        list.add(new ArrayList<String>() {{
            add("5.20");
            add("SmallFlatRateBox");
            add("70");
        }});
        list.add(new ArrayList<String>() {{
            add("11.30");
            add("MediumFlatRateBox");
            add("70");
        }});
        return list;
    }

    public static boolean checkDate(String date) {
        if (!date.equals("") && date.length() == 5) {
            if (date.charAt(0) >= '0'
                    && date.charAt(0) <= '9'
                    && date.charAt(1) >= '0'
                    && date.charAt(1) <= '9'
                    && date.charAt(2) == '/'
                    && date.charAt(3) >= '0'
                    && date.charAt(3) <= '9'
                    && date.charAt(4) >= '0'
                    && date.charAt(4) <= '9') {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Double> getPackage() {
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(3.29);
        list.add(5.20);
        list.add(11.30);
        return list;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public static void setGridViewDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        int rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > 5) {
            x = items / 5;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

    public static Bitmap decodeFile(File f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static byte[] decodeFileAsBytes(File f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap image = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    public static String getDateFormatted(String strMonth, String strDayOfMonth,
                                          String strYear) {
        try {
            if (strMonth.length() == 1)
                strMonth = "0" + strMonth;
            if (strDayOfMonth.length() == 1)
                strDayOfMonth = "0" + strDayOfMonth;
            return strDayOfMonth + "/" + strMonth + "/" + strYear;
        } catch (Exception e) {
            return "";
        }
    }
//
//    public static void callDatePicker(Context nContext, String date,
//                                      DatePickerDialog.OnDateSetListener datePickerListener, FragmentManager manager) {
//        try {
//            int day, month, year;
//            if (date.equals("")) {
//                Calendar calendar = Calendar.getInstance();
//                day = calendar.get(Calendar.DAY_OF_MONTH);
//                month = calendar.get(Calendar.MONTH);
//                year = calendar.get(Calendar.YEAR);
//            } else {
//                String[] nDate = date.split("/");
//                month = Integer.parseInt(nDate[0]) - 1;
//                day = Integer.parseInt(nDate[1]);
//                year = Integer.parseInt(nDate[2]);
//            }
//            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
//                    datePickerListener, year, month, day, false);
//            datePickerDialog.setVibrate(false);
//            datePickerDialog.setYearRange(1985, 2028);
//            datePickerDialog.setCloseOnSingleTapDay(false);
//            datePickerDialog.show(manager, "datepicker");
//        } catch (Exception e) {
//        }
//    }

    public static String getAuthToken(String token) {
        String accessToken = "";
        try {
            accessToken = Base64.encodeToString(token.getBytes("utf-8"), Base64.NO_WRAP | Base64.URL_SAFE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("Service_Response", "token_ " + accessToken);
        return accessToken;
    }

    public interface AsyncCompleteMethod {
        public void onComplete(Map<String, Object> result);
    }

//    public static void buyShipment(Shipment shipment, AsyncCompleteMethod onAsyncCompleteMethod) {
//        new BuyShipment(shipment, onAsyncCompleteMethod).execute();
//    }

//    private static class BuyShipment extends AsyncTask<String, Void, Boolean> {
//        private AsyncCompleteMethod onAsyncCompleteMethod;
//        private Shipment shipment;
//        private Map<String, Object> result = new HashMap<String, Object>();
//
//        public BuyShipment(Shipment shipment, AsyncCompleteMethod onAsyncCompleteMethod) {
//            this.shipment = shipment;
//            this.onAsyncCompleteMethod = onAsyncCompleteMethod;
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            EasyPost.apiKey = Constants.EASY_POST_KEY;
//            List<String> buyCarriers = new ArrayList<String>();
//            buyCarriers.add("USPS");
//            List<String> buyServices = new ArrayList<String>();
//            buyServices.add("PriorityMailInternational");
//            try {
//                shipment = shipment.buy(shipment.lowestRate(buyCarriers, buyServices));
//                result.put(Constants.KeyValues.SHIPMENT_ID, "");
//                result.put(Constants.KeyValues.SHIPMENT, shipment);
//            } catch (EasyPostException e1) {
//                e1.printStackTrace();
//                result.put(Constants.KeyValues.SHIPMENT, null);
//                result.put(Constants.KeyValues.SHIPMENT_ID, "");
//            }
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            onAsyncCompleteMethod.onComplete(this.result);
//        }
//    }
//
//    public static Map<String, Object> getDefaultAddressMap() {
//        Map<String, Object> defaultAddressMap = new HashMap<String, Object>();
//        defaultAddressMap.put(Constants.KeyValues.NAME, "VapeMail Team");
//        defaultAddressMap.put(Constants.KeyValues.STREET1, "1618 Sullivan ave");
//        defaultAddressMap.put(Constants.KeyValues.STREET2, "#498");
//        defaultAddressMap.put(Constants.KeyValues.CITY, "Daly City");
//        defaultAddressMap.put(Constants.KeyValues.COUNTRY, "US");
//        defaultAddressMap.put(Constants.KeyValues.STATE, "CA");
//        defaultAddressMap.put(Constants.KeyValues.ZIP, "94015");
//        defaultAddressMap.put(Constants.KeyValues.PHONE, "(408)594-9520");
//        defaultAddressMap.put(Constants.KeyValues.EMAIL, "mike@vapemail.com");
//        return defaultAddressMap;
//    }

//    public static void createShipment(Map<String, Object> fromAddressMap,
//                                      Map<String, Object> toAddressMap,
//                                      Map<String, Object> parcelMap,
//                                      AsyncCompleteMethod onAsyncCompleteMethod) {
//        new CreateShipment(fromAddressMap, toAddressMap, parcelMap, onAsyncCompleteMethod).execute();
//    }

//    private static class CreateShipment extends AsyncTask<String, Void, Boolean> {
//        private AsyncCompleteMethod onAsyncCompleteMethod;
//        private Map<String, Object> fromAddressMap;
//        private Map<String, Object> toAddressMap;
//        private Map<String, Object> parcelMap;
//        private Map<String, Object> result;
//
//        public CreateShipment(Map<String, Object> fromAddressMap,
//                              Map<String, Object> toAddressMap,
//                              Map<String, Object> parcelMap,
//                              AsyncCompleteMethod onAsyncCompleteMethod) {
//            this.fromAddressMap = fromAddressMap;
//            this.toAddressMap = toAddressMap;
//            this.parcelMap = parcelMap;
//            this.onAsyncCompleteMethod = onAsyncCompleteMethod;
//            result = new HashMap<String, Object>();
//        }

    //        @Override
//        protected Boolean doInBackground(String... params) {
//            EasyPost.apiKey = Constants.EASY_POST_KEY;
//
////            Map<String, Object> fromAddressMap = new HashMap<String, Object>();
////            fromAddressMap.put("name", "Simpler Postage Inc");
////            fromAddressMap.put("street1", "388 Townsend St");
////            fromAddressMap.put("city", "San Francisco");
////            fromAddressMap.put("state", "CA");
////            fromAddressMap.put("zip", "94107");
////            fromAddressMap.put("phone", "415-456-7890");
////
////            Map<String, Object> toAddressMap = new HashMap<String, Object>();
////            toAddressMap.put("name", "Sawyer Bateman");
////            toAddressMap.put("street1", "1A Larkspur Cres");
////            toAddressMap.put("city", "St. Albert");
////            toAddressMap.put("state", "AB");
////            toAddressMap.put("zip", "T8N2M4");
////            toAddressMap.put("phone", "780-483-2746");
////            toAddressMap.put("country", "CA");
//
////            Map<String, Object> parcelMap = new HashMap<String, Object>();
////            parcelMap.put("weight", 22.9);
////            parcelMap.put("height", 12.1);
////            parcelMap.put("width", 8);
////            parcelMap.put("length", 19.8);
//
//            Map<String, Object> customsItem1Map = new HashMap<String, Object>();
//            customsItem1Map.put("description", "EasyPost T-shirts");
//            customsItem1Map.put("quantity", 2);
//            customsItem1Map.put("value", 23.56);
//            customsItem1Map.put("weight", 18.8);
//            customsItem1Map.put("origin_country", "us");
//            customsItem1Map.put("hs_tariff_number", "123456");
//
//            Map<String, Object> customsItem2Map = new HashMap<String, Object>();
//            customsItem2Map.put("description", "EasyPost Stickers");
//            customsItem2Map.put("quantity", 11);
//            customsItem2Map.put("value", 8.98);
//            customsItem2Map.put("weight", 3.2);
//            customsItem2Map.put("origin_country", "us");
//            customsItem2Map.put("hs_tariff_number", "654321");
//
//            try {
//                Parcel parcel = Parcel.create(parcelMap);
//                Address fromAddress = Address.create(fromAddressMap);
//                Address toAddress = Address.create(toAddressMap);
//
//                // Address verified = to_address.verify();
//
//                Map<String, Object> customsInfoMap = new HashMap<String, Object>();
//                customsInfoMap.put("integrated_form_type", "form_2976");
//                customsInfoMap.put("customs_certify", true);
//                customsInfoMap.put("customs_signer", "Dr. Pepper");
//                customsInfoMap.put("contents_type", "gift");
//                customsInfoMap.put("eel_pfc", "NOEEI 30.37(a)");
//                customsInfoMap.put("non_delivery_option", "return");
//                customsInfoMap.put("restriction_type", "none");
//                CustomsItem customsItem1 = CustomsItem.create(customsItem1Map);
//                CustomsItem customsItem2 = CustomsItem.create(customsItem2Map);
//                List<CustomsItem> customsItemsList = new ArrayList<CustomsItem>();
//                customsItemsList.add(customsItem1);
//                customsItemsList.add(customsItem2);
//                customsInfoMap.put("customs_items", customsItemsList);
//                CustomsInfo customsInfo = CustomsInfo.create(customsInfoMap);
//
//                // create shipment
//                Map<String, Object> shipmentMap = new HashMap<String, Object>();
//                shipmentMap.put("to_address", toAddress);
//                shipmentMap.put("from_address", fromAddress);
//                shipmentMap.put("parcel", parcel);
//                shipmentMap.put("customs_info", customsInfo);
//
//                Shipment shipment = Shipment.create(shipmentMap);
//                result.put(Constants.KeyValues.SHIPMENT, shipment);
//                result.put(Constants.KeyValues.SHIPMENT_ID, shipment.getId());
//                List<Rate> rates = shipment.getRates();
//                for (Rate rate : rates) {
//                    if (rate.getRate().equals(CommonMethods.getPackageValues().get(0).get(0))) {
//                        result.put(Constants.KeyValues.RATE_ID, rate.getId());
//                        result.put(Constants.KeyValues.RATE, rate);
//                    }
//                    if (rate.getRate().equals(CommonMethods.getPackageValues().get(1).get(0))) {
//                        result.put(Constants.KeyValues.RATE_ID, rate.getId());
//                        result.put(Constants.KeyValues.RATE, rate);
//                    }
//                    if (rate.getRate().equals(CommonMethods.getPackageValues().get(1).get(0))) {
//                        result.put(Constants.KeyValues.RATE_ID, rate.getId());
//                        result.put(Constants.KeyValues.RATE, rate);
//                    }
//                }
////                 buy postage
////                List<String> buyCarriers = new ArrayList<String>();
////                buyCarriers.add("USPS");
////                List<String> buyServices = new ArrayList<String>();
////                buyServices.add("PriorityMailInternational");
////
////                Map<String, Object> buyShipment = new HashMap<String, Object>();
////                shipmentMap.put("rate_id", rates.get(0).getId());
////                shipmentMap.put("rate", rates.get(0));
////                shipmentMap.put("shipment_id", shipment.getId());
//
////                shipment = shipment.buy(shipment.lowestRate(buyCarriers, buyServices));
//
//
////                shipment = shipment.buy(rates.get(0));
//
////                String s=  shipment.prettyPrint();
////                String s2=s;
//            } catch (EasyPostException e) {
//                e.printStackTrace();
//                result.put(Constants.KeyValues.SHIPMENT, null);
//                result.put(Constants.KeyValues.SHIPMENT_ID, "");
//                result.put(Constants.KeyValues.RATE_ID, "");
//            }
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            onAsyncCompleteMethod.onComplete(this.result);
//        }
//    }
//
//    public static void focusOnView(View view,Context context) {
//        int height=CommonMethods.getDeviceHeight(context);
//        final Rect rect = new Rect(0, 0, view.getWidth(), height-view.getHeight());
//        view.requestRectangleOnScreen(rect, false);
//    }
    public static void showSoftKeyBoard(final EditText editText) {
        (new Handler()).postDelayed(new Runnable() {

            public void run() {
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
//                ivLogo.setVisibility(View.GONE);
            }
        }, 400);
    }
}
