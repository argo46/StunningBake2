package com.example.stunnningbake.stunningbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Ingredient;
import com.example.stunnningbake.stunningbake.ui.MainActivity;

import java.util.ArrayList;

import static com.example.stunnningbake.stunningbake.widget.UpdateIngredientService.FROM_ACTIVITY_INGREDIENTS_LIST;
import static com.example.stunnningbake.stunningbake.widget.UpdateIngredientService.STRING_FROM_ACTIVITY_INGREDIENT_LIST;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidgetProvider extends AppWidgetProvider {

    public static final String REMOTEVIEW_INGREDIENT_LIST = "REMOTE_VIEW_INGREDIENT_LIST";
    public static final String REMOTEVIEW_BUNDLE = "REMOTEVIEW_BUNDLE";

    static ArrayList<Ingredient> ingredients = new ArrayList();
    static String recipeName;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget_provider);
        if (recipeName.isEmpty()){
            views.setTextViewText(R.id.appwidget_text, widgetText);
        } else {
            views.setTextViewText(R.id.appwidget_text, recipeName +" " +context.getResources().getString(R.string.ingredients));
        }


        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        Intent intent1 = new Intent(context, RecipeIngredientWidgetService.class);
        views.setRemoteAdapter(R.id.list_view_appwidget, intent1);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,RecipeIngredientWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            ingredients = intent.getParcelableArrayListExtra(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view_appwidget);
            recipeName = intent.getStringExtra(STRING_FROM_ACTIVITY_INGREDIENT_LIST);
            RecipeIngredientWidgetProvider.updateIngredientWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }

    }
}

