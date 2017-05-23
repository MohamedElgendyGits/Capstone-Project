package com.instanews.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.test.espresso.core.deps.guava.collect.ImmutableList;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class Contract {

    static final String AUTHORITY = "com.udacity.instanews";
    static final String PATH_NEWS = "news";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    @SuppressWarnings("unused")
    public static final class NewsContract implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_NEWS).build();
        public static final String COLUMN_NEWS_TITLE = "title";
        public static final String COLUMN_NEWS_DESCRIPTION = "description";
        public static final String COLUMN_NEWS_AUTHOR = "author";
        public static final String COLUMN_PUBLISHED_DATE = "publishedDate";
        public static final String COLUMN_NEWS_JSON = "newsJson";

        public static final int POSITION_ID = 0;
        public static final int POSITION_NEWS_TITLE = 1;
        public static final int POSITION_NEWS_DESCRIPTION = 2;
        public static final int POSITION_NEWS_AUTHOR = 3;
        public static final int POSITION_PUBLISHED_DATE = 4;
        public static final int POSITION_NEWS_JSON = 5;

        public static final ImmutableList<String> NEWS_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_NEWS_TITLE,
                COLUMN_NEWS_DESCRIPTION,
                COLUMN_NEWS_AUTHOR,
                COLUMN_PUBLISHED_DATE,
                COLUMN_NEWS_JSON
        );
        static final String TABLE_NAME = "news";

        public static Uri makeUriForNews(String news) {
            return URI.buildUpon().appendPath(news).build();
        }

        static String getNewsFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }
    }

}