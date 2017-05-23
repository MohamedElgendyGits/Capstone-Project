package com.instanews.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.instanews.R;
import com.instanews.application.InstaNewsConstants;
import com.instanews.model.News;
import com.instanews.utils.FragmentUtils;
import com.instanews.utils.JsonUtils;
import com.instanews.view.fragments.NewsDetailsFragment;
import com.instanews.view.fragments.NewsFragment;
import com.instanews.view.interfaces.OnNewsSelectedListener;

public class MainActivity extends AppCompatActivity implements OnNewsSelectedListener{

    // Flag determines if this is a one or two pane layout
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null){
            FragmentUtils.replaceFragment(this,new NewsFragment(),R.id.fragment_container,false,"",false);
        }

        // Call this to determine which layout we are in (tablet or phone)
        if (findViewById(R.id.fragment_detail_container) != null) {
            isTwoPane = true;
        }

        if(getIntent().getExtras() != null) {
            // coming from widget
            String newsJsonString = getIntent().getExtras().getString(InstaNewsConstants.MAIN_DETAIL_INTENT_KEY);
            onNewsSelected(JsonUtils.convertJsonStringToObject(newsJsonString,News.class));
        }
    }

    @Override
    public void onNewsSelected(News news) {

        // attach data to bundle
        Bundle bundle = new Bundle();
        String newsJsonString = JsonUtils.convertObjectToJsonString(news,News.class);
        bundle.putString(InstaNewsConstants.MAIN_DETAIL_INTENT_KEY, newsJsonString);
        // set bundle to the newsDetails fragment
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        newsDetailsFragment.setArguments(bundle);


        if (isTwoPane) {
            FragmentUtils.replaceFragment(this,newsDetailsFragment,R.id.fragment_detail_container,false,"",false);
        }
        else {
            FragmentUtils.replaceFragment(this,newsDetailsFragment,R.id.fragment_container,true,"",true);
        }
    }
}
