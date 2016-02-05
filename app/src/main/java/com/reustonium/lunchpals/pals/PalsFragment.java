package com.reustonium.lunchpals.pals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reustonium.lunchpals.Injection;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.data.Pal;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by andrew on 2/4/16.
 */
public class PalsFragment extends Fragment implements PalsContract.View {

    private static final int REQUEST_ADD_PAL = 1;

    private PalsContract.UserActionsListener mActionsListener;

    private PalsAdapter mListAdapter;

    public PalsFragment() {
        // Requires empty public constructor
    }

    public static PalsFragment newInstance() {
        return new PalsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new PalsAdapter(new ArrayList<Pal>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadPals(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        mActionsListener = new PalsPresenter(Injection.providePalsRepository(), this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a pal was successfully added, show snackbar
        if (REQUEST_ADD_PAL == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), getString(R.string.successfully_saved_pal_message),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pals, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.pals_list);
        recyclerView.setAdapter(mListAdapter);

        int numColumns = getContext().getResources().getInteger(R.integer.num_pals_columns);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_pal);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.addNewPal();
            }
        });

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionsListener.loadPals(true);
            }
        });
        return root;
    }

    /**
     * Listener for clicks on pals in the RecyclerView.
     */
    PalItemListener mItemListener = new PalItemListener() {
        @Override
        public void onPalClick(Pal clickedPal) {
            mActionsListener.openPalDetails(clickedPal);
        }
    };

    @Override
    public void setProgressIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showPals(List<Pal> pals) {
        mListAdapter.replaceData(pals);
    }

    @Override
    public void showAddPal() {
//        Intent intent = new Intent(getContext(), AddPalActivity.class);
//        startActivityForResult(intent, REQUEST_ADD_PAL);

        Snackbar.make(getView(), "Launch Add Pal Activity",
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showPalDetailUi(String palId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
//        Intent intent = new Intent(getContext(), PalDetailActivity.class);
//        intent.putExtra(PalDetailActivity.EXTRA_NOTE_ID, palId);
//        startActivity(intent);
        Snackbar.make(getView(), "Launch Pal Detail Activity",
                Snackbar.LENGTH_SHORT).show();
    }


    private static class PalsAdapter extends RecyclerView.Adapter<PalsAdapter.ViewHolder> {

        private List<Pal> mPals;
        private PalItemListener mItemListener;

        public PalsAdapter(List<Pal> pals, PalItemListener itemListener) {
            setList(pals);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View palView = inflater.inflate(R.layout.item_pal, parent, false);

            return new ViewHolder(palView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Pal pal = mPals.get(position);

            viewHolder.title.setText(pal.getTitle());
            viewHolder.description.setText(pal.getDescription());
        }

        public void replaceData(List<Pal> pals) {
            setList(pals);
            notifyDataSetChanged();
        }

        private void setList(List<Pal> pals) {
            mPals = checkNotNull(pals);
        }

        @Override
        public int getItemCount() {
            return mPals.size();
        }

        public Pal getItem(int position) {
            return mPals.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView title;

            public TextView description;
            private PalItemListener mItemListener;

            public ViewHolder(View itemView, PalItemListener listener) {
                super(itemView);
                mItemListener = listener;
                title = (TextView) itemView.findViewById(R.id.pal_detail_title);
                description = (TextView) itemView.findViewById(R.id.pal_detail_description);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Pal pal = getItem(position);
                mItemListener.onPalClick(pal);

            }
        }
    }

    public interface PalItemListener {

        void onPalClick(Pal clickedPal);
    }

}
