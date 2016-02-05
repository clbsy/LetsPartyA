package com.parteam.letspartya.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import com.parteam.letspartya.model.bean.CommentItem;
import com.parteam.letspartya.model.bean.PartyItem;
import com.parteam.letspartya.net.NetService;

/**
 * Created by xulin on 2016/1/26.
 */
public class PartiesModel {
    private static final String TAG = "PartiesModel";

    private OnDataFetchedCallback<PartyItem> mOnItemDataFetchedCallback;

    public PartiesModel() {
    }

    public void getFriendParties(Context context) {
        RequestParams friendParams = new RequestParams();
        friendParams.put("clientId", 4);
        NetService.getData(context, NetService.FRIEND_PARTIES, friendParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (null != mOnItemDataFetchedCallback) {
                    mOnItemDataFetchedCallback.onDataFetched(parseItems(response));
                }
            }
        });
    }

    public void updateFav(Context context, long subjectId, final OnUpdateFavCallback callback) {
        RequestParams params = new RequestParams();
        params.put("subjectId", subjectId);
        Log.d(TAG, "updateFav - params : " + params);
        NetService.getData(context, NetService.UPDATE_FAV, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (null == response) {
                    return;
                }

                if (response.has("fav")) {
                    try {
                        long fav = response.getLong("fav");
                        if (null != callback) {
                            callback.updateFav(fav);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void updateComments(Context context, long subjectId, long userId, String content, final OnUpdateCommentsCallback commentsCallback) {
        RequestParams params = new RequestParams();
        params.put("subjectId", subjectId);
        params.put("partnerId", userId);
        params.put("comment", content);
        Log.d(TAG, "updateComments - params : " + params);
        NetService.getData(context, NetService.UPDATE_COMMENT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (null == response) {
                    return;
                }

                if (null == commentsCallback) {
                    return;
                }

                if (response.has("comments")) {
                    ArrayList<CommentItem> commentItems;
                    try {
                        commentItems = parseComments(response.getJSONArray("comments"));
                        commentsCallback.onUpdateComments(commentItems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

            item.setId(jsonItem.getLong("id"));
            item.setHolderID(jsonItem.getLong("holderID"));
            item.setTime(jsonItem.getLong("time"));
            item.setFav(jsonItem.getLong("fav"));
            item.setComments(parseComments(jsonItem.getString("comments")));
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

    private ArrayList<CommentItem> parseComments(String commentStr) {
        if (TextUtils.isEmpty(commentStr) || "null".equals(commentStr)) {
            return null;
        }


        JSONArray array = null;
        try {
            array = new JSONArray(commentStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parseComments(array);
    }

    private ArrayList<CommentItem> parseComments(JSONArray array) {
        if ((null == array) || (0 == array.length())) {
            return null;
        }

        ArrayList<CommentItem> comments = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                CommentItem item = new CommentItem();
                item.setContent(jsonObject.getString("content"));
                item.setPartnerId(jsonObject.getLong("partnerId"));
                if (jsonObject.has("nickName")) {
                    item.setNickName(jsonObject.getString("nickName"));
                }

                if (jsonObject.has("name")) {
                    item.setNickName(jsonObject.getString("name"));
                }
                comments.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return comments;
    }

    public void setOnItemDataFetchedCallback(OnDataFetchedCallback<PartyItem> onItemDataFetchedCallback) {
        this.mOnItemDataFetchedCallback = onItemDataFetchedCallback;
    }

    public interface OnUpdateFavCallback {
        void updateFav(long fav);
    }

    public interface OnUpdateCommentsCallback {
        void onUpdateComments(ArrayList<CommentItem> commentItems);
    }
}
