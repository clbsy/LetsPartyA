package parteam.letspartya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

public class GuideFragment extends Fragment {
    private static final String TAG = GuideFragment.class.getSimpleName();

    private ViewPager mViewPager;

    private DisplayImageOptions mImageOptions;

    private LinearLayout mIndicatorParent;

    private ArrayList<Integer> mData = new ArrayList<>();
    private LoopPageAdapter mPageAdapter;

    private RecommendListAdapter mListAdapter;

    public static GuideFragment newInstance() {
        GuideFragment fragment = new GuideFragment();
        return fragment;
    }

    public GuideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageOptions = Utils.getDefaultImageOptions(R.mipmap.ic_launcher);
        mPageAdapter = new LoopPageAdapter();
        mListAdapter = new RecommendListAdapter();

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
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.guide_recommend_pages);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(2);
        mViewPager.addOnPageChangeListener(mPageAdapter);

        //TODO Not show now
        mIndicatorParent = (LinearLayout) view.findViewById(R.id.guide_indicator_parent);
        for (int i = 0; i < mData.size(); i++) {
            ImageView dotView = new ImageView(getActivity());
            dotView.setImageResource(R.mipmap.dot_w);
            dotView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mIndicatorParent.addView(dotView);
        }

        ListView listView = (ListView) view.findViewById(R.id.guide_recommend_list);
        listView.setAdapter(mListAdapter);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            Utils.displayImage(imageView, mData.get(realPosition), mImageOptions);
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

    private class RecommendListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                viewHolder.imageView = new FixedSizeImageView(getActivity());
                viewHolder.imageView.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT));
                viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView = viewHolder.imageView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Utils.displayImage(viewHolder.imageView, mData.get(position), mImageOptions);
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
