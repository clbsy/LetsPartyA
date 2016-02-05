package com.parteam.letspartya.ui;

import android.support.v7.app.AppCompatActivity;

import com.parteam.letspartya.net.NetService;

/**
 * Created by mengce on 2016/1/10.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetService.cancelRequest(this);
    }
}
