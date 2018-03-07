package com.example.sfd.mysmarthome;

import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by SFD on 2018/1/31.
 */

public class UserFragment extends Fragment implements View.OnClickListener{

    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //这个构造方法不能删除，否则程序会崩溃
        super.onActivityCreated(savedInstanceState);

        initViews();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_user_login:
                if (btnLogin.getText().equals("点击登录")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserLoginActivity.class);
                    startActivity(intent);

                    btnLogin.setText("用户已登录");
                }else {
                    Toast.makeText(MyApplication.getContext(),
                            "显示用户信息", Toast.LENGTH_SHORT).show();

                    btnLogin.setText("点击登录");
                }
                break;
            default:
                break;
        }
    }

    private void initViews(){
        btnLogin = (Button) getActivity().findViewById(R.id.btn_user_login);

        btnLogin.setOnClickListener(this);
    }
}
