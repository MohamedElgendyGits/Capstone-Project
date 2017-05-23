package com.instanews.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.instanews.R;
import com.instanews.application.InstaNewsConstants;
import com.instanews.data.Contract;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsWidgetData implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;
    private Intent intent;

    //For obtaining the activity's context and intent
    public NewsWidgetData(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initCursor(){
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        /**This is done because the widget runs as a separate thread
         when compared to the current app and hence the app's data won't be accessible to it */

        cursor = context.getContentResolver().query(Contract.NewsContract.URI,
                Contract.NewsContract.NEWS_COLUMNS.toArray(new String[]{}),
                null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        /** Listen for data changes and initialize the cursor again **/
        initCursor();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        /** Populate your widget's single list item **/
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_news_widget);
        cursor.moveToPosition(i);

        remoteViews.setTextViewText(R.id.textView_item_widget_news_header,cursor.getString(Contract.NewsContract.POSITION_NEWS_TITLE));


        // set Onclick Item Intent
        Intent onClickItemIntent = new Intent();
        int newsColumn = cursor.getColumnIndex(Contract.NewsContract.COLUMN_NEWS_JSON);
        onClickItemIntent.putExtra(InstaNewsConstants.MAIN_DETAIL_INTENT_KEY,cursor.getString(newsColumn));

        remoteViews.setOnClickFillInIntent(R.id.list_item_widget_row,onClickItemIntent);


        return remoteViews;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public long getItemId(int i) {
        cursor.moveToPosition(i);
        return cursor.getLong(cursor.getColumnIndex(Contract.NewsContract._ID));
    }

    @Override
    public void onDestroy() {
        if (cursor!=null)
            cursor.close();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
