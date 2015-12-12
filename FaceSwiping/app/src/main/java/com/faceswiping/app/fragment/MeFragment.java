package com.faceswiping.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.R;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    @InjectView(R.id.fragment_me_userImage)
    ImageView userImage;

    @InjectView(R.id.fragment_me_userName)
    TextView userName;

    @InjectView(R.id.fragment_me_userDescribe)
    TextView userDescribe;

    @InjectView(R.id.fragment_me_myInfo)
    LinearLayout myInformation;

    @InjectView(R.id.fragment_me_myPhoto)
    LinearLayout myPhoto;

    @InjectView(R.id.fragment_me_myFile)
    LinearLayout myFile;

    @InjectView(R.id.fragment_me_mySetting)
    LinearLayout mySetting;

    @InjectView(R.id.fragment_me_mySecret)
    LinearLayout mySecret;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {

    }

}
