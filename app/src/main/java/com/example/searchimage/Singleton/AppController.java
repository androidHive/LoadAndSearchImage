package com.example.searchimage.Singleton;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*
 *This is class is singltone class used for to handle all volly request and we can get volly instance from this class...
 */

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        AppController.context = getApplicationContext();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
}
