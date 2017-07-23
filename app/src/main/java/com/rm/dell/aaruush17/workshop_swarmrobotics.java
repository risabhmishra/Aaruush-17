package com.rm.dell.aaruush17;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Arko Chatterjee on 19-07-2017.
 */

public class workshop_swarmrobotics extends android.support.v4.app.Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.workshop_brainwave, container, false);

        TextView text = (TextView) v.findViewById(R.id.textView3);
        TextView text1 = (TextView) v.findViewById(R.id.textView4);
        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/M_R.ttf");
        text.setTypeface(typeface1);
        text1.setTypeface(typeface1);

        text.setText("HT Labs");
        text1.setText("September 13th and 14th | Price : Rs.5600 for 4");

        return v;
    }

}
