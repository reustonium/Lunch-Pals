package reustonium.com.lunchpals.ui.main;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import reustonium.com.lunchpals.R;
import reustonium.com.lunchpals.ui.base.BaseActivity;


public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject PalsAdapter mPalsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorText));
        setSupportActionBar(toolbar);

        //Add RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pal_list);
        mRecyclerView.setAdapter(mPalsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Dummy some data
        List<String> mPals = new ArrayList<>();
        mPals.add("Andy");
        mPals.add("Jimmy Jamm");
        showPals(mPals);
    }

    @Override
    public void showPals(List<String> pals) {
        mPalsAdapter.setPals(pals);
        mPalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPalsEmpty() {
        mPalsAdapter.setPals(Collections.<String>emptyList());
        mPalsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }
}
