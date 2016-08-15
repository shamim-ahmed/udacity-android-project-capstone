package edu.udacity.android.contentfinder;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import edu.udacity.android.contentfinder.db.ContentFinderContract;
import edu.udacity.android.contentfinder.util.Constants;
import edu.udacity.android.contentfinder.util.WidgetUtils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class KeywordListIntentService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor cursor = null;

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null) {
                    cursor.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                // initialize the new cursor
                // a potential problem was fixed here related to Locale. For searching
                // in database, US locale needs to be used, irrespective of the user's preferred locale.
                Uri searchUri = ContentFinderContract.KeywordEntry.CONTENT_URI;

                String sortOrder = String.format("%s DESC LIMIT %d", ContentFinderContract.KeywordEntry._ID, Constants.WIDGET_KEYWORD_LIMIT);
                cursor = getContentResolver().query(searchUri, null, null, null, sortOrder);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }

            @Override
            public int getCount() {
                return cursor == null ? 0 : cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_list_item);

                ContentValues values = WidgetUtils.readCursor(cursor);
                WidgetUtils.populateView(values, views, getApplicationContext());

                final Intent fillInIntent = new Intent();
                views.setOnClickFillInIntent(R.id.list_item_root, fillInIntent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (cursor != null && cursor.moveToPosition(position)) {
                    int index = cursor.getColumnIndex(ContentFinderContract.KeywordEntry._ID);
                    return cursor.getLong(index);
                }

                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
