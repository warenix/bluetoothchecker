package org.dyndns.warenix.bluetoothchecker.model;

import android.text.format.DateUtils;

/**
 * Created by warenix on 3/5/16.
 */
public class AppItem {
    private final String mAppName;
    private final String mPacakageName;
    private final long mLastUpdateTime;

    public AppItem(String appName, String packageName, long lastUpdateTime) {
        mAppName = appName;
        mPacakageName = packageName;
        mLastUpdateTime = lastUpdateTime;
    }

    public String getAppName() {
        return mAppName;
    }

    public String getPackageName() {
        return mPacakageName;
    }

    public CharSequence getRelativetLastUpdateTime() {
        return DateUtils.getRelativeTimeSpanString(mLastUpdateTime);
    }
}