package org.dyndns.warenix.bluetoothchecker;

import android.bluetooth.BluetoothAdapter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.dyndns.warenix.bluetoothchecker.model.AppItem;
import org.dyndns.warenix.bluetoothchecker.view.AppItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        List<AppItem> appItemList = listAppWithPermission("android.permission.BLUETOOTH_ADMIN");
        AppItemAdapter adapter = new AppItemAdapter();
        adapter.setList(appItemList);
        recyclerView.setAdapter(adapter);

        Snackbar snackbar = Snackbar.make(recyclerView, R.string.app_intro_text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    public List<AppItem> listAppWithPermission(String targetPermission) {
        List<AppItem> appItemList = new ArrayList<>();

        PackageManager p = this.getPackageManager();
        final List<PackageInfo> appList = p.getInstalledPackages(PackageManager.GET_PERMISSIONS | PackageManager.GET_RECEIVERS |
                PackageManager.GET_SERVICES | PackageManager.GET_PROVIDERS);

        // Loop through all installed packaged to get a list of used permissions and PackageInfos
        for (PackageInfo pi : appList) {
            // Do not add System Packages
            if ((pi.requestedPermissions == null || pi.packageName.equals("android")) ||
                    (pi.applicationInfo != null && (pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0))
                continue;


            for (String permission : pi.requestedPermissions) {
                if (targetPermission != null && !targetPermission.equals(permission)) {
                    continue;
                }
                try {
                    PermissionInfo pinfo = p.getPermissionInfo(permission, PackageManager.GET_META_DATA);
                    CharSequence label = pinfo.loadLabel(p);
                    CharSequence desc = pinfo.loadDescription(p);
                    CharSequence appName = pi.applicationInfo.loadLabel(p);

                    Log.d(TAG, String.format("[%s]-[%s] label[%s] desc[%s]", appName, permission, label, desc));
                    appItemList.add(new AppItem(appName.toString(), pi.packageName, pi.lastUpdateTime));
                } catch (PackageManager.NameNotFoundException e) {
                    Log.i(TAG, "Ignoring unknown permission " + permission);
                    continue;
                }
            }
        }

        return appItemList;
    }

}
