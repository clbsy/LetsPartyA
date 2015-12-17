package parteam.letspartya;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements GuideFragment.OnFragmentInteractionListener ,
                                                              SettingFragment.OnFragmentInteractionListener,
                                                              SearchFragment.OnFragmentInteractionListener{
    private final static String TAG = "MainActivity";
    private ViewPager mMainViewPager = null;
    private TabListener mTabListener = null;
    private View mGuidePage = null;
    private View mSettingPage = null;
    private View mSearchPage = null;
    private MainViewPagerAdapter mMainViewPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        setContentView(R.layout.activity_main);
        mMainViewPager = (ViewPager)this.findViewById(R.id.main_view_pager);

        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mMainViewPager.setAdapter(mMainViewPagerAdapter);
        mTabListener = new TabListener();
        mGuidePage = (View)findViewById(R.id.guide_page);
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
                mMainViewPager.setCurrentItem(1);
            }else if (v == mSearchPage) {
                mMainViewPager.setCurrentItem(2);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
