package com.parteam.letspartya.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mengce on 2016/1/6.
 */
public class NetService {

    private static final String BASE_URL = "http://101.200.142.114:9000";

    public static final String FRIEND_PARTIES = "/parties";

    private static AsyncHttpClient sHttpClient = new AsyncHttpClient();

    public static void getRecommend(Context context,String url,RequestParams params,AsyncHttpResponseHandler handler){
        String requestUrl = BASE_URL + url;
        sHttpClient.get(context,requestUrl,params,handler);
    }

    public static void getFriendParties(Context context, RequestParams params, AsyncHttpResponseHandler handler) {
        sHttpClient.get(context, BASE_URL + FRIEND_PARTIES,params, handler);
    }

    public static void cancelRequest(Context context) {
        sHttpClient.cancelRequests(context, false);
    }

    public static void cancelRequest(Context context, boolean mayInterruptIfRunning) {
        sHttpClient.cancelRequests(context, mayInterruptIfRunning);
    }
}
