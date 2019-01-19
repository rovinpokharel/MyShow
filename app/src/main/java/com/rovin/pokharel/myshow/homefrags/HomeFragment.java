package com.rovin.pokharel.myshow.homefrags;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rovin.pokharel.myshow.NowShowing;
import com.rovin.pokharel.myshow.R;
import com.rovin.pokharel.myshow.UpcomingActivity;
//import com.rovin.pokharel.myshow.helper.SimpleFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button btnNowShowing, btnUpcoming;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnNowShowing = (Button)view.findViewById(R.id.home_btn_nowshowing);
        btnUpcoming = (Button) view.findViewById(R.id.home_btn_upcoming);

        btnNowShowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NowShowing.class));
            }
        });

        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UpcomingActivity.class));
            }
        });
        return view;
    }
}
