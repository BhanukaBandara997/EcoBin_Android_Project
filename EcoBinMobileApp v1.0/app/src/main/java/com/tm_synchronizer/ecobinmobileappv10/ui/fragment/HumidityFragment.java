package com.tm_synchronizer.ecobinmobileappv10.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import com.tm_synchronizer.ecobinmobileappv10.R;


public class HumidityFragment extends Fragment {

    public static HumidityFragment newInstance(int page, String title) {
        HumidityFragment humidityFragment = new HumidityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("3", page);
        bundle.putString("Humidity", title);
        humidityFragment.setArguments(bundle);
        return humidityFragment;
    }

    private LineChart lineChart_humidity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_humidity_chart, container, false);

        lineChart_humidity = rootView.findViewById(R.id.humidity_line_chart);
        YAxis rightYAxis = lineChart_humidity.getAxisRight();
        rightYAxis.setEnabled(false);
        lineChart_humidity.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart_humidity.setDragEnabled(false);
        lineChart_humidity.setScaleEnabled(false);

        ArrayList<Entry> temperature_chart = new ArrayList<>();

        temperature_chart.add(new Entry(0, 30f));
        temperature_chart.add(new Entry(1, 24f));
        temperature_chart.add(new Entry(2, 32f));
        temperature_chart.add(new Entry(3, 27f));
        temperature_chart.add(new Entry(4, 19f));
        temperature_chart.add(new Entry(0, 24f));

        LineDataSet temperature_set = new LineDataSet(temperature_chart, "Humidity Values");
        temperature_set.setFillAlpha(110);
        temperature_set.setColor(Color.BLUE);
        temperature_set.setLineWidth(3f);

        ArrayList<ILineDataSet> temperature_data_set = new ArrayList<>();
        temperature_data_set.add(temperature_set);

        LineData lineData = new LineData(temperature_data_set);
        lineChart_humidity.setData(lineData);
        return rootView;
    }
}


