package parteam.letspartya;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenlibao on 15-12-17.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private GuideFragment mGuideF = null;
    private SettingFragment mSettingF = null;
    private SearchFragment mSearchF = null;
    private FragmentManager mFm = null;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mGuideF = new GuideFragment();
        mSettingF = new SettingFragment();
        mSearchF = new SearchFragment();
    }
    @Override
    public int getCount() {
        return 3;
    }

    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                return mGuideF;
            case 1:
                return mSettingF;
            case 2:
                return mSearchF;
            default:
                break;
        }
        return null;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
