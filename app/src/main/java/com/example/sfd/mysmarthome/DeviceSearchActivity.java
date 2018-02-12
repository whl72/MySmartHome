package com.example.sfd.mysmarthome;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Handler;
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

public class DeviceSearchActivity extends Activity
                    implements View.OnClickListener{

    private Button btnSearch;
    private Button btnAdd;
    protected WifiAdmin mWifiAdmin;
    private List<ScanResult> mWifiList;
    private ListView mListView;
    protected String ssid;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_search);

        mWifiAdmin = new WifiAdmin(DeviceSearchActivity.this);
        initViews();
        setBtnRotate();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                ssid = mWifiList.get(i).SSID;
                mWifiAdmin.addNetwork(mWifiAdmin.createWifiInfo(ssid, "0", 1));
                Toast.makeText(MyApplication.getContext(), "准备连接:"+ssid,
                        Toast.LENGTH_SHORT).show();
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
}
