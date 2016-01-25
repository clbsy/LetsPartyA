package parteam.letspartya.model;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import parteam.letspartya.model.bean.PartyRecommendBean;
import parteam.letspartya.model.dao.PartyRecommendDao;
import parteam.letspartya.net.NetService;

/**
 * Created by mengce on 2016/1/9.
 */
public class RecommendModel {

    private static final String TAG = RecommendModel.class.getSimpleName();

    private Context mContext;
    private RecommendCallback mCallback;
    public RecommendModel(Context context){
        mContext = context;
    }

    public ArrayList<PartyRecommendBean> getPartyRecommend(){
        PartyRecommendDao recommendDao = new PartyRecommendDao();
        ArrayList<PartyRecommendBean> recommendList = recommendDao.queryPartyRecommend(mContext.getApplicationContext());
        return recommendList;
    }
    public boolean isNeedUpdate(){

        return true;
    }
    public void syncPartyRecommendFromNet(){
        RequestParams params = new RequestParams();
        //params.put();
        Log.e(TAG,"syncPartyRecommendFromNet ");
        NetService.getRecommend(mContext,"welcome",params,new TextHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e(TAG,"response = " + responseString.toString());
                ArrayList<PartyRecommendBean> recommendBeans = parserJson(responseString);

                if(null != mCallback){
                    mCallback.onDataChanged(recommendBeans);
                }
                PartyRecommendDao recommendDao = new PartyRecommendDao();
                recommendDao.insert(mContext, recommendBeans);
            }
        });
    }

    public void setRecommendCallback(RecommendCallback callback){
        mCallback = callback;
    }

    public interface RecommendCallback{

        void onDataChanged(ArrayList<PartyRecommendBean> recommendBeans);
    }
    private ArrayList<PartyRecommendBean> parserJson(String response){

        ArrayList<PartyRecommendBean> recommendBeans = new ArrayList<PartyRecommendBean>();
        try {

            JSONArray array =  new JSONArray(response);
            Log.e(TAG,"array  = " + array.toString());
            for(int i =0;i<array.length();i++){
                JSONObject partyObj = (JSONObject)array.getJSONObject(i);
                PartyRecommendBean bean = new PartyRecommendBean();
                bean.setUrl(partyObj.getString("url"));
                bean.setTitle(partyObj.getString("title"));
                bean.setImage(partyObj.getString("image"));
                bean.setId(partyObj.getInt("id"));
                recommendBeans.add(bean);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return recommendBeans;
    }
}
