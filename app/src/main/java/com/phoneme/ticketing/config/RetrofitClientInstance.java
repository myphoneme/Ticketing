package com.phoneme.ticketing.config;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.phoneme.ticketing.BuildConfig;
import com.phoneme.ticketing.UserAuth;
import com.phoneme.ticketing.user.HeaderInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;*/
//import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    //private static final String BASE_URL = "http://phoneme.in/anujitbhu/napolean/ticketingapici/index.php/";
    private static final String BASE_URL = "http://phoneme.in/";

    //private static final String BASE_URL=BuildConfig.SERVER_URL;
    private static final String BASE_URL_INFRA_MONIORING="https://www.phoneme.in/anujitbhu/ticketing/";
    private static final String BASE_URL_INFRA_MONITORING_COMMAND="http://mh.nhp.org.in/";
    //private static final String BASE_URL = "http://phoneme.in/";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    /*
    public static Retrofit getRetrofitInstance2() {
        /*
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", "value").build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();*/
//        if (retrofit == null) {
//            retrofit = new retrofit2.Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }*/

    public static Retrofit getRetrofitInstance(Context context) {
        final UserAuth userAuth=new UserAuth(context);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", userAuth.getJwtToken()).build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit APISetup(Activity activity) {
        final int CACHE_SIZE = 10 * 1024 * 1024;
        final String CACHE_DIR = "httpCache";
        OkHttpClient.Builder mOkBuilder;
        Cache mCache;
        HttpLoggingInterceptor mLogInterceptor;
        Retrofit mRetrofit;

        try {
            mOkBuilder = new OkHttpClient.Builder();
            mCache = new Cache(new File(activity.getCacheDir(), CACHE_DIR), CACHE_SIZE);
            mOkBuilder.cache(mCache);
            mLogInterceptor = new HttpLoggingInterceptor();
            mLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//deprecated
            //mLogInterceptor.level(HttpLoggingInterceptor.Level.BODY);//used when implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1' is used
            mOkBuilder.addInterceptor(mLogInterceptor);
            UserAuth auth = new UserAuth(activity.getApplicationContext());

            //Check if user token exists
            String accessToken = auth.getJwtToken();
            if (accessToken != null && accessToken.length() != 0) {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + accessToken));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            } else {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + Config.MASTER_TOKEN));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            }

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkBuilder.build())
                    .build();
            return mRetrofit;
        } catch (Exception ex) {
            Log.d("TICKETING", ex.getStackTrace().toString());
            return null;
        }
    }
    //May be for String
    public static Retrofit APISetupScalars(Activity activity) {
        final int CACHE_SIZE = 10 * 1024 * 1024;
        final String CACHE_DIR = "httpCache";
        OkHttpClient.Builder mOkBuilder;
        Cache mCache;
        HttpLoggingInterceptor mLogInterceptor;
        Retrofit mRetrofit;

        try {
            mOkBuilder = new OkHttpClient.Builder();
            mCache = new Cache(new File(activity.getCacheDir(), CACHE_DIR), CACHE_SIZE);
            mOkBuilder.cache(mCache);
            mLogInterceptor = new HttpLoggingInterceptor();
            mLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//deprecated
            //mLogInterceptor.level(HttpLoggingInterceptor.Level.BODY);//used when implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1' is used
            mOkBuilder.addInterceptor(mLogInterceptor);
            UserAuth auth = new UserAuth(activity.getApplicationContext());

            //Check if user token exists
            String accessToken = auth.getJwtToken();
            if (accessToken != null && accessToken.length() != 0) {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + accessToken));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            } else {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + Config.MASTER_TOKEN));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            }

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(mOkBuilder.build())
                    .build();
            return mRetrofit;
        } catch (Exception ex) {
            Log.d("TICKETING", ex.getStackTrace().toString());
            return null;
        }
    }

    public static Retrofit APIInfraMonitoring(Activity activity){
        final int CACHE_SIZE = 10 * 1024 * 1024;
        final String CACHE_DIR = "httpCache";
        OkHttpClient.Builder mOkBuilder;
        Cache mCache;
        HttpLoggingInterceptor mLogInterceptor;
        Retrofit mRetrofit;

        try {
            mOkBuilder = new OkHttpClient.Builder();
            mCache = new Cache(new File(activity.getCacheDir(), CACHE_DIR), CACHE_SIZE);
            mOkBuilder.cache(mCache);
            mLogInterceptor = new HttpLoggingInterceptor();
            mLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//deprecated
            //mLogInterceptor.level(HttpLoggingInterceptor.Level.BODY);//used when implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1' is used
            mOkBuilder.addInterceptor(mLogInterceptor);
            UserAuth auth = new UserAuth(activity.getApplicationContext());

            //Check if user token exists
            String accessToken = auth.getJwtToken();
            if (accessToken != null && accessToken.length() != 0) {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + accessToken));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            } else {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + Config.MASTER_TOKEN));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            }

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_INFRA_MONIORING)
                    //.baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkBuilder.build())
                    .build();
            return mRetrofit;
        } catch (Exception ex) {
            Log.d("TICKETING", ex.getStackTrace().toString());
            return null;
        }
    }

    public static Retrofit APIInfraMonitoringCommand(Activity activity){
        final int CACHE_SIZE = 10 * 1024 * 1024;
        final String CACHE_DIR = "httpCache";
        OkHttpClient.Builder mOkBuilder;
        Cache mCache;
        HttpLoggingInterceptor mLogInterceptor;
        Retrofit mRetrofit;

        try {
            mOkBuilder = new OkHttpClient.Builder();
            mCache = new Cache(new File(activity.getCacheDir(), CACHE_DIR), CACHE_SIZE);
            mOkBuilder.cache(mCache);
            mLogInterceptor = new HttpLoggingInterceptor();
            mLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//deprecated
            //mLogInterceptor.level(HttpLoggingInterceptor.Level.BODY);//used when implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1' is used
            mOkBuilder.addInterceptor(mLogInterceptor);
            UserAuth auth = new UserAuth(activity.getApplicationContext());

            //Check if user token exists
            String accessToken = auth.getJwtToken();
            if (accessToken != null && accessToken.length() != 0) {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + accessToken));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            } else {
                //mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", "Bearer " + Config.MASTER_TOKEN));
                mOkBuilder.addInterceptor(new HeaderInterceptor("Authorization", accessToken));
            }

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_INFRA_MONITORING_COMMAND)
                    //.baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkBuilder.build())
                    .build();
            return mRetrofit;
        } catch (Exception ex) {
            Log.d("TICKETING", ex.getStackTrace().toString());
            return null;
        }
    }
}
