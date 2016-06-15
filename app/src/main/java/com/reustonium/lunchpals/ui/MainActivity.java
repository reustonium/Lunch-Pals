package com.reustonium.lunchpals.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.reustonium.lunchpals.R;

public class MainActivity extends AppCompatActivity {

    private ImageView mProfileImage;
    private TextView mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfileImage = (ImageView) findViewById(R.id.main_iv_profile);
        mUserName = (TextView)findViewById(R.id.main_tv_username);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mProfileImage.setImageURI(user.getPhotoUrl());
            mUserName.setText(user.getDisplayName());
        }
    }
}
