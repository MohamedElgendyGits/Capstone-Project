package com.instanews.view.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.instanews.R;
import com.instanews.data.Contract;
import com.instanews.model.News;
import com.instanews.sync.NewsSyncJob;
import com.instanews.utils.ConnectionUtils;
import com.instanews.utils.JsonUtils;
import com.instanews.view.adapters.NewsListAdapter;
import com.instanews.view.interfaces.OnNewsSelectedListener;


import static com.instanews.application.InstaNewsConstants.BUNDLE_RECYCLER_LAYOUT;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsFragment extends Fragment implements NewsListAdapter.NewsClickListener ,
         LoaderManager.LoaderCallbacks<Cursor> {

    private FirebaseAnalytics firebaseAnalytics;

    private static final int NEWS_LOADER = 0;

    // declare news list components
    private RecyclerView newsRecyclerView;
    private NewsListAdapter newsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView error;
    private OnNewsSelectedListener onNewsSelectedListener;


    public NewsFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        // Obtain the Fire base Analytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        // initialize recipe recycler view
        newsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_news);
        newsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(mLayoutManager);
        newsAdapter = new NewsListAdapter(this);
        newsRecyclerView.setAdapter(newsAdapter);

        error = (TextView) rootView.findViewById(R.id.error);
        retrieveNewsImmediately();

        NewsSyncJob.initialize(getActivity());
        getLoaderManager().initLoader(NEWS_LOADER, null, this);

        return rootView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnNewsSelectedListener) {
            onNewsSelectedListener = (OnNewsSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnIngredientSelectedListener or OnStepSelectedListener");
        }
    }

    private void retrieveNewsImmediately() {

        if (!ConnectionUtils.isConnected(getActivity()) && newsAdapter.getItemCount() == 0) {
            error.setText(getString(R.string.error_no_network));
            error.setVisibility(View.VISIBLE);
        } else if (!ConnectionUtils.isConnected(getActivity())) {
            Toast.makeText(getActivity(),R.string.error_no_network,Toast.LENGTH_SHORT).show();
        } else {
            NewsSyncJob.syncImmediately(getActivity());
            error.setVisibility(View.GONE);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                Contract.NewsContract.URI,
                Contract.NewsContract.NEWS_COLUMNS.toArray(new String[]{}),
                null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0) {
            error.setVisibility(View.GONE);
        }

        newsAdapter.setCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        newsAdapter.setCursor(null);
    }

    @Override
    public void onNewsClick(int position, View v) {
        String newsJson = newsAdapter.getNewsAtPosition(position);
        News selectedNews = JsonUtils.convertJsonStringToObject(newsJson,News.class);
        onNewsSelectedListener.onNewsSelected(selectedNews);

        sendClicksToFireBase(selectedNews);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            newsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, newsRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void sendClicksToFireBase(News selectedNews) {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, selectedNews.getSection());

        //Logs an app event.
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
        firebaseAnalytics.setMinimumSessionDuration(20000);
        firebaseAnalytics.setSessionTimeoutDuration(500);

        //Sets a user property to a given value.
        firebaseAnalytics.setUserProperty("Section", selectedNews.getSection());
    }

}


