package com.example.isebenzi.utils;


import android.content.Context;

import com.example.isebenzi.business.objects.GetOffersResponse;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Provider;
import com.example.isebenzi.business.objects.SignInResponse;
import com.example.isebenzi.business.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zain on 6/4/2015.
 */
public class CommonObjects {

    public static Context context;
    public static User user;
    public static SignInResponse signInResponse;
    public static GetOffersResponse getOffersResponse;
    public static List<Job> OfferJobs=new ArrayList<>();
    public static List<Job> openJobs=new ArrayList<>();
    public static List<Job> closedJobs=new ArrayList<>();
    public static List<Provider> providers=new ArrayList<>();

    public static List<Provider> getProviders() {
        return providers;
    }

    public static void setProviders(List<Provider> providers) {
        CommonObjects.providers = providers;
    }

    public static List<Job> getOfferJobs() {
        return OfferJobs;
    }

    public static void setOfferJobs(List<Job> offerJobs) {
        OfferJobs = offerJobs;
    }

    public static List<Job> getOpenJobs() {
        return openJobs;
    }

    public static void setOpenJobs(List<Job> openJobs) {
        CommonObjects.openJobs = openJobs;
    }

    public static List<Job> getClosedJobs() {
        return closedJobs;
    }

    public static void setClosedJobs(List<Job> closedJobs) {
        CommonObjects.closedJobs = closedJobs;
    }

    public static GetOffersResponse getGetOffersResponse() {
        return getOffersResponse;
    }

    public static void setGetOffersResponse(GetOffersResponse getOffersResponse) {
        CommonObjects.getOffersResponse = getOffersResponse;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CommonObjects.user = user;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        CommonObjects.context = context;
    }

    public static SignInResponse getSignInResponse() {
        return signInResponse;
    }

    public static void setSignInResponse(SignInResponse signInResponse) {
        CommonObjects.signInResponse = signInResponse;
    }
}
