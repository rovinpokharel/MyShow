package com.rovin.pokharel.myshow.homefrags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovin.pokharel.myshow.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
    private ImageView imgRovin, imgBiraj, imgPuspa;
    private TextView tvDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        imgRovin = (ImageView) view.findViewById(R.id.rovin);
        imgBiraj = (ImageView) view.findViewById(R.id.biraj);
        imgPuspa = (ImageView) view.findViewById(R.id.puspa);
        tvDesc = (TextView) view.findViewById(R.id.pp_description);

        tvDesc.setText("My Show is mobile application based booking system, providing fully automated online ticketing, registration, and booking services to people for easiness.");

        imgRovin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDesc.setText("Rovin Pokharel\n 015-329\n rovinpokharel123@gmail.com");
            }
        });
        imgBiraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDesc.setText("Biraj Raut\n 015-308\n raute_biraj@gmail.com");
            }
        });

        imgPuspa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDesc.setText("Pusparaj Khanal\n 015-316\n jhadi_puspe@gmail.com");
            }
        });
        return view;
    }

}
