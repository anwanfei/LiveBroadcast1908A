package com.anfly.loninmvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anfly.loninmvp.bean.LoginBean;
import com.anfly.loninmvp.presenter.ImpLoginPresenter;
import com.anfly.loninmvp.view.LoginView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private Button btn_login;
    private EditText et_pwd;
    private EditText et_name;
    private ImpLoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ImpLoginPresenter(this);
        initView();
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_name = (EditText) findViewById(R.id.et_name);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        String pwd = et_pwd.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        presenter.login(name, pwd);
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        int errorCode = loginBean.getErrorCode();
        //使用sp保存账号密码
        if (errorCode == 0) {
            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
    }
}
