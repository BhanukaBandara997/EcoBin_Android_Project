package com.tm_synchronizer.ecobinmobileappv10.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tm_synchronizer.ecobinmobileappv10.R;
import com.tm_synchronizer.ecobinmobileappv10.model.EcoBinData;
import com.tm_synchronizer.ecobinmobileappv10.network.ServerConnection;
import com.tm_synchronizer.ecobinmobileappv10.ui.activity.LoginActivity;
import com.txusballesteros.widgets.FitChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DashboardFragment extends Fragment {

    String temperatureValue, humidityValue, methaneValue, binStatus, waterLevel, stirringStatus, daysLeft;

    static final String BASE_URL = "https://ecobintest.herokuapp.com";
    static JSONObject dataobject;
    TextView dayNumberTxt, temperatureValueTxt, humidityValueTxt, methaneValueTxt, waterLevelValueTxt, stirringStatusTxt, binStatusTxt;

    List<EcoBinData> ecoBinDataList = new ArrayList<>();
    EcoBinData binData;
    FitChart fitChart;
    View tempRootView;
    public static String userName, address, emailAddress, mobileNo;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tempRootView =rootView;
        initializeComponent(rootView);


//        requestData();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                requestData();
            }
        }, 3600000);

        return rootView;
    }

    private void initializeComponent(View rootView) {
        dayNumberTxt = rootView.findViewById(R.id.day_number);
        temperatureValueTxt = rootView.findViewById(R.id.temperature_value);
        humidityValueTxt = rootView.findViewById(R.id.humidity_value);
        methaneValueTxt = rootView.findViewById(R.id.methane_value);
        waterLevelValueTxt = rootView.findViewById(R.id.water_level_value);
        stirringStatusTxt = rootView.findViewById(R.id.stirring_value);
        binStatusTxt = rootView.findViewById(R.id.bin_status);
        fitChart = rootView.findViewById(R.id.fit_chart);
    }

    @Override
    public void onResume(){
        final ArrayList<Integer> datalist = new ArrayList<>();
        /*edited by bVd*/
        String url = BASE_URL + "/api/transact/echoshowinfo";
        final String useremail = LoginActivity.logemail;
        JSONObject object = new JSONObject();
        Date date = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("kk");
        String h = sm.format(date);
        final int day = date.getDate();
        final int hour = Integer.parseInt(h);
        // Toast.makeText(getActivity(), day + " | " + hour, Toast.LENGTH_SHORT).show();
        try {
            object.put("email", useremail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest infoobjectrequest = new JsonObjectRequest(Request.Method.POST, "https://ecobintest.herokuapp.com/api/transact/echoshowinfo", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray sensordata = response.getJSONArray("Data");
                    userName = response.getString("Name");
                    emailAddress = response.getString("Email");
                    address = response.getString("Address");
                    mobileNo = response.getString("Mobile");
                    int count=sensordata.length();
                    JSONObject lastdata= (JSONObject) sensordata.get(count-1);
                    String temperature = lastdata.get("Temprature").toString();
                    if (lastdata != null){
                        temperatureValueTxt.setText(temperature);
                        methaneValueTxt.setText(lastdata.get("MethaneStatus").toString());
                        humidityValueTxt.setText(lastdata.get("Humidity").toString());
                        dayNumberTxt.setText(lastdata.get("Estimate").toString());
                        if(lastdata.get("WaterLevel").toString().equals("1")){
                            waterLevelValueTxt.setText("Full");
                        }else{
                            waterLevelValueTxt.setText("Low");
                        }
                    }
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
        ServerConnection.getInstance(getActivity()).addToRequestque(infoobjectrequest);
        super.onResume();
    }


    public void requestData() {
        final ArrayList<Integer> datalist = new ArrayList<>();
        /*edited by bVd*/
        String url = BASE_URL + "/api/transact/echoshowinfo";
        String useremail = LoginActivity.logemail;
        JSONObject object = new JSONObject();
        Date date = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("kk");
        String h = sm.format(date);
        final int day = date.getDate();
        final int hour = Integer.parseInt(h);
       // Toast.makeText(getActivity(), day + " | " + hour, Toast.LENGTH_SHORT).show();
        try {
            object.put("email", useremail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest infoobjectrequest = new JsonObjectRequest(Request.Method.POST, "https://ecobintest.herokuapp.com/api/transact/echoshowinfo", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray sensordata = response.getJSONArray("Data");
                    int count=sensordata.length();
                    JSONObject lastdata= (JSONObject) sensordata.get(count-1);
                    String temperature = lastdata.get("Temprature").toString();
                   // Toast.makeText(getActivity(), lastdata.get("Temprature").toString(), Toast.LENGTH_SHORT).show();
                    if (lastdata != null){
                        temperatureValueTxt.setText(temperature);
                        methaneValueTxt.setText(lastdata.get("MethaneStatus").toString());
                        humidityValueTxt.setText(lastdata.get("Humidity").toString());
                        waterLevelValueTxt.setText(lastdata.get("WaterLevel").toString());
                        dayNumberTxt.setText(lastdata.get("Estimate").toString());
                    }
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
        ServerConnection.getInstance(getActivity()).addToRequestque(infoobjectrequest);

    }



}

