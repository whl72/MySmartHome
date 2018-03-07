package com.example.sfd.mysmarthome;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserLoginActivity extends Activity implements View.OnClickListener{

    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        initViews();
    }

    private void initViews(){
        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                Intent intent = new Intent();
                intent.setClass(this, UserRegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
