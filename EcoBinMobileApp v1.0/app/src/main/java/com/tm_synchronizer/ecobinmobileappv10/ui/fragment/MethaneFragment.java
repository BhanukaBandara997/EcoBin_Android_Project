package com.tm_synchronizer.ecobinmobileappv10.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.tm_synchronizer.ecobinmobileappv10.R;
import com.tm_synchronizer.ecobinmobileappv10.network.ServerConnection;
import com.tm_synchronizer.ecobinmobileappv10.ui.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MethaneFragment extends Fragment {
    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart lineChart_methane;
    public String TAG = "MethaneFragment";

    public static MethaneFragment newInstance(int page, String title) {
        MethaneFragment methaneFragment = new MethaneFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("2", page);
        bundle.putString("Methane", title);
        methaneFragment.setArguments(bundle);
        return methaneFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_methane_chart, container, false);
//
//        x = new ArrayList<>();
//        y = new ArrayList<>();
//
//        lineChart_methane = rootView.findViewById(R.id.methane_line_chart);
//        lineChart_methane.setDrawGridBackground(false);
//        lineChart_methane.setTouchEnabled(true);
//        lineChart_methane.setDragEnabled(true);
//        lineChart_methane.setScaleEnabled(true);
//        lineChart_methane.setPinchZoom(true);
//        XAxis xl = lineChart_methane.getXAxis();
//        xl.setAvoidFirstLastClipping(true);
//        YAxis leftAxis = lineChart_methane.getAxisLeft();
//        leftAxis.setInverted(true);
//        YAxis rightAxis = lineChart_methane.getAxisRight();
//        rightAxis.setEnabled(false);
//        Legend l = lineChart_methane.getLegend();
//        l.setForm(Legend.LegendForm.LINE);
//
//        XAxis xAxis = lineChart_methane.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        lineChart_methane.setDragEnabled(false);
//        lineChart_methane.setScaleEnabled(false);
//        lineChart_methane.getAxisRight().setEnabled(false);
//
//        dataRequest();
//
//        return rootView;

        View rootView = inflater.inflate(R.layout.fragment_humidity_chart, container, false);

        lineChart_methane = rootView.findViewById(R.id.humidity_line_chart);
        YAxis rightYAxis = lineChart_methane.getAxisRight();
        rightYAxis.setEnabled(false);
        lineChart_methane.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart_methane.setDragEnabled(false);
        lineChart_methane.setScaleEnabled(false);

        ArrayList<Entry> temperature_chart = new ArrayList<>();

        temperature_chart.add(new Entry(0, 30f));
        temperature_chart.add(new Entry(1, 24f));
        temperature_chart.add(new Entry(2, 32f));
        temperature_chart.add(new Entry(3, 27f));
        temperature_chart.add(new Entry(4, 19f));
        temperature_chart.add(new Entry(0, 24f));

        LineDataSet temperature_set = new LineDataSet(temperature_chart, "Methane Values");
        temperature_set.setFillAlpha(110);
        temperature_set.setColor(Color.BLUE);
        temperature_set.setLineWidth(3f);

        ArrayList<ILineDataSet> temperature_data_set = new ArrayList<>();
        temperature_data_set.add(temperature_set);

        LineData lineData = new LineData(temperature_data_set);
        lineChart_methane.setData(lineData);
        return rootView;
    }

    public void dataRequest() {
        String userEmail = LoginActivity.logemail;
        JSONObject object = new JSONObject();
        Date date = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("kk");
        String h = sm.format(date);
        final int day = date.getDate();
        final int hour = Integer.parseInt(h);
        try {
            object.put("email", userEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest infoObjectResponse = new JsonObjectRequest(Request.Method.POST, "https://ecobintest.herokuapp.com/api/transact/echoshowinfo", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONArray sensordata = response.getJSONArray("Data");
                    for (int i = 0; i < sensordata.length(); i++) {
                        JSONObject info= (JSONObject) sensordata.get(i);
                        String time = info.getString("Time");
                        String strMethaneValue = info.getString("MethaneStatus");
                        int methaneValue = Integer.valueOf(strMethaneValue);
                        x.add(new Entry(methaneValue, i));
                        y.add(time);
                    }
                    LineDataSet set1 = new LineDataSet(x, "NAV Data Value");
                    set1.setLineWidth(1.5f);
                    set1.setCircleRadius(4f);
                    LineData data = new LineData((ILineDataSet) y, set1);
                    lineChart_methane.setData(data);
                    lineChart_methane.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ServerConnection.getInstance(getActivity()).addToRequestque(infoObjectResponse);

    }

}
