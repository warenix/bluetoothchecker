package org.dyndns.warenix.bluetoothchecker.view;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.dyndns.warenix.bluetoothchecker.model.AppItem;

import java.util.List;

/**
 * Created by warenix on 3/5/16.
 */
public class AppItemAdapter extends RecyclerView.Adapter<AppItemViewHolder> {
    List<AppItem> mDataList;

    @Override
    public AppItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AppItemViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(AppItemViewHolder holder, int position) {
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setList(List<AppItem> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }
}
