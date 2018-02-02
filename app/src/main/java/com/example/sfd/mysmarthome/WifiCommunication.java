package com.example.sfd.mysmarthome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class WifiCommunication extends Activity
                                implements View.OnClickListener{

    private Button btnScan;
    private Button btnCheck;
    private Button btnOpen;
    private Button btnClose;
    private Button btnSend;
    private EditText edtReceive;
    private ListView mListView;
    protected WifiAdmin mWifiAdmin;
    private List<ScanResult> mWifiList;
    public int level;
    protected String ssid;

    private IntentFilter intentFilter;
    private WifiReceiver mWifiStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_communication);

//        //必须调用该方法，否则滚动条无法拖动
//        edtReceive.setMovementMethod(new ScrollingMovementMethod());

        mWifiAdmin = new WifiAdmin(MyApplication.getContext());
        initViews();

////        intentFilter = new IntentFilter();
////        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mWifiStateReceiver = new WifiReceiver();
        registerReceiver(mWifiStateReceiver, intentFilter);

//        IntentFilter intentFilter = new IntentFilter(WifiManager.
//                NETWORK_STATE_CHANGED_ACTION);
////        IntentFilter intentFilter = new
////                IntentFilter("android.net.wifi.WIFI_STATE_CHANGE");
//        registerReceiver(mReceiver, intentFilter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder
                        (MyApplication.getContext());
                ssid = mWifiList.get(i).SSID;
                mWifiAdmin.addNetwork(mWifiAdmin.createWifiInfo(ssid, "0", 1));

//                alert.setTitle(ssid);
//                alert.setMessage("输入密码");
//                final EditText et_password=new EditText(MyApplication.getContext());
//                final SharedPreferences preferences=getSharedPreferences("wifi_password",Context.MODE_PRIVATE);
//                et_password.setText(preferences.getString(ssid, ""));
//                alert.setView(et_password);
//                //alert.setView(view1);
//                alert.setPositiveButton("连接", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String pw = et_password.getText().toString();
//                        if(null == pw  || pw.length() < 8){
//                            Toast.makeText(MyApplication.getContext(), "密码至少8位", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        SharedPreferences.Editor editor=preferences.edit();
//                        editor.putString(ssid, pw);   //保存密码
//                        editor.commit();
//                        mWifiAdmin.addNetwork(mWifiAdmin.CreateWifiInfo(ssid, et_password.getText().toString(), 3));
//                    }
//                });
//                alert.setNegativeButton("取消", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //
//                        //mWifiAdmin.removeWifi(mWifiAdmin.getNetworkId());
//                    }
//                });
//                alert.create();
//                alert.show();
            }
        });
    }

    private void initViews(){
        btnScan = (Button) findViewById(R.id.btn_scan_wifi);
        btnCheck = (Button) findViewById(R.id.btn_check_wifi);
        btnOpen = (Button) findViewById(R.id.btn_open_wifi);
        btnClose = (Button) findViewById(R.id.btn_close_wifi);
        mListView = (ListView) findViewById(R.id.list_view_device);
        btnScan.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_scan_wifi:
                mWifiAdmin.startWifiScan(MyApplication.getContext());
                mWifiList=mWifiAdmin.getWifiList();
                if(mWifiList!=null){
                    mListView.setAdapter(new MyAdapter(this,mWifiList));
                    new Utility().setListViewHeightBasedOnChildren(mListView);
                }
                break;
            case R.id.btn_check_wifi:
                mWifiAdmin.checkState(MyApplication.getContext());
                break;
            case R.id.btn_open_wifi:
                mWifiAdmin.openWifi(MyApplication.getContext());
                break;
            case R.id.btn_close_wifi:
                mWifiAdmin.closeWifi(MyApplication.getContext());
                break;
            default:
                break;
        }
    }

    /*设置listview的高度*/
    public class Utility {
        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager manager = (ConnectivityManager)
//                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo wifiInfo = manager.getNetworkInfo
//                    (ConnectivityManager.TYPE_WIFI);
//            if(wifiInfo.isConnected()){
//                WifiManager wifiManager = (WifiManager)
//                        context.getSystemService(context.WIFI_SERVICE);
//                String wifiSSID = wifiManager.getConnectionInfo().getSSID();
//                Toast.makeText(context, wifiSSID+"连接成功！",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

    class WifiReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifiInfo = manager.getNetworkInfo
                        (ConnectivityManager.TYPE_WIFI);
                if (wifiInfo.isConnected()) {
                    WifiManager wifiManager = (WifiManager)
                            context.getSystemService(context.WIFI_SERVICE);
                    String wifiSSID = wifiManager.getConnectionInfo().getSSID();
                    Toast.makeText(context, wifiSSID + "连接成功！",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "啥都没",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
