package com.anfly.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 权限修饰符必须是public
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void getData(MessageEvent messageEvent) {
        Toast.makeText(MainActivity.this, messageEvent.toString(), Toast.LENGTH_SHORT).show();
        Log.e("TAG", "MainActivity getData()的日志：" + messageEvent.toString());
    }

    @OnClick({R.id.bnt_go_subs, R.id.bnt_go_subs_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnt_go_subs:
                go();
                break;
            case R.id.bnt_go_subs_publish:
                EventBus.getDefault().postSticky("来自MainActivity的粘性事件");
                go();
                break;
        }
    }

    private void go() {
        Intent intent = new Intent(this, SubscribActivity.class);
        startActivity(intent);
    }
}
