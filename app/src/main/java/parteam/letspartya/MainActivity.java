package parteam.letspartya;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private final static String TAG = "MainActivity";
    private ViewPager mMainViewPager = null;
    private TabListener mTabListener = null;
    private View mGuidePage = null;
    private View mSettingPage = null;
    private View mSearchPage = null;
    private GuideFragment mGuideF = null;
    private SettingFragment mSettingF = null;
    private SearchFragment mSearchF = null;
    private MainViewPagerAdapter mMainViewPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        setContentView(R.layout.activity_main);
        mMainViewPager = (ViewPager)this.findViewById(R.id.main_view_pager);
        mGuideF = GuideFragment.newInstance(null,null);
        mSettingF = SettingFragment.newInstance(null,null);
        mSearchF = SearchFragment.newInstance(null,null);
        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(mGuideF);
        fragments.add(mSettingF);
        fragments.add(mSearchF);
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments);
        mMainViewPager.setAdapter(mMainViewPagerAdapter);
        mMainViewPager.setCurrentItem(0);
        mTabListener = new TabListener();
        mGuidePage = (TextView)findViewById(R.id.guide_page);
        mGuidePage.setOnClickListener(mTabListener);
        mSettingPage = (TextView)findViewById(R.id.setting_page);
        mSettingPage.setOnClickListener(mTabListener);
        mSearchPage = (TextView)findViewById(R.id.search_page);
        mSearchPage.setOnClickListener(mTabListener);
    }

    public class TabListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v == mGuidePage) {
                mMainViewPager.setCurrentItem(0);
            }else if (v == mSettingPage){
                mMainViewPager.setCurrentItem(1, true);
            }else if (v == mSearchPage) {
                mMainViewPager.setCurrentItem(2, true);
            }
        }
    }
}
