package com.nanodegree.hyunyong.microdotstatus;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.data.Microdot;
import com.nanodegree.hyunyong.microdotstatus.data.Repository;
import com.nanodegree.hyunyong.microdotstatus.data.ResponseState;
import com.nanodegree.hyunyong.microdotstatus.data.Webservice;
import com.nanodegree.hyunyong.microdotstatus.db.AppDatabase;
import com.nanodegree.hyunyong.microdotstatus.db.CityDao;
import com.nanodegree.hyunyong.microdotstatus.view.MainActivity;
import com.nanodegree.hyunyong.microdotstatus.view.SearchActivity;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementation of App Widget functionality.
 */
public class AQIWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.aqiwidget);

        Intent searchIntent = new Intent(context, SearchActivity.class);
        searchIntent.putExtra(SearchActivity.EXTRA_FROM_WIDGET, true);
        PendingIntent searchPendingIntent = PendingIntent.getActivity(context, 0, searchIntent, 0);
        views.setOnClickPendingIntent(R.id.add_location, searchPendingIntent);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.current_city_container, pendingIntent);


        // There may be multiple widgets active, so update all of them
        CityDao dao = Room.databaseBuilder(context, AppDatabase.class, "app-db").allowMainThreadQueries().build().cityDao();
        Repository repository = new Repository(new Retrofit.Builder()
                .baseUrl("https://api.waqi.info/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Webservice.class));
        if (dao == null) return;


        // current city widget
        final City currentCity = dao.getCurrentCity(true);
        if (currentCity == null) return;
        repository.getFeedFromLocation(currentCity.getGeo().get(0), currentCity.getGeo().get(1)).observeForever(new Observer<ResponseState>() {
            @Override
            public void onChanged(ResponseState responseState) {
                if ("ok".equals(responseState.getStatus())) {
                    Microdot microdot = responseState.getData();
                    views.setTextViewText(R.id.city_name, Utils.getSimpleCityName(currentCity.getName()));
                    views.setImageViewResource(R.id.iv_icon, Utils.getDrawableResourceByAqi(microdot.getAqi()));
                    views.setTextViewText(R.id.tv_marker, String.valueOf(microdot.getAqi()));
                    views.setInt(R.id.current_city_container, "setBackgroundResource", Utils.getColorResourceByAqi(microdot.getAqi()));
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }
            }
        });

        // second setting widget
        final City widget = dao.getCity(true);
        if (widget == null) return;
        repository.getFeedFromLocation(widget.getGeo().get(0), widget.getGeo().get(1)).observeForever(new Observer<ResponseState>() {
            @Override
            public void onChanged(ResponseState responseState) {
                if ("ok".equals(responseState.getStatus())) {
                    Microdot microdot = responseState.getData();
                    views.setTextViewText(R.id.city_name2, Utils.getSimpleCityName(widget.getName()));
                    views.setImageViewResource(R.id.iv_icon2, Utils.getDrawableResourceByAqi(microdot.getAqi()));
                    views.setTextViewText(R.id.tv_marker2, String.valueOf(microdot.getAqi()));
                    views.setInt(R.id.selected_widget_container, "setBackgroundResource", Utils.getColorResourceByAqi(microdot.getAqi()));
                    views.setViewVisibility(R.id.selected_widget_container, View.VISIBLE);
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }
            }
        });
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            // refresh current widget
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            int[] widgetId = mgr.getAppWidgetIds(new ComponentName(context, getClass()));
            if (widgetId.length > 0) {
                updateAppWidget(context, mgr, mgr.getAppWidgetIds(new ComponentName(context, getClass()))[0]);
            }
        }
        super.onReceive(context, intent);
    }
}

