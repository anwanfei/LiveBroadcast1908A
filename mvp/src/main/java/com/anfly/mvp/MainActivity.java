package com.anfly.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anfly.mvp.presenter.ImpClickPresenter;
import com.anfly.mvp.view.ClickView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClickView {

    private Button btn_gettext;
    private TextView tv_gettext;
    private ImpClickPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ImpClickPresenter(this);
        initView();
    }

    private void initView() {
        btn_gettext = (Button) findViewById(R.id.btn_gettext);
        tv_gettext = (TextView) findViewById(R.id.tv_gettext);

        btn_gettext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gettext:
                presenter.click();
                break;
        }
    }

    @Override
    public void onSuccess(String msg) {
        tv_gettext.setText(msg);
    }

    @Override
    public void onFail(String error) {
        tv_gettext.setText(error);
    }
}
