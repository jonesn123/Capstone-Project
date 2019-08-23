package com.nanodegree.hyunyong.microdotstatus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BindingUtil {
    @BindingAdapter({"aqi"})
    public static void getColorResourceByAqi(TextView textView, float aqi) {
        Context context = textView.getContext();
        int backgroundColor = 0;
        int textColor = 0;
        if (aqi > 0 && aqi <= 50) {
            backgroundColor = context.getResources().getColor(R.color.aqi_good, null);
            textColor = context.getResources().getColor(android.R.color.white, null);
        } else if (aqi > 50 && aqi <= 100) {
            backgroundColor = context.getResources().getColor(R.color.aqi_moderate, null);
            textColor = context.getResources().getColor(R.color.primaryTextColor, null);
        } else if (aqi > 100 && aqi <= 150) {
            backgroundColor = context.getResources().getColor(R.color.aqi_bad, null);
            textColor = context.getResources().getColor(R.color.primaryTextColor, null);
        } else if (aqi > 150 && aqi <= 200) {
            backgroundColor = context.getResources().getColor(R.color.aqi_so_bad, null);
            textColor = context.getResources().getColor(android.R.color.white, null);
        } else if (aqi > 200 && aqi <= 300) {
            backgroundColor = context.getResources().getColor(R.color.aqi_very_bad, null);
            textColor = context.getResources().getColor(android.R.color.white, null);
        } else if (aqi > 300) {
            backgroundColor = context.getResources().getColor(R.color.aqi_extremely_bad, null);
            textColor = context.getResources().getColor(android.R.color.white, null);
        }
        textView.setBackgroundColor(backgroundColor);
        textView.setTextColor(textColor);
    }

    @BindingAdapter({"aqiText"})
    public static void getAqiText(TextView textView, float aqi) {
        Context context = textView.getContext();
        int textResource = 0;
        int textColor = 0;
        if (aqi > 0 && aqi <= 50) {
            textResource = R.string.good;
            textColor = context.getResources().getColor(R.color.aqi_good, null);
        } else if (aqi > 50 && aqi <= 100) {
            textResource = R.string.moderate;
            textColor = context.getResources().getColor(R.color.aqi_moderate, null);
        } else if (aqi > 100 && aqi <= 150) {
            textResource = R.string.bad;
            textColor = context.getResources().getColor(R.color.aqi_bad, null);
        } else if (aqi > 150 && aqi <= 200) {
            textResource = R.string.unhealthy;
            textColor = context.getResources().getColor(R.color.aqi_so_bad, null);
        } else if (aqi > 200 && aqi <= 300) {
            textResource = R.string.very_unhealthy;
            textColor = context.getResources().getColor(R.color.aqi_very_bad, null);
        } else if (aqi > 300) {
            textResource = R.string.hazardous;
            textColor = context.getResources().getColor(R.color.aqi_extremely_bad, null);
        }
        if (textResource != 0) {
            textView.setText(textResource);
        }
        textView.setTextColor(textColor);
    }
}