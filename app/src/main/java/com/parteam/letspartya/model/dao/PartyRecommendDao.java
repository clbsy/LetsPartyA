package com.parteam.letspartya.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.parteam.letspartya.model.bean.PartyRecommendBean;

/**
 * Created by mengce on 2016/1/9.
 */
public class PartyRecommendDao {

    public ArrayList<PartyRecommendBean> queryPartyRecommend(Context context){

        ArrayList<PartyRecommendBean> recommendList = new ArrayList<PartyRecommendBean>();
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RECOMMEND,null,null,null,null,null,null);
        if(null != cursor){
            final int idIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.RecommendColumns.ID);
            final int titleIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.RecommendColumns.TITLE);
            final int imageIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.RecommendColumns.IMAGE);
            final int urlIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.RecommendColumns.URL);
            while (cursor.moveToNext()){
                PartyRecommendBean bean = new PartyRecommendBean();
                bean.setId(cursor.getInt(idIndex));
                bean.setTitle(cursor.getString(titleIndex));
                bean.setImage(cursor.getString(imageIndex));
                bean.setUrl(cursor.getString(urlIndex));
                recommendList.add(bean);
            }
            cursor.close();
        }
        db.close();
        return recommendList;
    }

    public void insert(Context context,ArrayList<PartyRecommendBean> list){

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        for(int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.RecommendColumns.ID,list.get(i).getId());
            values.put(DatabaseHelper.RecommendColumns.IMAGE,list.get(i).getImage());
            values.put(DatabaseHelper.RecommendColumns.TITLE,list.get(i).getTitle());
            values.put(DatabaseHelper.RecommendColumns.URL,list.get(i).getUrl());

            db.insert(DatabaseHelper.TABLE_RECOMMEND, null, values);
        }
        db.close();
    }

    public void update(Context context,ArrayList<PartyRecommendBean> list){

    }

    public void delete(Context context){

    }

}
