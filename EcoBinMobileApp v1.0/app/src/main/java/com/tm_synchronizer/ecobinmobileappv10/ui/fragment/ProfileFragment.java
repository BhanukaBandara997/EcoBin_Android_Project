package com.tm_synchronizer.ecobinmobileappv10.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tm_synchronizer.ecobinmobileappv10.R;
import com.tm_synchronizer.ecobinmobileappv10.ui.activity.EditUserProfileActivity;


public class ProfileFragment extends Fragment {

    ImageView profileImage;
    TextView nameLabel, addressLabel, emailLabel, mobileNumberLabel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = rootView.findViewById(R.id.img_profile);
        nameLabel = rootView.findViewById(R.id.name_lbl);
        addressLabel = rootView.findViewById(R.id.address_lbl);
        emailLabel = rootView.findViewById(R.id.mobileNo_lbl);
        mobileNumberLabel = rootView.findViewById(R.id.mobile_no_lbl);

        nameLabel.setText(DashboardFragment.userName);
        addressLabel.setText(DashboardFragment.address);
        emailLabel.setText(DashboardFragment.emailAddress);
        mobileNumberLabel.setText(DashboardFragment.mobileNo);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserProfileActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}






