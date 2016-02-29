package com.reustonium.lunchpals.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.reustonium.lunchpals.R;

public class PalsAdapter extends RecyclerView.Adapter<PalsAdapter.PalsViewHolder> {

    private List<String> mPals;

    @Inject
    public PalsAdapter() {
        mPals = new ArrayList<>();
    }

    public void setPals(List<String> pals) {
        mPals = pals;
    }

    @Override
    public PalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pals, parent, false);
        return new PalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PalsViewHolder holder, int position) {
        String s = mPals.get(position);
        holder.palName.setText(s);
    }

    @Override
    public int getItemCount() {
        return mPals.size();
    }

    class PalsViewHolder extends RecyclerView.ViewHolder {

        TextView palName;

        public PalsViewHolder(View itemView) {
            super(itemView);
            palName = (TextView)  itemView.findViewById(R.id.pal_name);
        }
    }
}
