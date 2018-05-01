package com.ssannttoss.android_challenge.mvc.view.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ssannttoss.android_challenge.R;

import java.util.List;

/**
 * Created by ssannttoss on 1/20/2018.
 */

public class SearchResultItemAdapter extends RecyclerView.Adapter<SearchResultItemAdapter.ViewHolder> {

    private final Context context;
    private final List<AddressViewModel> items;
    private final OnActionListener onActionListener;
    public SearchResultItemAdapter(Context context, List<AddressViewModel> items, OnActionListener onActionListener) {

        this.context = context;
        this.items = items;
        this.onActionListener = onActionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AddressViewModel item = items.get(position);
        final String content = item.getFeatureName();

        holder.txtFeatureName.setText(content);
        holder.txtFeatureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.onSelectedItem(item);
            }
        });
        holder.btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionListener.onViewMapClicked(item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnActionListener<AddressViewModel> {
        void onSelectedItem(com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel item);

        void onViewMapClicked(com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView txtFeatureName;
        ImageButton btnShowMap;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            txtFeatureName = itemView.findViewById(R.id.txt_address);
            btnShowMap = itemView.findViewById(R.id.btn_show_map);
        }
    }
}
