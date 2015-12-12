package com.faceswiping.app.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.faceswiping.app.R;
import com.faceswiping.app.adapter.ChatAdapter;
import com.faceswiping.app.base.BaseFragment;
import com.faceswiping.app.bean.ChatBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {


    @InjectView(R.id.listview)
    ListView listView;

    private ChatAdapter adapter;

    private ArrayList<ChatBean> mDatas;

    private String json = "[{\n" +
            "  \"name\": \"John\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/0.png\",\n" +
            "  \"content\": \"刷脸加好友的功能好赞啊！！！\",\n" +
            "  \"date\": \"12:00\"\n" +
            "}, {\n" +
            "  \"name\": \"新的好友\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/1.png\",\n" +
            "  \"content\": \"TianTian通过“刷脸加好友”加你为好友\",\n" +
            "  \"date\": \"11:56\"\n" +
            "}, {\n" +
            "  \"name\": \"RedLight\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/2.png\",\n" +
            "  \"content\": \"我想要你和你一起去！\",\n" +
            "  \"date\": \"11:36\"\n" +
            "}, {\n" +
            "  \"name\": \"Marsboyding\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/3.png\",\n" +
            "  \"content\": \"啥时候再一起去吃好吃的？\",\n" +
            "  \"date\": \"11:27\"\n" +
            "}, {\n" +
            "  \"name\": \"Judy\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/4.png\",\n" +
            "  \"content\": \"唔～囧囧辛苦啦～\",\n" +
            "  \"date\": \"11:20\"\n" +
            "}, {\n" +
            "  \"name\": \"Lin\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/5.png\",\n" +
            "  \"content\": \"什么鬼！\",\n" +
            "  \"date\": \"10:25\"\n" +
            "}, {\n" +
            "  \"name\": \"Yanxin\",\n" +
            "  \"headImageUrl\": \"http://7xp4qa.com1.z0.glb.clouddn.com/6.png\",\n" +
            "  \"content\": \"我想要和你一起去上海参加比赛！\",\n" +
            "  \"date\": \"10:24\"\n" +
            "}]\n";

    //是一些小图，放在内存中
    public static DisplayImageOptions optionsImage = new DisplayImageOptions
            .Builder()
            .showImageOnLoading(R.drawable.load_default)
            .showImageForEmptyUri(R.drawable.load_fail)
            .showImageOnFail(R.drawable.load_fail)
            .cacheInMemory(true)
            .cacheOnDisk(false)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.inject(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        adapter = new ChatAdapter();

        mDatas = new Gson().fromJson(json, new TypeToken<List<ChatBean>>() {
        }.getType());

        adapter.setData(mDatas);
        listView.setAdapter(adapter);

    }
}
