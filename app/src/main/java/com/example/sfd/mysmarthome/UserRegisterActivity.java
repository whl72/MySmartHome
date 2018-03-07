package com.example.sfd.mysmarthome;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserRegisterActivity extends Activity implements View.OnClickListener{

    private Button btnPhoneRegister;
    private Button btnMailRegister;
    private Button btnBack;
    private android.app.FragmentManager fragmentManager;
    private android.app.FragmentTransaction fragmentTransaction;
    private PhoneRegisterFragment phoneRegisterFragment;
    private MailRegisterFragment mailRegisterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        initViews();
        initFragments();
    }

    private void initViews(){
        btnPhoneRegister = findViewById(R.id.btn_register_phone);
        btnMailRegister = findViewById(R.id.btn_register_mail);
        btnBack = findViewById(R.id.btn_user_register_back);

        btnPhoneRegister.setOnClickListener(this);
        btnMailRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void initFragments(){
        phoneRegisterFragment = new PhoneRegisterFragment();
        mailRegisterFragment = new MailRegisterFragment();

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.phone_register_fragment_id,
                phoneRegisterFragment, "phoneRegister");
        fragmentTransaction.add(R.id.phone_register_fragment_id,
                mailRegisterFragment, "mailRegister");

        hidAllFragments();
        setTabSelection(0);
        fragmentTransaction.show(phoneRegisterFragment).commit();
    }

    @Override
    public void onClick(View view) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        hidAllFragments();
        switch (view.getId()){
            case R.id.btn_register_phone:
                setTabSelection(0);
                fragmentTransaction.show(phoneRegisterFragment).commit();
                break;
            case R.id.btn_register_mail:
                setTabSelection(1);
                fragmentTransaction.show(mailRegisterFragment).commit();
                break;
            case R.id.btn_user_register_back:
                Intent intent = new Intent();
                intent.setClass(this, UserLoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index){
        switch (index){
            case 0:
                btnPhoneRegister.setBackgroundColor(Color.parseColor("#6699cc"));
                btnMailRegister.setBackgroundColor(Color.parseColor("#e0e0e0"));
                break;
            case 1:
                btnMailRegister.setBackgroundColor(Color.parseColor("#6699cc"));
                btnPhoneRegister.setBackgroundColor(Color.parseColor("#e0e0e0"));
                break;
            default:
                break;
        }
    }

    private void hidAllFragments(){
        fragmentTransaction.hide(phoneRegisterFragment);
        fragmentTransaction.hide(mailRegisterFragment);
    }
}
