package com.faceswiping.app.fragment;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {
    private ImageView userImage;
    private TextView userName;
    private TextView userDescribe;
    private LinearLayout myInformation;
    private LinearLayout myPhoto;
    private LinearLayout myFile;
    private LinearLayout mySetting;
    private LinearLayout mySecret;

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

    private void initView(){
        userImage = (ImageView)getActivity().findViewById(R.id.fragment_me_userImage);
        userName = (TextView)getActivity().findViewById(R.id.fragment_me_userName);
        userDescribe = (TextView)getActivity().findViewById(R.id.fragment_me_userDescribe);

        myInformation = (LinearLayout)getActivity().findViewById(R.id.fragment_me_myInfo);
        myPhoto = (LinearLayout)getActivity().findViewById(R.id.fragment_me_myPhoto);
        myFile = (LinearLayout)getActivity().findViewById(R.id.fragment_me_myFile);
        mySetting = (LinearLayout)getActivity().findViewById(R.id.fragment_me_mySetting);
        mySecret = (LinearLayout)getActivity().findViewById(R.id.fragment_me_mySecret);
    }

}
