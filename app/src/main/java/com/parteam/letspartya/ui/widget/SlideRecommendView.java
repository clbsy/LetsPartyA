package com.parteam.letspartya.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.parteam.letspartya.R;
import com.parteam.letspartya.ui.ImageUtils;
import com.parteam.letspartya.model.bean.PartyRecommendBean;
import com.parteam.letspartya.util.Constants;

/**
 * Created by mengce on 2016/1/3.
 */
public class SlideRecommendView extends FrameLayout {

    private final static boolean isAutoPlay = true;

    private static final int MSG_UPDATE_UI = 101;
    private static final int MSG_SLIDE_TO_NEXT = 102;

    private ViewPager mViewPager;
    private SlideRecommendAdapter mAdapter;
    private LinearLayout mDotContainer;
    private LinearLayout mIndicatorLayout;
    private RelativeLayout mTipLayout;
    private TextView mTitle;
    private DisplayImageOptions mImageOptions;
    private int mCurrentIndex;

    private ScheduledExecutorService scheduledExecutorService;
    private ArrayList<PartyRecommendBean> mDataList;
    private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();
    private ArrayList<TextView> mIndicatorViewList = new ArrayList<TextView>();

    public SlideRecommendView(Context context){
        this(context,null);
    }
    public SlideRecommendView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }
    public SlideRecommendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        LayoutInflater.from(context).inflate(R.layout.slide_recommend_view, this);
        mViewPager = (ViewPager)findViewById(R.id.slide_recommend_view_viewpager);
        mTitle = (TextView)findViewById(R.id.slide_recommend_view_title);
        mIndicatorLayout = (LinearLayout)findViewById(R.id.slide_recommend_view_indicator);
        mTipLayout = (RelativeLayout)findViewById(R.id.slide_recommend_view_tip_layout);

        initUI();
        mViewPager.setFocusable(true);
        mAdapter = new SlideRecommendAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());



        mImageOptions = ImageUtils.getDefaultImageOptions(R.mipmap.ic_launcher);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_UI:
                    for(int i = 0;i <mDataList.size();i++){
                        PartyRecommendBean recommendBean = mDataList.get(i);
                        ImageUtils.displayImage(mImageViewList.get(i), recommendBean.getUrl(), mImageOptions);
                    }
                    //mTitle.setText(mDataList.get(0).getTitle());
                    //mTipLayout.setVisibility(View.VISIBLE);
                    //startPlay();
                    break;
                case MSG_SLIDE_TO_NEXT:
                    mViewPager.setCurrentItem(mCurrentIndex);
                    break;
            }
        }

    };

    private class SlideTask implements Runnable{

        @Override
        public void run() {
            synchronized (mViewPager){
                mCurrentIndex = (mCurrentIndex + 1)%mImageViewList.size();
                mHandler.sendEmptyMessage(MSG_SLIDE_TO_NEXT);
            }
        }
    }
    private void initUI(){

        for(int i = 0; i < Constants.SLIDE_RECOMMEND_IMAGE_COUNT;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(R.mipmap.test1);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViewList.add(imageView);

            TextView textView = new TextView(getContext());
            textView.setText(i+"");
            textView.setTextColor(Color.WHITE);
            mIndicatorLayout.addView(textView);
            mIndicatorViewList.add(textView);
        }
    }

    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideTask(), 1, 4, TimeUnit.SECONDS);
    }

    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }

    public void setDataList(ArrayList<PartyRecommendBean> list){

        mDataList = list;
        if(null != mDataList && mDataList.size() > 0) {
            mHandler.sendEmptyMessage(MSG_UPDATE_UI);
        }

    }

    private class SlideRecommendAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return null == mImageViewList ? 0 : mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageViewList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            boolean isAutoPlay = false;
            switch (state){
                case 0:
                    break;
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
            }
        }
    }
}
