package parteam.letspartya;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenlibao on 15-12-17.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFm = null;
    private List<Fragment> mFragments;

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFm = fm;
        mFragments = fragments;
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }

    public Fragment getItem(int arg0) {
//        switch (arg0) {
//            case 0:
//                return mFragments.get(0);
//            case 1:
//                return mFragments.get(1);
//            case 2:
//                return mFragments.get(2);
//            default:
//                break;
//        }
        return mFragments.get(arg0);
    }
}
