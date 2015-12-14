package com.faceswiping.app.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.ListBaseAdapter;
import com.faceswiping.app.bean.NewFriendBean;
import com.faceswiping.app.interf.ListItemClickHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wanglin on 15/12/13.
 */
public class NewFriendAdapter extends ListBaseAdapter<NewFriendBean> {

    //是一些小图，放在内存中
    public static DisplayImageOptions optionsImage = new DisplayImageOptions
            .Builder()
            .showImageOnLoading(R.drawable.boom)
            .showImageForEmptyUri(R.drawable.boom)
            .showImageOnFail(R.drawable.boom)
            .cacheInMemory(true)
            .cacheOnDisk(false)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();


    private ListItemClickHelp callback;

    public void setCallBack (ListItemClickHelp callBack){
        this.callback = callBack;
    }

    public static class ViewHolder {

        @InjectView(R.id.new_friends_listItem_friendImage)
        ImageView nf_friendImage;

        @InjectView(R.id.new_friends_listItem_friendName)
        TextView nf_friendName;

        @InjectView(R.id.new_friends_listItem_friendInfo)
        TextView nf_friendInfo;

        @InjectView(R.id.new_friends_listItem_requestFrom)
        TextView nf_requestFrom;

        @InjectView(R.id.new_friends_listItem_friendReceiveButton)
        TextView nf_receiveButton;

        @InjectView(R.id.new_friends_listItem_friendReceivedView)
        TextView nf_receivedView;

        @InjectView(R.id.new_friends_listItem_friendGroupPhoto)
        ImageView nf_friendGroupImage;

        public ViewHolder(View view) {

            ButterKnife.inject(this, view);

        }
    }

    @Override
    protected View getRealView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder vh = null;
        LayoutInflater inflater = getLayoutInflater(parent.getContext());
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.new_friends_listitem, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        NewFriendBean newFriendBean = mDatas.get(position);

        //System.out.println(newFriendBean.getHeadImageUrl());



        if (newFriendBean.getName() != null) {
            vh.nf_friendGroupImage.setVisibility(View.GONE);
            vh.nf_receiveButton.setVisibility(View.GONE);
            vh.nf_requestFrom.setVisibility(View.GONE);
            vh.nf_receivedView.setVisibility(View.VISIBLE);
            vh.nf_friendName.setText(newFriendBean.getName());
            ImageLoader.getInstance().displayImage(newFriendBean.getHeaderImageUrl(), vh.nf_friendImage, optionsImage);
            vh.nf_friendInfo.setText("你好,很高兴认识你~!");

        } else {
            vh.nf_friendGroupImage.setVisibility(View.VISIBLE);
            vh.nf_receiveButton.setVisibility(View.VISIBLE);
            vh.nf_requestFrom.setVisibility(View.VISIBLE);
            vh.nf_receivedView.setVisibility(View.GONE);

            ImageLoader.getInstance().displayImage(newFriendBean.getHeadImageUrl(), vh.nf_friendImage, optionsImage);
            vh.nf_friendInfo.setText("你好,很高兴认识你~!");

            vh.nf_friendName.setText(newFriendBean.getUserName());
            vh.nf_requestFrom.setText("来源:刷脸加好友");
            ImageLoader.getInstance().displayImage(newFriendBean.getImageUrl(), vh.nf_friendGroupImage, optionsImage);
            vh.nf_receiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(v,parent,position,v.getId());
                }
            });
        }
        return convertView;
    }
}
