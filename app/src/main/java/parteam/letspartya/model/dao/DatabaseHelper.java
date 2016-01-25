package parteam.letspartya.model.dao;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import parteam.letspartya.BaseApplication;

/**
 * Created by mengce on 2016/1/8.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "letsparty.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECOMMEND = "recommend";

    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context context){

        if(null == mInstance){
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        onUpgrade(db, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int version = oldVersion + 1; version <= newVersion; version++) {
            upgradeTo(db, version);
        }
    }

    private void upgradeTo(SQLiteDatabase db,int version){

        switch (version){
            case 1:
                createRecommendTable(db);
                break;
            default:
                break;
        }
    }
    private void createRecommendTable(SQLiteDatabase db){

        db.execSQL("CREATE TABLE " + TABLE_RECOMMEND + " (" +
                "_id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "image TEXT," +
                "url TEXT" +
                ");");
    }

    public static interface RecommendColumns{

        static final String ID = "_id";
        static final String TITLE = "title";
        static final String IMAGE = "image";
        static final String URL = "url";
    }
}
