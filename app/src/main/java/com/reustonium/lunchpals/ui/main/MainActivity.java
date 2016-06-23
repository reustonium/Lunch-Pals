package com.reustonium.lunchpals.ui.main;

import android.os.Bundle;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

=======
>>>>>>> d5a53c60ab47c63e2116e2a684b08cfef4a1189f
        getActivityComponent().inject(this);
    }
}
