package com.swingstats.pga;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * User: jconley
 * Date: 7/25/2014
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewFactory(this.getApplicationContext(), intent);
    }
}