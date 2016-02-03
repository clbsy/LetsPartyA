package com.parteam.letspartya.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.parteam.letspartya.R;
import com.parteam.letspartya.model.bean.PartyItem;
import com.parteam.letspartya.ui.widget.SquareImageView;

import java.util.ArrayList;

public class FriendPartyDetailActivity extends AppCompatActivity {

    private PartyItem mPartyItem;
    private DisplayImageOptions mImageOptions;

    private FriendPartyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_party_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPartyItem = (PartyItem) getIntent().getSerializableExtra("partyItem");
        if (null != mPartyItem) {
            toolbar.setTitle(mPartyItem.getTopical());
        }
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mImageOptions = ImageUtils.getDefaultImageOptions(R.mipmap.ic_launcher);
        GridView gridView = (GridView) findViewById(R.id.party_detail_grid);
        if ((null == mPartyItem) || (null == mPartyItem.getImages())) {
            mAdapter = new FriendPartyAdapter(new ArrayList<String>());
        } else {
            mAdapter = new FriendPartyAdapter(mPartyItem.getImages());
        }
        gridView.setAdapter(mAdapter);
    }

    private class FriendPartyAdapter extends BaseAdapter {
        private ArrayList<String> mImages;

        public FriendPartyAdapter(ArrayList<String> images) {
            mImages = images;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public Object getItem(int position) {
            return mImages.get(position);
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
                convertView = viewHolder.imageView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ImageUtils.displayImage(viewHolder.imageView, mImages.get(position), mImageOptions);
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView imageView;

        public ViewHolder() {
            imageView = new SquareImageView(FriendPartyDetailActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

}
