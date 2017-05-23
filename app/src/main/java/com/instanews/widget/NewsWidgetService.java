package com.instanews.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class NewsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsWidgetData(this,intent);
    }
}
