package com.reustonium.lunchpals.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.reustonium.lunchpals.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PalsAdapter extends RecyclerView.Adapter<PalsAdapter.PalsViewHolder> {

    private List<String> mPals;
    private Callback mCallback;

    @Inject
    public PalsAdapter() {
        mPals = new ArrayList<>();
    }

    public void setPals(List<String> pals) {
        mPals = pals;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
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
        holder.setPalName(s);
        holder.textPalName.setText(s);
    }

    @Override
    public int getItemCount() {
        return mPals.size();
    }

    @Nullable
    private Integer getPalPosition(String palName) {
        for (int position = 0; position < getItemCount(); position++) {
            String s = mPals.get(position);
            if (s.equals(palName)) {
                return position;
            }
        }
        return null;
    }

    class PalsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_pal_name)
        public TextView textPalName;
        @Bind(R.id.layout_pal)
        public View layoutPal;
        @Bind(R.id.image_pal_profile)
        public ImageView imagePalProfile;
        public String palName;

        public PalsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.layout_pal)
        void onItemClicked() {
            if (mCallback != null) mCallback.onPalClicked(palName, imagePalProfile, textPalName);
        }

        public void setPalName(String palName) {
            this.palName = palName;
        }
    }

    interface Callback {
        void onPalClicked(String palName, View imagePalProfile, View textPalName);
    }
}
