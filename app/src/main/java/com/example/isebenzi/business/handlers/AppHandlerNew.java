package com.example.isebenzi.business.handlers;

import android.util.Log;

import com.example.isebenzi.business.objects.AcceptJob;
import com.example.isebenzi.business.objects.AddJobResponse;
import com.example.isebenzi.business.objects.EditAvailability;
import com.example.isebenzi.business.objects.EditBankDetails;
import com.example.isebenzi.business.objects.EditProfile;
import com.example.isebenzi.business.objects.GetOffersResponse;
import com.example.isebenzi.business.objects.GetRatingResponse;
import com.example.isebenzi.business.objects.ParamFile;
import com.example.isebenzi.business.objects.ParamString;
import com.example.isebenzi.business.objects.RequestPayment;
import com.example.isebenzi.business.objects.SearchResultsResponse;
import com.example.isebenzi.business.objects.SignInResponse;
import com.example.isebenzi.business.objects.SignUpResponse;
import com.example.isebenzi.business.objects.UploadPhoto;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.example.isebenzi.utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;


public class AppHandlerNew {

    public interface SignInListener {
        public void onSignIn(SignInResponse signInResponse);
    }

    public interface SignUpListener {
        public void onSignUp(SignUpResponse signUpResponse);
    }

    public interface SeekerSignUpListener {
        public void onSignUp(SignUpResponse signUpResponse);
    }

    public interface SearchJobListener {
        public void onSearchJobs(SearchResultsResponse searchResultsResponse);
    }

    public interface AddJobListener {
        public void onAddJob(AddJobResponse addJobResponse);
    }

    public interface EditProfileListner {
        public void onEditProfile(EditProfile editProfile);
    }

    public interface UploadPhotoListner {
        public void onUploadPhoto(UploadPhoto uploadPhoto);
    }

    public interface EditAvailabilityListner {
        public void onEditAvailability(EditAvailability editAvailability);
    }

    public interface EditBankDetailsListner {
        public void onEditBankDetails(EditBankDetails editBankDetails);
    }

    public interface AcceptJobListner {
        public void onAcceptJob(AcceptJob acceptJob);
    }
    public interface RequestPaymentListner {
        public void onRequestPayment(RequestPayment requestPayment);
    }
    public interface GetRatingListner {
        public void onGetRating(GetRatingResponse getRatingResponse);
    }

    public interface GetJobsListner {
        public void onGetJobs(GetOffersResponse getOffersResponse);
    }

    //Call to fetch data
    public static void signIn(String email, String password,String token,String type, final SignInListener signInListener) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.EMAIL, email));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSWORD, password));
        paramStrings.add(new ParamString(Constants.KeyValues.TOKEN, token));
        paramStrings.add(new ParamString(Constants.KeyValues.TYPE, type));

        new CallForServerNew("auth", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**Login", response);
                if (!isError) {
                    try {
                        SignInResponse signInResponse = new Gson().fromJson(response, SignInResponse.class);
                        if (signInResponse.getMessage().equals("success")) {
                            signInListener.onSignIn(signInResponse);
                        } else {
                            CommonMethods.hideProgressDialog();
                            signInListener.onSignIn(signInResponse);
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    //Call to fetch data
    public static void providerEditProfile(String firstname, String lastname, String phone, String passport, String password, String id, final EditProfileListner editProfileListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.FIRST_NAME, firstname));
        paramStrings.add(new ParamString(Constants.KeyValues.LAST_NAME, lastname));
        paramStrings.add(new ParamString(Constants.KeyValues.PHOEN, phone));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSPORT_ID, passport));
        paramStrings.add(new ParamString(Constants.KeyValues.ID, id));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSWORD, password));
        new CallForServerNew("editProfile", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**EditProfile", response);
                if (!isError) {
                    try {
                        EditProfile editProfile = new Gson().fromJson(response, EditProfile.class);
                        if (editProfile.getMessage().equals("success")) {
                            editProfileListner.onEditProfile(editProfile);
                        } else {
                            CommonMethods.hideProgressDialog();
                            editProfileListner.onEditProfile(editProfile);
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    public static void providerupLoadPhoto(File file, String id, final UploadPhotoListner uploadPhotoListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.USER_ID, id));
        ArrayList<ParamFile> paramFiles = new ArrayList<>();
        paramFiles.add(new ParamFile(Constants.KeyValues.PHOTO, file));
        new CallForServerSignUp("uploadPhoto", new CallForServerSignUp.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**uploadPhoto", response);
                if (!isError) {
                    try {
                        UploadPhoto uploadPhoto = new Gson().fromJson(response, UploadPhoto.class);
                        if (uploadPhoto.getMessage().equals("success")) {
                            uploadPhotoListner.onUploadPhoto(uploadPhoto);
                        } else {
                            CommonMethods.hideProgressDialog();
                            uploadPhotoListner.onUploadPhoto(uploadPhoto);
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, paramFiles).callForServerPost();
    }
    public static void requestPayment(String id, final RequestPaymentListner requestPaymentListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.ID, id));
        new CallForServerNew("requestPayment", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**requestPayment", response);
                if (!isError) {
                    try {
                        RequestPayment requestPayment = new Gson().fromJson(response, RequestPayment.class);
                        if (requestPayment.getMessage().equals("success")) {
                            requestPaymentListner.onRequestPayment(requestPayment);
                        } else {
                            CommonMethods.hideProgressDialog();
                            requestPaymentListner.onRequestPayment(requestPayment);
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }
    public static void providerAcceptJob(String id, String status, final AcceptJobListner acceptJobListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.STATUS, status));
        paramStrings.add(new ParamString(Constants.KeyValues.ID, id));
        new CallForServerNew("changeJobStatus", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**changeJobStatus", response);
                if (!isError) {
                    try {
                        AcceptJob acceptJob = new Gson().fromJson(response, AcceptJob.class);
                        if (acceptJob.getMessage().equals("success")) {
                            acceptJobListner.onAcceptJob(acceptJob);
                        } else {
                            CommonMethods.hideProgressDialog();
                            acceptJobListner.onAcceptJob(acceptJob);
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    public static void providerEditBankDetail(String account_number, String branch_code, String bank_name, String account_name, String id, final EditBankDetailsListner editBankDetailsListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.ACCOUNT_NUMBER, account_number));
        paramStrings.add(new ParamString(Constants.KeyValues.BRANCH_CODE, branch_code));
        paramStrings.add(new ParamString(Constants.KeyValues.BANK_NAME, bank_name));
        paramStrings.add(new ParamString(Constants.KeyValues.ACCOUNT_NAME, account_name));
        paramStrings.add(new ParamString(Constants.KeyValues.USER_ID, id));
        new CallForServerNew("editBank", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**EditBankingDetails", response);
                if (!isError) {
                    try {
                        EditBankDetails editBankDetails1 = new Gson().fromJson(response, EditBankDetails.class);
                        if (editBankDetails1.getMessage().equals("success")) {
                            editBankDetailsListner.onEditBankDetails(editBankDetails1);
                        } else {
                            CommonMethods.hideProgressDialog();
                            editBankDetailsListner.onEditBankDetails(editBankDetails1);
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    public static void providerGetJobs(String seeker, String provider, final GetJobsListner getJobsListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.PROVIDER_USER_ID, provider));
        paramStrings.add(new ParamString(Constants.KeyValues.SEEKER_USER_ID, seeker));
        new CallForServerNew("getJobs", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**getJobs", response);
                if (!isError) {
                    try {
                        GetOffersResponse getOffersResponse = new Gson().fromJson(response, GetOffersResponse.class);
                        if (getOffersResponse != null) {
                            getJobsListner.onGetJobs(getOffersResponse);
                        } else {
                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    public static void providergetRatings(String id, final GetRatingListner getRatingListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.PROVIDER_USER_ID, id));
        new CallForServerNew("getRating", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**getRating", response);
                if (!isError) {
                    try {
                        GetRatingResponse getRatingResponse = new Gson().fromJson(response, GetRatingResponse.class);
                        if (getRatingResponse != null) {
                            getRatingListner.onGetRating(getRatingResponse);
                        } else {
                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), signInResponse.getStatus());
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
                    }
                } else {
                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    public static void editAvailabiltiy(UserLocal userLocal, final EditAvailabilityListner editAvailabilityListner) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.LATITUDE, userLocal.getLatitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.LONGITUDE, userLocal.getLongitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.ADDRESS, userLocal.getAddress()));
        paramStrings.add(new ParamString(Constants.KeyValues.DAILY_RATE, userLocal.getDailyRate()));
        paramStrings.add(new ParamString(Constants.KeyValues.RADIUS, userLocal.getRadius()));
        paramStrings.add(new ParamString(Constants.KeyValues.TO, userLocal.getTo()));
        paramStrings.add(new ParamString(Constants.KeyValues.FROM, userLocal.getFrom()));
        paramStrings.add(new ParamString(Constants.KeyValues.USER_ID, CommonObjects.getUser().getId()));
        new CallForServerNew("editAvailability", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**editAvailability", response);
                if (!isError) {
                    try {
                        EditAvailability editAvailability = new Gson().fromJson(response, EditAvailability.class);
                        if (editAvailability.getMessage().equals("success")) {
                            editAvailabilityListner.onEditAvailability(editAvailability);
                        } else {
                            editAvailabilityListner.onEditAvailability(editAvailability);
                            CommonMethods.hideProgressDialog();
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
                    }
                } else {
                    CommonMethods.hideProgressDialog();
                }
            }
        }, paramStrings, new ArrayList<ParamFile>()).callForServerPost();
    }

    //Call to fetch data
    public static void seekerAddJob(UserLocal userLocal, final AddJobListener addJobListener) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.SEEKER_USER_ID, CommonObjects.getUser().getId()));
        paramStrings.add(new ParamString(Constants.KeyValues.PROVIDER_USER_ID, userLocal.getProvider()));
        paramStrings.add(new ParamString(Constants.KeyValues.STATUS, userLocal.getStatus()));

        paramStrings.add(new ParamString(Constants.KeyValues.LATITUDE, userLocal.getLatitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.LONGITUDE, userLocal.getLongitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.TO_DATE, userLocal.getTo()));
        paramStrings.add(new ParamString(Constants.KeyValues.FROM_DATE, userLocal.getFrom()));
        paramStrings.add(new ParamString(Constants.KeyValues.OCCUPATION, userLocal.getOccupation()));
        paramStrings.add(new ParamString(Constants.KeyValues.PER_DAY, userLocal.getPerDayFees()));
        paramStrings.add(new ParamString(Constants.KeyValues.PER_HOUR, userLocal.getPerHourFees()));
        paramStrings.add(new ParamString(Constants.KeyValues.PERSONS, userLocal.getNoOfPersons()));
        ArrayList<ParamFile> paramFiles = new ArrayList<>();
        new CallForServerNew("addJob", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**addJob", response);
                if (!isError) {
                    try {
                        AddJobResponse addJobResponse = new Gson().fromJson(response, AddJobResponse.class);
                        if (addJobResponse.getMessage().equals("success")) {
                            addJobListener.onAddJob(addJobResponse);
                        } else {
                            addJobListener.onAddJob(addJobResponse);
                            CommonMethods.hideProgressDialog();
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
                    }
                } else {
                    CommonMethods.hideProgressDialog();
                }
            }
        }, paramStrings, paramFiles).callForServerPost();
    }

    //Call to fetch data
    public static void seekerJobSearch(UserLocal userLocal, final SearchJobListener searchJobListener) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.LATITUDE, userLocal.getLatitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.LONGITUDE, userLocal.getLongitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.TO_DATE, userLocal.getTo()));
        paramStrings.add(new ParamString(Constants.KeyValues.FROM_DATE, userLocal.getFrom()));
        paramStrings.add(new ParamString(Constants.KeyValues.OCCUPATION, userLocal.getOccupation()));
        paramStrings.add(new ParamString(Constants.KeyValues.PER_DAY, userLocal.getPerDayFees()));
        paramStrings.add(new ParamString(Constants.KeyValues.PER_HOUR, userLocal.getPerHourFees()));
        paramStrings.add(new ParamString(Constants.KeyValues.PERSONS, userLocal.getNoOfPersons()));
        ArrayList<ParamFile> paramFiles = new ArrayList<>();
        new CallForServerNew("jobSearch", new CallForServerNew.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**jobSearch", response);
                if (!isError) {
                    try {
                        SearchResultsResponse searchResultsResponse = new Gson().fromJson(response, SearchResultsResponse.class);
                        if (searchResultsResponse != null) {
                            searchJobListener.onSearchJobs(searchResultsResponse);
                        } else {
                            CommonMethods.hideProgressDialog();
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
                    }
                } else {
                    CommonMethods.hideProgressDialog();
                }
            }
        }, paramStrings, paramFiles).callForServerPost();
    }

    //Call to fetch data
    public static void seekerSignUp(UserLocal userLocal, final SeekerSignUpListener signUpListener) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.FIRST_NAME, userLocal.getFirstname()));
        paramStrings.add(new ParamString(Constants.KeyValues.LAST_NAME, userLocal.getLastname()));
        paramStrings.add(new ParamString(Constants.KeyValues.PHOEN, userLocal.getPhone()));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSPORT_ID, userLocal.getPassport()));
        paramStrings.add(new ParamString(Constants.KeyValues.TYPE, userLocal.getType()));
        paramStrings.add(new ParamString(Constants.KeyValues.EMAIL, userLocal.getEmail()));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSWORD, userLocal.getPassword()));
        paramStrings.add(new ParamString(Constants.KeyValues.LATITUDE, userLocal.getLatitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.LONGITUDE, userLocal.getLongitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.BANK_NAME, userLocal.getBankName()));
        paramStrings.add(new ParamString(Constants.KeyValues.BANK_TYPE, userLocal.getBank_type()));
        paramStrings.add(new ParamString(Constants.KeyValues.EXPIRY, userLocal.getExpiry()));
        paramStrings.add(new ParamString(Constants.KeyValues.STC, userLocal.getStc()));
        paramStrings.add(new ParamString(Constants.KeyValues.NUMBER, userLocal.getNumber()));
        paramStrings.add(new ParamString(Constants.KeyValues.LOCATION_NAME, userLocal.getLocation()));
        paramStrings.add(new ParamString(Constants.KeyValues.TOKEN, userLocal.getToken()));
        ArrayList<ParamFile> paramFiles = new ArrayList<>();
        paramFiles.add(new ParamFile(Constants.KeyValues.PHOTO, userLocal.getProfilePic()));
        new CallForServerSignUp("signup", new CallForServerSignUp.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**SignUp", response);
                if (!isError) {
                    try {
                        SignUpResponse signUpResponse = new Gson().fromJson(response, SignUpResponse.class);
                        if (signUpResponse.getMessage().equals("success")) {
                            signUpListener.onSignUp(signUpResponse);
                        } else {
                            signUpListener.onSignUp(signUpResponse);
                            CommonMethods.hideProgressDialog();
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
                    }
                } else {
                    CommonMethods.hideProgressDialog();
                }
            }
        }, paramStrings, paramFiles).callForServerPost();
    }

    //Call to fetch data
    public static void signUpProvider(UserLocal userLocal, final SignUpListener signUpListener) {
        ArrayList<ParamString> paramStrings = new ArrayList<>();
        paramStrings.add(new ParamString(Constants.KeyValues.FIRST_NAME, userLocal.getFirstname()));
        paramStrings.add(new ParamString(Constants.KeyValues.LAST_NAME, userLocal.getLastname()));
        paramStrings.add(new ParamString(Constants.KeyValues.PHOEN, userLocal.getPhone()));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSPORT_ID, userLocal.getPassport()));
        paramStrings.add(new ParamString(Constants.KeyValues.TYPE, userLocal.getType()));
        paramStrings.add(new ParamString(Constants.KeyValues.EMAIL, userLocal.getEmail()));
        paramStrings.add(new ParamString(Constants.KeyValues.PASSWORD, userLocal.getPassword()));
        paramStrings.add(new ParamString(Constants.KeyValues.LATITUDE,userLocal.getLatitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.LONGITUDE, userLocal.getLongitude()));
        paramStrings.add(new ParamString(Constants.KeyValues.ADDRESS, userLocal.getAddress()));
        paramStrings.add(new ParamString(Constants.KeyValues.DAILY_RATE, userLocal.getDailyRate()));
        paramStrings.add(new ParamString(Constants.KeyValues.RADIUS, userLocal.getRadius()));
        paramStrings.add(new ParamString(Constants.KeyValues.TO, userLocal.getTo()));
        paramStrings.add(new ParamString(Constants.KeyValues.FROM, userLocal.getFrom()));
        paramStrings.add(new ParamString(Constants.KeyValues.ACCOUNT_NUMBER, userLocal.getAccountNumber()));
        paramStrings.add(new ParamString(Constants.KeyValues.BRANCH_CODE, userLocal.getBranchCode()));
        paramStrings.add(new ParamString(Constants.KeyValues.BANK_NAME, userLocal.getBankName()));
        paramStrings.add(new ParamString(Constants.KeyValues.ACCOUNT_NAME, userLocal.getAccountName()));
        paramStrings.add(new ParamString(Constants.KeyValues.TOKEN, userLocal.getToken()));
        ArrayList<ParamFile> paramFiles = new ArrayList<>();
        paramFiles.add(new ParamFile(Constants.KeyValues.PHOTO, userLocal.getProfilePic()));
        paramFiles.add(new ParamFile(Constants.KeyValues.PASSPORT_PHOTO, userLocal.getFile()));
        new CallForServerSignUp("signup", new CallForServerSignUp.OnServerResultNotifier() {
            @Override
            public void onServerResultNotifier(boolean isError, String response) {
                Log.d("**SignUp", response);
                if (!isError) {
                    try {
                        SignUpResponse signUpResponse = new Gson().fromJson(response, SignUpResponse.class);
                        if (signUpResponse.getMessage().equals("success")) {
                            signUpListener.onSignUp(signUpResponse);
                        } else {
                            signUpListener.onSignUp(signUpResponse);
                            CommonMethods.hideProgressDialog();
                        }
                    } catch (Exception e) {
                        CommonMethods.hideProgressDialog();
                    }
                } else {
                    CommonMethods.hideProgressDialog();
                }
            }
        }, paramStrings, paramFiles).callForServerPost();
    }
//    //Call to fetch data
//    public static void forgotPassword(ForgotPasswordInput forgotPasswordInput, final SpecificListener specificListener) {
//        new CallForServer("forgot" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataResponse specificDataResponse = new Gson().fromJson(response, SpecificDataResponse.class);
//                        if (specificDataResponse.getCode().equals("200")) {
//                            specificListener.onSpecificCall(specificDataResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(forgotPasswordInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void logout(LogoutInput logoutInput, final LogoutListener logout) {
//        new CallForServer("logout" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        LogoutResponse logoutResponse = new Gson().fromJson(response, LogoutResponse.class);
//                        if(logoutResponse.getCode().equals("200")) {
//                            logout.onLogout(logoutResponse);
//                        }
//                        else
//                        {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(),logoutResponse.getStatus());
//                        }
//                    }
//                    catch (Exception e)
//                    {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(),e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(logoutInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void resetPassword(ResetPasswordInput resetPasswordInput, final SpecificListener specificListener) {
//        new CallForServer("resetpass" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataResponse specificDataResponse = new Gson().fromJson(response, SpecificDataResponse.class);
//                        if (specificDataResponse.getCode().equals("200")) {
//                            specificListener.onSpecificCall(specificDataResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(resetPasswordInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void profileUpdate(ProfileUpdateInput profileUpdateInput, final SpecificListener specificListener) {
//        new CallForServer("profile" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataResponse specificDataResponse = new Gson().fromJson(response, SpecificDataResponse.class);
//                        if (specificDataResponse.getCode().equals("200")) {
//                            specificListener.onSpecificCall(specificDataResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(profileUpdateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void addEditVehicle(VehicleInput vehicleInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("vehicles/edit" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(vehicleInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void addEditTyre(TyreInput tyreInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("vehicles/edittyre" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tyreInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void saveVehicleStructure(TokenIdInput tokenIdInput, final SpecificListener specificListener) {
//        new CallForServer("vehicles/savestructure" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataResponse specificDataResponse = new Gson().fromJson(response, SpecificDataResponse.class);
//                        if (specificDataResponse.getCode().equals("200")) {
//                            specificListener.onSpecificCall (specificDataResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenIdInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void vehicleDetail(TokenIdInput tokenIdInput, final VehicleDetailListener vehicleDetailListener) {
//        new CallForServer("vehicles/detail" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        VehicleDetailResponse vehicleDetailResponse = new Gson().fromJson(response, VehicleDetailResponse.class);
//                        if (vehicleDetailResponse.getCode().equals("200")) {
//                            vehicleDetailListener.onVehicleDetailListener (vehicleDetailResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), vehicleDetailResponse.getStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenIdInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void serviceProvidersDropdown(TokenInput tokenInput, final ServiceProviderDropdownListener serviceProviderDropdownListener) {
//        new CallForServer("service_providers/dropdown" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        ServiceProviderDropdownResponse serviceProviderDropdownResponse = new Gson().fromJson(response, ServiceProviderDropdownResponse.class);
//                        if (serviceProviderDropdownResponse.getCode().equals("200")) {
//                            serviceProviderDropdownListener.onServiceProviderDropdownListener (serviceProviderDropdownResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), serviceProviderDropdownResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void customersDropdown(TokenSpIdInput tokenSpIdInput, final CustomerDropdownListener customerDropdownListener) {
//        new CallForServer("customers/dropdown" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        CustomerDropdownResponse customerDropdownResponse = new Gson().fromJson(response, CustomerDropdownResponse.class);
//                        if (customerDropdownResponse.getCode().equals("200")) {
//                            customerDropdownListener.onCustomerDropdownListener (customerDropdownResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), customerDropdownResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenSpIdInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void groupsDropdown(TokenSpCIdInput tokenSpCIdInput, final GroupDropdownListener groupDropdownListener) {
//        new CallForServer("groups/dropdown" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        GroupDropdownResponse groupDropdownResponse = new Gson().fromJson(response, GroupDropdownResponse.class);
//                        if (groupDropdownResponse.getCode().equals("200")) {
//                            groupDropdownListener.onGroupDropdownListener (groupDropdownResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), groupDropdownResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenSpCIdInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void vehicleList(TokenPageLSDateInput tokenPageLSDateInput, final VehicleListListener vehicleListListener) {
//        new CallForServer("vehicles/vehicle_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        VehicleListResponse vehicleListResponse = new Gson().fromJson(response, VehicleListResponse.class);
//                        if (vehicleListResponse.getCode().equals("200")) {
//                            vehicleListListener.onVehicleListListener (vehicleListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), vehicleListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void searchVehicleByCustomer(TokenCIdPageInput tokenCIdPageInput, final VehicleListListener vehicleListListener) {
//        new CallForServer("vehicles/search" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        VehicleListResponse vehicleListResponse = new Gson().fromJson(response, VehicleListResponse.class);
//                        if (vehicleListResponse.getCode().equals("200")) {
//                            vehicleListListener.onVehicleListListener (vehicleListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), vehicleListResponse.getStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenCIdPageInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void searchVehicleByRegNumber(TokenRNPageInput tokenRNPageInput, final VehicleListListener vehicleListListener) {
//        new CallForServer("vehicles/search" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        VehicleListResponse vehicleListResponse = new Gson().fromJson(response, VehicleListResponse.class);
//                        if (vehicleListResponse.getCode().equals("200")) {
//                            vehicleListListener.onVehicleListListener (vehicleListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), vehicleListResponse.getStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenRNPageInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void jobList(TokenPageLSDateInput tokenPageLSDateInput, final JobListListener jobListListener) {
//        new CallForServer("jobs/job_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        JobListResponse jobListResponse = new Gson().fromJson(response, JobListResponse.class);
//                        if (jobListResponse.getCode().equals("200")) {
//                            jobListListener.onJobListListener (jobListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), jobListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void jobDetail(TokenIdInput tokenIdInput, final JobDetailListener JobDetailListener) {
//        new CallForServer("jobs/detail" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        JobDetailResponse JobDetailResponse = new Gson().fromJson(response, JobDetailResponse.class);
//                        if (JobDetailResponse.getCode().equals("200")) {
//                            JobDetailListener.onJobDetailListener (JobDetailResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
//                            CommonMethods.showMessage(CommonObjects.getContext(), JobDetailResponse.getStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
//                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenIdInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void serviceProviderList(TokenPageLSDateInput tokenPageLSDateInput, final ServiceProviderListListener serviceProviderListListener) {
//        new CallForServer("service_providers/sp_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        ServiceProviderListResponse serviceProviderListResponse = new Gson().fromJson(response, ServiceProviderListResponse.class);
//                        if (serviceProviderListResponse.getCode().equals("200")) {
//                            serviceProviderListListener.onServiceProviderListListener (serviceProviderListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), serviceProviderListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void customerList(TokenPageLSDateInput tokenPageLSDateInput, final CustomerListListener customerListListener) {
//        new CallForServer("customers/customer_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        CustomerListResponse customerListResponse = new Gson().fromJson(response, CustomerListResponse.class);
//                        if (customerListResponse.getCode().equals("200")) {
//                            customerListListener.onCustomerListListener (customerListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), customerListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void groupList(TokenPageLSDateInput tokenPageLSDateInput, final GroupListListener groupListListener) {
//        new CallForServer("groups/group_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        GroupListResponse groupListResponse = new Gson().fromJson(response, GroupListResponse.class);
//                        if (groupListResponse.getCode().equals("200")) {
//                            groupListListener.onGroupListListener (groupListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), groupListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void configurationList(TokenLSDateInput tokenLSDateInput, final ConfigurationListListener configurationListListener) {
//        new CallForServer("observations/config_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        ConfigurationListResponse configurationListResponse = new Gson().fromJson(response, ConfigurationListResponse.class);
//                        if (configurationListResponse.getCode().equals("200")) {
//                            configurationListListener.onConfigurationListListener (configurationListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), configurationListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void configurationList(TokenSpIdInput tokenSpIdInput, final ConfigurationListListener configurationListListener) {
//        new CallForServer("observations/config_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        ConfigurationListResponse configurationListResponse = new Gson().fromJson(response, ConfigurationListResponse.class);
//                        if (configurationListResponse.getCode().equals("200")) {
//                            configurationListListener.onConfigurationListListener (configurationListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), configurationListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
//                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenSpIdInput)).callForServerPost();
//    }
//
//
//    //Call to fetch data
//    public static void addInspectionJobBased(StartAddInspectionJobBasedInput startAddInspectionJobBasedInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("inspections/edit" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(startAddInspectionJobBasedInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void addInspectionDirect(StartAddInspectionDirectInput startAddInspectionDirectInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("inspections/edit" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(startAddInspectionDirectInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void addTyreInspection(TyreInspectionInput tyreInspectionInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("inspections/tyreinspection" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tyreInspectionInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void addGeneralObservation(GeneralObservationInput generalObservationInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("inspections/tyreinspection" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(generalObservationInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void inspectionList(TokenPageLSDateInput tokenPageLSDateInput, final InspectionListListener inspectionListListener) {
//        new CallForServer("inspections/inspection_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        InspectionListResponse inspectionListResponse = new Gson().fromJson(response, InspectionListResponse.class);
//                        if (inspectionListResponse.getCode().equals("200")) {
//                            inspectionListListener.onInspectionListListener (inspectionListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), inspectionListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void yardList(TokenPageLSDateInput tokenPageLSDateInput, final YardListListener yardListListener) {
//        new CallForServer("yards/yard_list" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        YardListResponse yardListResponse = new Gson().fromJson(response, YardListResponse.class);
//                        if (yardListResponse.getCode().equals("200")) {
//                            yardListListener.onYardListListener (yardListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), yardListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void addTyreDropdown(TokenInput tokenInput, final AddTyreDropdownListener addTyreDropdownListener) {
//        new CallForServer("vehicles/tyredropdowns" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        AddTyreDropdownResponse addTyreDropdownResponse = new Gson().fromJson(response, AddTyreDropdownResponse.class);
//                        if (addTyreDropdownResponse.getCode().equals("200")) {
//                            addTyreDropdownListener.onAddTyreDropdownListener (addTyreDropdownResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), addTyreDropdownResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void inspectionSummary(TokenInspectionIdInput tokenInspectionIdInput, final InspectionSummaryListener inspectionSummaryListener) {
//        new CallForServer("inspections/summary" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        InspectionSummaryResponse inspectionSummaryResponse = new Gson().fromJson(response, InspectionSummaryResponse.class);
//                        if (inspectionSummaryResponse.getCode().equals("200")) {
//                            inspectionSummaryListener.onInspectionSummaryListener (inspectionSummaryResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), inspectionSummaryResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenInspectionIdInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void inspectionNotAllowedDirect(InspectionNotAllowedDirectInput inspectionNotAllowedDirectInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("inspections/edit" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(inspectionNotAllowedDirectInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void inspectionNotAllowedJobBased(InspectionNotAllowedJobBasedInput inspectionNotAllowedJobBasedInput, final SpecificWithIdListener specificWithIdListener) {
//        new CallForServer("inspections/edit" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        SpecificDataWithIdResponse specificDataWithIdResponse = new Gson().fromJson(response, SpecificDataWithIdResponse.class);
//                        if (specificDataWithIdResponse.getCode().equals("200")) {
//                            specificWithIdListener.onSpecificCallWithId(specificDataWithIdResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), specificDataWithIdResponse.getMessage());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(inspectionNotAllowedJobBasedInput)).callForServerPost();
//    }
//
//    //Call to fetch data
//    public static void worksheetList(TokenPageLSDateInput tokenPageLSDateInput, final WorksheetListListener worksheetListListener) {
//        new CallForServer("worksheets/worksheet_listt" , new CallForServer.OnServerResultNotifier() {
//            @Override
//            public void onServerResultNotifier(boolean isError, String response) {
//                if(!isError) {
//                    try {
//                        WorksheetListResponse worksheetListResponse = new Gson().fromJson(response, WorksheetListResponse.class);
//                        if (worksheetListResponse.getCode().equals("200")) {
//                            worksheetListListener.onWorksheetListListener (worksheetListResponse);
//                        } else {
//                            CommonMethods.hideProgressDialog();
////                            CommonMethods.showMessage(CommonObjects.getContext(), worksheetListResponse.getLocalStatus());
//                        }
//                    } catch (Exception e) {
//                        CommonMethods.hideProgressDialog();
////                        CommonMethods.showMessage(CommonObjects.getContext(), e.getMessage());
//                    }
//                }
//                else
//                {
//                    CommonMethods.hideProgressDialog();
////                    CommonMethods.showMessage(CommonObjects.getContext(),response);
//                }
//            }
//        },new Gson().toJson(tokenPageLSDateInput)).callForServerPost();
//    }
}
