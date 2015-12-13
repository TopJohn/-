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
import com.faceswiping.app.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by John on 15/12/12.
 */
public class ChatAdapter extends ListBaseAdapter<ChatBean> {


    public static class ViewHolder {

        @InjectView(R.id.ll_root_view)
        LinearLayout ll_root_view;

        @InjectView(R.id.tv_name)
        TextView tv_name;

        @InjectView(R.id.tv_content)
        TextView tv_content;

        @InjectView(R.id.im_header)
        ImageView im_header;

        @InjectView(R.id.tv_date)
        TextView tv_date;

        public ViewHolder(View view) {

            ButterKnife.inject(this, view);

        }
    }

    @Override
    protected View getRealView(int position, View convertView, final ViewGroup parent) {
        ViewHolder vh = null;
        LayoutInflater inflater = getLayoutInflater(parent.getContext());
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.list_chat, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        ChatBean chatBean = mDatas.get(position);

        vh.tv_name.setText(chatBean.getName());
        vh.tv_content.setText(chatBean.getContent());
        vh.tv_date.setText(chatBean.getDate());
        switch (position) {

            case 0:
                vh.im_header.setImageResource(R.drawable.header_0);
                break;
            case 1:
                vh.im_header.setImageResource(R.drawable.header_1);
                break;
            case 2:
                vh.im_header.setImageResource(R.drawable.header_2);
                break;
            case 3:
                vh.im_header.setImageResource(R.drawable.header_3);
                break;
            case 4:
                vh.im_header.setImageResource(R.drawable.header_4);
                break;
            case 5:
                vh.im_header.setImageResource(R.drawable.header_5);
                break;
            case 6:
                vh.im_header.setImageResource(R.drawable.header_6);
                break;
            default:
                break;

        }

        if (position == 1) {
            vh.ll_root_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showNewFriendsActivity(parent.getContext());
                }
            });
        }

        return convertView;
    }
}