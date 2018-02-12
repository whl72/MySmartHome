package com.example.sfd.mysmarthome;


import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity
        implements View.OnClickListener{

    private View btnDevice;
    private View btnShop;
    private View btnNews;
    private View btnPerson;
    private ImageView imgDevice;
    private ImageView imgShop;
    private ImageView imgNews;
    private ImageView imgPerson;
    private TextView txtDevice;
    private TextView txtShop;
    private TextView txtNews;
    private TextView txtPerson;
    private android.app.FragmentManager fragmentManager;
    private android.app.FragmentTransaction fragmentTransaction;
    private DeviceFragment deviceFragment;
    private ShopFragment shopFragment;
    private NewsFragment newsFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initFragment();
    }

    private void initViews(){
        btnDevice = findViewById(R.id.home_layout);
        btnShop = findViewById(R.id.classy_layout);
        btnNews = findViewById(R.id.message_layout);
        btnPerson = findViewById(R.id.mine_layout);
        imgDevice = (ImageView) findViewById(R.id.draw_home);
        imgShop = (ImageView) findViewById(R.id.draw_classy);
        imgNews = (ImageView) findViewById(R.id.draw_message);
        imgPerson = (ImageView) findViewById(R.id.draw_mine);
        txtDevice = (TextView) findViewById(R.id.text_home);
        txtShop = (TextView) findViewById(R.id.text_classy);
        txtNews = (TextView) findViewById(R.id.text_message);
        txtPerson = (TextView) findViewById(R.id.text_mine);
        btnDevice.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnNews.setOnClickListener(this);
        btnPerson.setOnClickListener(this);
    }

    private void initFragment(){
        deviceFragment = new DeviceFragment();
        shopFragment = new ShopFragment();
        newsFragment = new NewsFragment();
        userFragment = new UserFragment();

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, deviceFragment, "device");
        fragmentTransaction.add(R.id.content, shopFragment, "shop");
        fragmentTransaction.add(R.id.content, newsFragment, "news");
        fragmentTransaction.add(R.id.content, userFragment, "user");

        hideAllFragment();
        setTabSelection(0);
        fragmentTransaction.show(deviceFragment).commit();
    }

    @Override
    public void onClick(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        hideAllFragment();

        switch (view.getId()){
            case R.id.home_layout:
                setTabSelection(0);
//                deviceFragment = (DeviceFragment)
//                        fragmentManager.findFragmentByTag("device");
                fragmentTransaction.show(deviceFragment).commit();
                break;
            case R.id.classy_layout:
                setTabSelection(1);
//                shopFragment = (ShopFragment)
//                        fragmentManager.findFragmentByTag("shop");
                fragmentTransaction.show(shopFragment).commit();
                break;
            case R.id.message_layout:
                setTabSelection(2);
//                newsFragment = (NewsFragment)
//                        fragmentManager.findFragmentByTag("news");
                fragmentTransaction.show(newsFragment).commit();
                break;
            case R.id.mine_layout:
                setTabSelection(3);
//                userFragment = (UserFragment)
//                        fragmentManager.findFragmentByTag("user");
                fragmentTransaction.show(userFragment).commit();
                break;
            default:
                break;

        }
    }

    private void setTabSelection(int index){

        clearSelection();
        switch (index){
            case 0:
                imgDevice.setBackgroundResource(0);
                imgDevice.setImageResource(R.drawable.home_current);
                txtDevice.setTextColor(Color.rgb(0, 188, 156));
                break;
            case 1:
                imgShop.setBackgroundResource(0);
                imgShop.setImageResource(R.drawable.classy_current);
                txtShop.setTextColor(Color.rgb(0, 188, 156));
                break;
            case 2:
                imgNews.setBackgroundResource(0);
                imgNews.setImageResource(R.drawable.message_current);
                txtNews.setTextColor(Color.rgb(0, 188, 156));
                break;
            case 3:
                imgPerson.setBackgroundResource(0);
                imgPerson.setImageResource(R.drawable.mine_current);
                txtPerson.setTextColor(Color.rgb(0, 188, 156));
                break;
            default:
                break;
        }
    }

    private void clearSelection(){
        imgDevice.setImageResource(R.drawable.home);
        txtDevice.setTextColor(Color.parseColor("#7c7c7c"));
        imgShop.setImageResource(R.drawable.classy);
        txtShop.setTextColor(Color.parseColor("#7c7c7c"));
        imgNews.setImageResource(R.drawable.message);
        txtNews.setTextColor(Color.parseColor("#7c7c7c"));
        imgPerson.setImageResource(R.drawable.mine);
        txtPerson.setTextColor(Color.parseColor("#7c7c7c"));
    }

    private void hideAllFragment(){
        fragmentTransaction.hide(deviceFragment);
        fragmentTransaction.hide(shopFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.hide(userFragment);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
//        if (deviceFragment != null) {
//            transaction.hide(deviceFragment);
//        }
//        if (contactsFragment != null) {
//            transaction.hide(contactsFragment);
//        }
//        if (newsFragment != null) {
//            transaction.hide(newsFragment);
//        }
//        if (settingFragment != null) {
//            transaction.hide(settingFragment);
//        }
    }
}
