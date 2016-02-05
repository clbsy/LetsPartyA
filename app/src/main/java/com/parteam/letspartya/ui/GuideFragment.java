package com.parteam.letspartya.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import com.parteam.letspartya.R;
import com.parteam.letspartya.model.OnDataFetchedCallback;
import com.parteam.letspartya.model.PartiesModel;
import com.parteam.letspartya.model.RecommendModel;
import com.parteam.letspartya.model.bean.PartyItem;
import com.parteam.letspartya.model.bean.PartyRecommendBean;
import com.parteam.letspartya.ui.widget.FixedSizeImageView;
import com.parteam.letspartya.ui.widget.SlideRecommendView;

public class GuideFragment extends Fragment implements OnDataFetchedCallback<PartyItem> {
    private static final String TAG = GuideFragment.class.getSimpleName();

    private ViewPager mViewPager;
    private DisplayImageOptions mImageOptions;
    private LinearLayout mIndicatorParent;
    private ArrayList<Integer> mData = new ArrayList<>();
    private LoopPageAdapter mPageAdapter;
    private SlideRecommendView mRecommendView;
    private ArrayList<PartyRecommendBean> mRecommendList;
    private RecommendModel mRecommendModel;

    private FriendPartiesAdapter mPartiesAdapter;
    private ArrayList<PartyItem> mFriendParties = new ArrayList<>();
    private PartiesModel mPartiesModel;

    private LayoutInflater mInflater;

    private boolean mDestroyed;

    public static GuideFragment newInstance() {
        GuideFragment fragment = new GuideFragment();
        return fragment;
    }

    public GuideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDestroyed = false;
        mImageOptions = ImageUtils.getDefaultImageOptions(R.mipmap.ic_launcher);
        mPageAdapter = new LoopPageAdapter();
        mPartiesAdapter = new FriendPartiesAdapter();

        mRecommendModel = new RecommendModel(getActivity());
        mRecommendList = mRecommendModel.getPartyRecommend();
        Log.e(TAG,"mRecommendList size = " + mRecommendList.size());
        mRecommendModel.syncPartyRecommendFromNet();
        mPartiesModel = new PartiesModel();
        mPartiesModel.setOnItemDataFetchedCallback(this);
        mPartiesModel.getFriendParties(getActivity());

        mData.add(R.mipmap.test1);
        mData.add(R.mipmap.test2);
        mData.add(R.mipmap.test3);
        mData.add(R.mipmap.test4);
        mData.add(R.mipmap.test5);
        mData.add(R.mipmap.test6);
        mData.add(R.mipmap.test7);
        mData.add(R.mipmap.test8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        mRecommendView = (SlideRecommendView)view.findViewById(R.id.fragment_guide_slide_recommend_view);
        mRecommendView.setDataList(mRecommendList);
        mRecommendModel.setRecommendCallback(new RecommendModel.RecommendCallback() {
            @Override
            public void onDataChanged(ArrayList<PartyRecommendBean> recommendBeans) {
                mRecommendView.setDataList(recommendBeans);
            }
        });

        GridView gridView = (GridView) view.findViewById(R.id.guide_recommend_list);
        gridView.setAdapter(mPartiesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartyItem item = mFriendParties.get(position);
                Intent intent = new Intent(getActivity(), FriendPartyDetailActivity.class);
                intent.putExtra("partyItem", item);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDestroyed = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDataFetched(ArrayList<PartyItem> list) {
        if (mDestroyed) {
            return;
        }

        mFriendParties.clear();
        mFriendParties.addAll(list);
        mPartiesAdapter.notifyDataSetChanged();
        list.clear();
    }

    private class LoopPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            int size = mData.size();
            return (0 == size) ? size : (size + 2);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = (position - 1 + mData.size()) % mData.size();
            FixedSizeImageView imageView = new FixedSizeImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            ImageUtils.displayImage(imageView, mData.get(realPosition), mImageOptions);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (2 < getCount()) {
                if (0 == position) {
                    mViewPager.setCurrentItem(getCount() - 1 - 1, false);
                } else if (position == getCount() - 1) {
                    mViewPager.setCurrentItem(1, false);
                }

                int realPosition = (position - 1 + mData.size()) % mData.size();
                for (int i = 0; i < mIndicatorParent.getChildCount(); i++) {
                    ImageView dotView = (ImageView) mIndicatorParent.getChildAt(realPosition);
                    if (null != dotView) {
                        if (i == realPosition) {
                            dotView.setImageResource(R.mipmap.dot_1);
                        } else {
                            dotView.setImageResource(R.mipmap.dot_w);
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class FriendPartiesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mFriendParties.size();
        }

        @Override
        public Object getItem(int position) {
            return mFriendParties.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.litem_friend_party, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            PartyItem item = mFriendParties.get(position);
            ImageUtils.displayImage(viewHolder.imageView, item.getThumbnail(), mImageOptions);
            viewHolder.titleView.setText(item.getTopical());
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView imageView;
        TextView titleView;

        public ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.item_friend_party_image);
            titleView = (TextView) itemView.findViewById(R.id.item_friend_party_text);
        }
    }
}
