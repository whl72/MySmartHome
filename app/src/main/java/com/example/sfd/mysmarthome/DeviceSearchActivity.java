package com.example.sfd.mysmarthome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceSearchActivity extends Activity
                    implements View.OnClickListener{

    private final String AP_IP_ADDR = "192.168.4.1";
    private final int AP_IP_PORT = 5050;
    public static final int WIFI_CONNECT_SUCCESS = 1;
    public final int RECEIVE_AP_DATA = 10;
    public final int SEND_AP_DATA = 11;
    private static boolean btn_connect_ap_flag = false;

    private Button btnSearch;
    private Button btnAdd;
    protected WifiAdmin mWifiAdmin;
    private List<ScanResult> mWifiList;
    private ListView mListView;
    protected String ssid;
    private Animation animation;
    private IntentFilter intentFilter;
    private WifiReceiver mWifiStateReceiver;
    public static Handler mHandler;
    private static TcpClient tcpClient = null;
    ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_search);

        mWifiAdmin = new WifiAdmin(MyApplication.getContext());
        initViews();
        setBtnRotate();

        intentFilter = new IntentFilter();
        mWifiStateReceiver = new WifiReceiver();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.EXTRA_WIFI_STATE);
        intentFilter.addAction("tcpClientReceiver");

        registerReceiver(mWifiStateReceiver, intentFilter);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case WIFI_CONNECT_SUCCESS:
                        tcpClient = new TcpClient(AP_IP_ADDR.toString(),
                                AP_IP_PORT);
                        exec.execute(tcpClient);

                        Intent intent = new Intent();
                        intent.setClass(DeviceSearchActivity.this,
                                AddDeviceActivity.class);
                        startActivity(intent);
                        break;
                    case SEND_AP_DATA:
//                        textReceive.append("Tx: "+msg.obj.toString()+"\r\n");
                        break;
                    case RECEIVE_AP_DATA:
//                        textReceive.append("Rx: "+msg.obj.toString()+"\r\n");
                        break;
                    default:
                        break;
                }
            }
        };

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                ssid = mWifiList.get(i).SSID;
                mWifiAdmin.addNetwork(mWifiAdmin.createWifiInfo(ssid, "0", 1));
                Toast.makeText(MyApplication.getContext(), "准备连接:"+ssid,
                        Toast.LENGTH_SHORT).show();

                btn_connect_ap_flag = true;
            }
        });

    }

    private void initViews(){
        btnSearch = findViewById(R.id.btn_search_device);
        btnAdd = findViewById(R.id.btn_hand_add);
        mListView = (ListView) findViewById(R.id.list_search_device);
        btnSearch.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search_device:
                if(animation != null){
                    btnSearch.startAnimation(animation);
                }else {
                    btnSearch.setAnimation(animation);
                    btnSearch.startAnimation(animation);
                }
                //这种方式会导致程序崩溃，因为涉及到子线程更新UI了
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        btnSearch.clearAnimation();
//                    }
//                }).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSearch.clearAnimation();
                    }
                },5000);

                mWifiAdmin.startWifiScan(DeviceSearchActivity.this);
                mWifiList=mWifiAdmin.getWifiList();
                if(mWifiList!=null){
                    mListView.setAdapter(new MyAdapter(this,mWifiList));
                    new Utility().setListViewHeightBasedOnChildren(mListView);
                }
                break;
            case R.id.btn_hand_add:
                Intent intent = new Intent();
                intent.setClass(DeviceSearchActivity.this,
                                AddDeviceActivity.class);
                startActivity(intent);

                Toast.makeText(MyApplication.getContext(),
                        "功能暂未开放", Toast.LENGTH_SHORT).show();

//                        tcpClient = new TcpClient(AP_IP_ADDR.toString(),
//                            AP_IP_PORT);
//                        exec.execute(tcpClient);
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
            params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    private void setBtnRotate(){
        animation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        animation.setDuration(2000);
        animation.setRepeatCount(-1);
        animation.setFillAfter(true);
        animation.setStartOffset(10);
    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra
                        (WifiManager.EXTRA_NETWORK_INFO);
                if(info.getState().equals(NetworkInfo.State.DISCONNECTED)){
//                    Toast.makeText(MyApplication.getContext(), "wifi disconnected!",
//                            Toast.LENGTH_SHORT).show();
                }
                else if(info.getState().equals(NetworkInfo.State.CONNECTED)){

                    WifiManager wifiManager = (WifiManager)
                            context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    if(((wifiInfo.getSSID().indexOf("ORE_") != -1)
                            || (wifiInfo.getSSID().indexOf("ore_") != -1))
                            &&(btn_connect_ap_flag == true)){
                        MyMessage.sendMyMessage(WIFI_CONNECT_SUCCESS);
                        btn_connect_ap_flag = false;
                    }

                    //获取当前wifi名称
                    Toast.makeText(MyApplication.getContext(), "连接到网络 "
                                    + wifiInfo.getSSID(),
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

            if(intent.getAction().equals("tcpClientReceiver")){
                String msg = intent.getStringExtra("tcpClientReceiver");
                Message message = Message.obtain();
                message.what = RECEIVE_AP_DATA;
                message.obj = msg;
                mHandler.sendMessage(message);
//                Toast.makeText(MyApplication.getContext(), "收到模块数据",
//                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int getPort(String msg){
        if (msg.equals("")){
            msg = "5050";
        }
        return Integer.parseInt(msg);
    }
}
