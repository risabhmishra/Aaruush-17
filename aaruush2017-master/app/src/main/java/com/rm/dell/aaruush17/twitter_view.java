package com.rm.dell.aaruush17;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
/**
 * A simple {@link Fragment} subclass.
 */
public class twitter_view extends Fragment {

    public static final String TAG = "TimeLineActivity";

    private static final String baseURl = "https://twitter.com";

    private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/Aaruush_Srmuniv\">Tweets by Aaruush_Srmuniv</a> <script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";

View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_twitter_view, container, false);
        load_background_color();

        WebView webView = (WebView)v.findViewById(R.id.timeline_webview);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);






        return v;
    }

    private void load_background_color() {
        WebView webView = (WebView) v.findViewById(R.id.timeline_webview);
        //webView.setBackgroundColor(getResources().getColor(R.color.twitter_dark));
        webView.setBackgroundColor(0); // transparent
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}