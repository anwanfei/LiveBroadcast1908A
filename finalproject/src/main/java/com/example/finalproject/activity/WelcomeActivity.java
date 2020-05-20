package com.example.finalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;
    @BindView(R.id.btn_jump)
    Button btnJump;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    private long count = 5;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //四种补间动画star
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2, 0, 2);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 100, 0, 100);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(5000);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);

        ivWelcome.startAnimation(animationSet);

        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        long newCount = count - aLong;
                        if (newCount >= 0) {
                            tvCountDown.setText("倒计时：" + newCount);
                        } else {
                            disposable.dispose();
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.btn_jump)
    public void onViewClicked() {
        disposable.dispose();
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
