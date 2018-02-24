package com.example.sfd.mysmarthome;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.IBinder;
import android.view.animation.Animation;
import android.widget.ListView;

import java.util.List;

public class SearchDeviceService extends Service {

    protected WifiAdmin mWifiAdmin;
    private List<ScanResult> mWifiList;
    private ListView mListView;
    protected String ssid;
    private Animation animation;

    public SearchDeviceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mWifiAdmin.startWifiScan(SearchDeviceService.this);
        mWifiList=mWifiAdmin.getWifiList();
        if(mWifiList!=null){
            mListView.setAdapter(new MyAdapter(this,mWifiList));
//            new DeviceSearchActivity.Utility().setListViewHeightBasedOnChildren(mListView);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
