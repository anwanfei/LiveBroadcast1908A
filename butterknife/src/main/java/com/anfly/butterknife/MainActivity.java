package com.anfly.butterknife;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.tv1)
    TextView tv1;

    @BindString(R.string.jiyun)
    String jiyun;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn1, R.id.tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(MainActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv1:
                Toast.makeText(MainActivity.this, "点击了文本", Toast.LENGTH_SHORT).show();
                tv1.setText(jiyun);
                tv1.setTextColor(colorPrimary);
                break;
        }
    }
}
