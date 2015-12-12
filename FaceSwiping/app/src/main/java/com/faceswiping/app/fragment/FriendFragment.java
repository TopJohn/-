package com.faceswiping.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.faceswiping.app.R;
import com.faceswiping.app.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {

    @InjectView(R.id.fragment_friend_gridView)
    GridView gridView;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
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

    }

}
