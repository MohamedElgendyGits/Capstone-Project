package com.instanews.view.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instanews.R;
import com.instanews.data.Contract;
import com.instanews.model.News;
import com.instanews.model.NewsMedia;
import com.instanews.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.DataObjectHolder> {

    private static NewsClickListener newsClickListener;
    private Cursor cursor;
    private Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView newsThumbnailImageView;
        TextView newsHeaderTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);

            newsThumbnailImageView = (ImageView) itemView.findViewById(R.id.imageView_item_news_thumbnail);
            newsHeaderTextView = (TextView) itemView.findViewById(R.id.textView_item_news_header);

            // adding Listener...
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            newsClickListener.onNewsClick(getAdapterPosition(), v);
        }
    }

    public NewsListAdapter(NewsClickListener newsClickListener) {
        this.newsClickListener = newsClickListener;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        cursor.moveToPosition(position);


        String newsJson = cursor.getString(Contract.NewsContract.POSITION_NEWS_JSON);
        ArrayList<NewsMedia> newsMedia = JsonUtils.convertJsonStringToObject(newsJson,News.class).getMultimedia();
        if(newsMedia != null && newsMedia.size() > 0){
            String thumbnailUrl = newsMedia.get(1).getThumbnailUrl();
            Picasso.with(context).load(thumbnailUrl).into(holder.newsThumbnailImageView);
        }else {
            holder.newsThumbnailImageView.setImageResource(R.drawable.news_default_image);
        }

        String newsHeader = cursor.getString(Contract.NewsContract.POSITION_NEWS_TITLE);
        holder.newsHeaderTextView.setText(newsHeader);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }

    public String getNewsAtPosition(int position) {
        cursor.moveToPosition(position);
        return cursor.getString(Contract.NewsContract.POSITION_NEWS_JSON);
    }

    public interface NewsClickListener {
        void onNewsClick(int position, View v);
    }
}


