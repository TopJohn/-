package com.faceswiping.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.ListBaseAdapter;
import com.faceswiping.app.bean.ChatBean;
import com.faceswiping.app.bean.MarkedFriendBean;
import com.faceswiping.app.fragment.ChatFragment;
import com.faceswiping.app.util.UIHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wanglin on 15/12/13.
 */
public class MarkedFriendAdapter extends ListBaseAdapter<MarkedFriendBean> {
    public static class ViewHolder {

        @InjectView(R.id.marked_tv_name)
        TextView tv_name;

        @InjectView(R.id.marked_tv_content)
        TextView tv_content;

        @InjectView(R.id.marked_im_header)
        ImageView im_header;

        @InjectView(R.id.marked_tv_button)
        TextView tv_button;

        public ViewHolder(View view) {

            ButterKnife.inject(this, view);

        }
    }

    @Override
    protected View getRealView(int position, View convertView, final ViewGroup parent) {
        ViewHolder vh = null;
        LayoutInflater inflater = getLayoutInflater(parent.getContext());
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.list_marked, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {

            vh = (ViewHolder) convertView.getTag();

        }

        MarkedFriendBean markedFriendBean = mDatas.get(position);

        vh.tv_name.setText(markedFriendBean.getName());
        vh.tv_content.setText(markedFriendBean.getContent());
        ImageLoader.getInstance().displayImage(markedFriendBean.getHeadImageUrl(), vh.im_header, ChatFragment.optionsImage);
        vh.tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
