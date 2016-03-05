package org.dyndns.warenix.bluetoothchecker.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.dyndns.warenix.bluetoothchecker.R;
import org.dyndns.warenix.bluetoothchecker.model.AppItem;

/**
 * Created by warenix on 3/5/16.
 */
public class AppItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView mPackageNameView;
    private TextView mAppNameView;
    private TextView mRelativeLastUpdateTime;
    private AppItem mAppItem;

    public AppItemViewHolder(View itemView) {
        super(itemView);
        mAppNameView = (TextView) itemView.findViewById(R.id.app_name);
        mPackageNameView = (TextView) itemView.findViewById(R.id.package_name);
        mRelativeLastUpdateTime = (TextView) itemView.findViewById(R.id.relative_last_update_time);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAppInfoActivity(v.getContext(), mAppItem.getPackageName());
            }
        });
    }

    public static AppItemViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_item, parent, false);
        AppItemViewHolder viewHolder = new AppItemViewHolder(view);
        return viewHolder;
    }

    public void bindData(AppItem appItem) {
        mAppItem = appItem;
        mAppNameView.setText(appItem.getAppName());
        mPackageNameView.setText(appItem.getPackageName());
        mRelativeLastUpdateTime.setText(appItem.getRelativetLastUpdateTime());
    }

    private void launchAppInfoActivity(Context context, String packageName) {
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //e.printStackTrace();
            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            context.startActivity(intent);
        }
    }
}
