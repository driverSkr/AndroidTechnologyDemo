package com.ethan.customControls.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ethan.customControls.R;
import com.ethan.customControls.widget.CustomBanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Integer> imageList;
    private CustomBanner customBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addImageList();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        customBanner = view.findViewById(R.id.cb_banner);
        customBanner.setView(imageList);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        customBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        customBanner.stop();
    }

    private void addImageList(){
        imageList = new ArrayList<>();
        imageList.add(R.mipmap.banner_1);
        imageList.add(R.mipmap.banner_2);
        imageList.add(R.mipmap.banner_3);
        imageList.add(R.mipmap.banner_4);
        imageList.add(R.mipmap.banner_5);
    }
}