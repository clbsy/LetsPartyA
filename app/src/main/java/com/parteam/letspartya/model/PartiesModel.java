package com.parteam.letspartya.model;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import com.parteam.letspartya.model.bean.PartyItem;
import com.parteam.letspartya.net.NetService;

/**
 * Created by xulin on 2016/1/26.
 */
public class PartiesModel {
    private static final String TAG = "PartiesModel";

    private final OnDataFetchedCallback<PartyItem> mItemOnDataFetchedCallback;

    public PartiesModel(OnDataFetchedCallback<PartyItem> itemOnDataFetchedCallback) {
        this.mItemOnDataFetchedCallback = itemOnDataFetchedCallback;
    }

    public void getFriendParties(Context context) {
        RequestParams friendParams = new RequestParams();
        friendParams.put("clientId", 4);
        NetService.getFriendParties(context, friendParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (null != mItemOnDataFetchedCallback) {
                    mItemOnDataFetchedCallback.onDataFetched(parseItems(response));
                }
            }
        });
    }

    private ArrayList<PartyItem> parseItems(JSONArray response) {
        if ((null == response) || (0 == response.length())) {
            return null;
        }

        ArrayList<PartyItem> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonItem = response.getJSONObject(i);
                PartyItem item = parseItem(jsonItem);
                if (null != item) {
                    list.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private PartyItem parseItem(JSONObject jsonItem) {
        if (null == jsonItem) {
            return null;
        }

        PartyItem item = new PartyItem();
        try {
            String imagesStr = jsonItem.getString("image");
            if (!TextUtils.isEmpty(imagesStr)) {
                String[] imageArray = imagesStr.split(";");
                if ((null != imageArray) && (0 != imageArray.length)) {
                    ArrayList<String> images = new ArrayList<>();
                    for (String image : imageArray) {
                        if (!TextUtils.isEmpty(image)) {
                            images.add(image);
                        }
                    }

                    if (0 < images.size()) {
                        item.setThumbnail(images.get(0));
                        item.setImages(images);
                    }
                }
            }

            String thumbnail = jsonItem.getString("thumbnail");
            if (!TextUtils.isEmpty(thumbnail)) {
                item.setThumbnail(thumbnail);
            }

            item.setHolderID(jsonItem.getLong("holderID"));
            item.setTime(jsonItem.getLong("time"));
            item.setTopical(jsonItem.getString("topical"));
            item.setDetail(jsonItem.getString("detail"));
            item.setLocation(jsonItem.getString("location"));
            item.setCity(jsonItem.getString("city"));
            item.setProvince(jsonItem.getString("province"));
            item.setCounty(jsonItem.getString("county"));
            item.setOrganiger(jsonItem.getString("organiger"));
            item.setOrg_tel(jsonItem.getString("org_tel"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }
}
