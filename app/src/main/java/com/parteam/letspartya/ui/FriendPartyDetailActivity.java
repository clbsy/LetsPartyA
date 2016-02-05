package com.parteam.letspartya.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.parteam.letspartya.R;
import com.parteam.letspartya.model.PartiesModel;
import com.parteam.letspartya.model.bean.CommentItem;
import com.parteam.letspartya.model.bean.PartyItem;
import com.parteam.letspartya.ui.widget.SquareImageView;

import java.util.ArrayList;

public class FriendPartyDetailActivity extends BaseActivity {

    private PartyItem mPartyItem;
    private DisplayImageOptions mImageOptions;

    private FriendPartyAdapter mImageAdapter;
    private CommentAdapter mCommentAdapter;

    private TextView mFavView;
    private EditText mCommentEdit;

    private PartiesModel mPartiesModel;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_party_detail);

        mPartiesModel = new PartiesModel();
        mInflater = LayoutInflater.from(this);

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
            mImageAdapter = new FriendPartyAdapter(new ArrayList<String>());
        } else {
            mImageAdapter = new FriendPartyAdapter(mPartyItem.getImages());
        }
        gridView.setAdapter(mImageAdapter);

        ListView commentListView = (ListView) findViewById(R.id.party_detail_comments_list);
        if ((null == mPartyItem) || (null == mPartyItem.getComments())) {
            mCommentAdapter = new CommentAdapter(new ArrayList<CommentItem>());
        } else {
            mCommentAdapter = new CommentAdapter(mPartyItem.getComments());
        }
        commentListView.setAdapter(mCommentAdapter);

        mFavView = (TextView) findViewById(R.id.party_detail_fav);
        mFavView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mPartyItem) {
                    mPartiesModel.updateFav(FriendPartyDetailActivity.this, mPartyItem.getId(), new PartiesModel.OnUpdateFavCallback() {
                        @Override
                        public void updateFav(long fav) {
                            mPartyItem.setFav(fav);
                            mFavView.setText(String.valueOf(mPartyItem.getFav()));
                        }
                    });
                }
            }
        });

        mCommentEdit = (EditText) findViewById(R.id.party_detail_comment_edit);
        findViewById(R.id.party_detail_comment_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mPartyItem) {
                    return;
                }

                String comment = mCommentEdit.getText().toString();
                if ((TextUtils.isEmpty(comment)) || (TextUtils.isEmpty(comment.trim()))) {
                    Toast.makeText(FriendPartyDetailActivity.this, R.string.comment_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                mCommentEdit.setText(null);

                mPartiesModel.updateComments(FriendPartyDetailActivity.this, mPartyItem.getId(), 4, comment, new PartiesModel.OnUpdateCommentsCallback() {
                    @Override
                    public void onUpdateComments(ArrayList<CommentItem> commentItems) {
                        mCommentAdapter.setList(commentItems);
                    }
                });
            }
        });

        if (null != mPartyItem) {
            mFavView.setText(String.valueOf(mPartyItem.getFav()));
        }
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

    private class CommentAdapter extends BaseAdapter {
        private ArrayList<CommentItem> list;

        public CommentAdapter(ArrayList<CommentItem> list) {
            if (null == list) {
                list = new ArrayList<>();
            }

            this.list = list;
        }

        public void setList(ArrayList<CommentItem> list) {
            if (null == list) {
                this.list.clear();
            } else {
                this.list = list;
            }

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommentItem commentItem = list.get(position);
            convertView = mInflater.inflate(R.layout.list_item_comment, parent, false);
            TextView nameView = (TextView) convertView.findViewById(R.id.comment_name);
            TextView contentView = (TextView) convertView.findViewById(R.id.comment_content);
            nameView.setText(commentItem.getDisplayName());
            contentView.setText(commentItem.getContent());
            return convertView;
        }
    }

}
