package parteam.letspartya.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import parteam.letspartya.widget.SlideRecommendView;

/**
 * Created by ThinkPad on 2016/1/7.
 */
public class SlideRecommendAdapter extends PagerAdapter {

    private ArrayList<ImageView> mList;

    public SlideRecommendAdapter(ArrayList<ImageView> list){
        mList = list;
    }
    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
