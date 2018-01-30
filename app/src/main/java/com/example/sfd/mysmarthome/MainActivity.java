package com.example.sfd.mysmarthome;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_layout:
                setTabSelection(0);
                break;
            case R.id.classy_layout:
                setTabSelection(1);
                break;
            case R.id.message_layout:
                setTabSelection(2);
                break;
            case R.id.mine_layout:
                setTabSelection(3);
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
}
