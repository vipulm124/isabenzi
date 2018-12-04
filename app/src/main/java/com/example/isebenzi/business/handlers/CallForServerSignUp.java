package com.example.isebenzi.business.handlers;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.isebenzi.business.objects.ParamFile;
import com.example.isebenzi.business.objects.ParamString;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class CallForServerSignUp {

    private String url;
    private OnServerResultNotifier onServerResultNotifier;
    private String input;
    private ArrayList<ParamString> paramStrings = new ArrayList<>();
    private ArrayList<ParamFile> paramFiles = new ArrayList<>();
    private String NO_INTERNET = "No internet conection";
    private String API_BASE_URL = "http://isebenzi.wekanexplain.com/";

    public CallForServerSignUp(String url, OnServerResultNotifier onServerResultNotifier, ArrayList<ParamString> paramStrings, ArrayList<ParamFile> paramFiles) {
        this.url = url;
        this.onServerResultNotifier = onServerResultNotifier;
        this.paramStrings = paramStrings;
        this.paramFiles = paramFiles;
    }

    public CallForServerSignUp(String url, OnServerResultNotifier onServerResultNotifier, String input) {
        this.url = url;
        this.input = input;
        this.onServerResultNotifier = onServerResultNotifier;
    }

    public interface OnServerResultNotifier {
        public void onServerResultNotifier(boolean isError, String response);
    }

    //Load data from server
    public void callForServerGet() {
        if (CommonMethods.isNetworkAvailable(CommonObjects.getContext())) {
            AndroidNetworking.initialize(CommonObjects.getContext());
            ANRequest.GetRequestBuilder getRequestBuilder = AndroidNetworking.get(API_BASE_URL + url);
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
            getRequestBuilder.setOkHttpClient(client);
            Log.d("Service_Response", url);
            getRequestBuilder.build().getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String jsonStr) {
                    onServerResultNotifier.onServerResultNotifier(false, jsonStr);
                }

                @Override
                public void onError(ANError error) {
                    onServerResultNotifier.onServerResultNotifier(true, error.getErrorBody());
                }
            });
        } else {
            onServerResultNotifier.onServerResultNotifier(true, NO_INTERNET);
        }
    }

    //Load data from server
    public void callForServerPost() {
        if (CommonMethods.isNetworkAvailable(CommonObjects.getContext())) {
            AndroidNetworking.initialize(CommonObjects.getContext());
            ANRequest.MultiPartBuilder postRequestBuilder = AndroidNetworking.upload(API_BASE_URL + url);
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .build();
            postRequestBuilder.setOkHttpClient(client);
            Log.d("Service_Response", url);
            for (ParamString paramString : paramStrings) {
                Log.d("Service_Response", paramString.getKey() + " " + paramString.getValue());
                postRequestBuilder.addMultipartParameter(paramString.getKey(), paramString.getValue());
            }
            for (ParamFile paramFile : paramFiles) {
                Log.d("Service_Response", paramFile.getKey() + " " + paramFile.getFile().getAbsolutePath());
                postRequestBuilder.addMultipartFile(paramFile.getKey(),paramFile.getFile());
            }
//            postRequestBuilder.addHeaders("Connection", "Keep-Alive");
            postRequestBuilder.addHeaders("Content-Type", "application/x-www-form-urlencoded");
//            postRequestBuilder.addStringBody(input);
            postRequestBuilder.build().getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String jsonStr) {
                    onServerResultNotifier.onServerResultNotifier(false, jsonStr);
                }

                @Override
                public void onError(ANError error) {
                    onServerResultNotifier.onServerResultNotifier(true, error.getErrorBody());
                }
            });
        } else {
            onServerResultNotifier.onServerResultNotifier(true, NO_INTERNET);
        }
    }

}
