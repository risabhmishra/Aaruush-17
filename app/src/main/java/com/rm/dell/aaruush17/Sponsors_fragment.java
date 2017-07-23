package com.rm.dell.aaruush17;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sponsors_fragment extends Fragment {


    public Sponsors_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sponsors_fragment, container, false);

        TextView text = (TextView) view.findViewById(R.id.lenovo);
        TextView text1 = (TextView) view.findViewById(R.id.hp);
        TextView text2 = (TextView) view.findViewById(R.id.cisco);
        TextView text3 = (TextView) view.findViewById(R.id.wittyfeed);
        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/M_R.ttf");
        text.setTypeface(typeface1);
        text1.setTypeface(typeface1);
        text2.setTypeface(typeface1);
        text3.setTypeface(typeface1);


        return view;
    }

}
