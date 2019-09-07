package com.nanodegree.hyunyong.microdotstatus;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RemoteViews;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.nanodegree.hyunyong.microdotstatus.data.City;
import com.nanodegree.hyunyong.microdotstatus.data.Microdot;
import com.nanodegree.hyunyong.microdotstatus.data.Repository;
import com.nanodegree.hyunyong.microdotstatus.data.ResponseState;
import com.nanodegree.hyunyong.microdotstatus.data.Webservice;
import com.nanodegree.hyunyong.microdotstatus.db.AppDatabase;
import com.nanodegree.hyunyong.microdotstatus.db.CityDao;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementation of App Widget functionality.
 */
public class AQIWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.aqiwidget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // There may be multiple widgets active, so update all of them
        CityDao dao = Room.databaseBuilder(context, AppDatabase.class, "app-db").allowMainThreadQueries().build().cityDao();
        Repository repository = new Repository(new Retrofit.Builder()
                .baseUrl("https://api.waqi.info/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Webservice.class));
        if (dao == null) return;
        List<City> cityList = dao.getCities();
        for (int i = 0; i < cityList.size() ; i++) {
            final City city = cityList.get(i);
            repository.getFeedFromLocation(city.getGeo().get(0), city.getGeo().get(1)).observeForever(new Observer<ResponseState>() {
                @Override
                public void onChanged(ResponseState responseState) {
                    if ("ok".equals(responseState.getStatus())) {
                        Microdot microdot = responseState.getData();
                        views.setTextViewText(R.id.city_name, Utils.getSimpleCityName(city.getName()));
                        views.setImageViewResource(R.id.iv_icon, Utils.getDrawableResourceByAqi(microdot.getAqi()));
                        views.setTextViewText(R.id.tv_marker, String.valueOf(microdot.getAqi()));
                        views.setInt(R.id.bg_conatainer, "setBackgroundResource", Utils.getColorResourceByAqi(microdot.getAqi()));
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                }
            });
        }
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
}

