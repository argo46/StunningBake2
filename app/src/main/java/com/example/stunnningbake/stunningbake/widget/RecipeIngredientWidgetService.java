package com.example.stunnningbake.stunningbake.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by PC-Lenovo on 17/09/2017.
 */

public class RecipeIngredientWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RecipeIngredientViewsFactory(this.getApplicationContext(),intent);
    }
}
