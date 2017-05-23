package com.instanews.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instanews.R;
import com.instanews.application.InstaNewsConstants;
import com.instanews.model.News;
import com.instanews.model.NewsMedia;
import com.instanews.utils.DateUtils;
import com.instanews.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsDetailsFragment extends Fragment {

    // declare ui components
    private TextView titleTextView;
    private ImageView thumbnailImageView;
    private TextView authorTextView;
    private TextView publishedDateTextView;
    private TextView descriptionTextView;
    private ImageView originalUrlImageView;

    private News newsDetails;


    public NewsDetailsFragment() {
        //required empty constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String newsJsonString = bundle.getString(InstaNewsConstants.MAIN_DETAIL_INTENT_KEY);
            newsDetails = JsonUtils.convertJsonStringToObject(newsJsonString, News.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_details, container, false);

        // initialize ui components
        titleTextView = (TextView) rootView.findViewById(R.id.textView_news_details_title);
        thumbnailImageView = (ImageView) rootView.findViewById(R.id.imageView_news_details_thumbnail);
        authorTextView = (TextView) rootView.findViewById(R.id.textView_news_details_author);
        publishedDateTextView = (TextView) rootView.findViewById(R.id.textView_news_details_publishedDate);
        descriptionTextView = (TextView) rootView.findViewById(R.id.textView_news_details_description);
        originalUrlImageView = (ImageView) rootView.findViewById(R.id.imageVew_news_details_original_url);

        fillUiFieldsWithData();

        return rootView;
    }

    private void fillUiFieldsWithData() {

        String title = newsDetails.getTitle();
        titleTextView.setText(title);

        ArrayList<NewsMedia> newsMedia = newsDetails.getMultimedia();
        if(newsMedia != null && newsMedia.size() > 0){
            String thumbnailUrl = newsDetails.getMultimedia().get(4).getThumbnailUrl();
            Picasso.with(getActivity()).load(thumbnailUrl).into(thumbnailImageView);
        }else {
            thumbnailImageView.setImageResource(R.drawable.news_default_image);
        }

        String author = newsDetails.getAuthor();
        authorTextView.setText(author);

        String publishedDate = newsDetails.getPublishedDate();
        publishedDate = DateUtils.convertToPrettyDate(publishedDate);
        publishedDateTextView.setText(publishedDate);

        String description = newsDetails.getDescription();
        descriptionTextView.setText(description);

        originalUrlImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = newsDetails.getNewsUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}

