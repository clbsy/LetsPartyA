package parteam.letspartya.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mengce on 2016/1/6.
 */
public class NetService {

    private static final String BASE_URL = "http://101.200.142.114:8080/letsparty-v0.2/";
    private static AsyncHttpClient sHttpClient = new AsyncHttpClient();

    public static void getRecommend(Context context,String url,RequestParams params,AsyncHttpResponseHandler handler){
        String requestUrl = BASE_URL + url;
        sHttpClient.get(context,requestUrl,params,handler);
    }
}
