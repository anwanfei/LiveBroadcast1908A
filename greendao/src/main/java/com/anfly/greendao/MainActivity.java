package com.anfly.greendao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_insert;
    private Button btn_delete;
    private Button btn_query;
    private Button btn_updata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_updata = (Button) findViewById(R.id.btn_updata);

        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_updata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_query:
                query();
                break;
            case R.id.btn_updata:
                updata();
                break;
        }
    }

    private void updata() {
        StudentBean studentBean = new StudentBean();
        studentBean.setId((long) 5);
        studentBean.setName("anfly1111111111" + 5);
        studentBean.setAge(String.valueOf(5));
        studentBean.setSex("女");
        DbHelper.getInstance().updata(studentBean);
    }

    private void query() {
        List<StudentBean> list = DbHelper.getInstance().queryAll();
        for (int i = 0; i < list.size(); i++) {
            Log.e("TAG", "查询结果：" + list.get(i).toString());
        }
    }

    private void delete() {
        StudentBean studentBean = new StudentBean();
        studentBean.setId((long) 5);
        studentBean.setName("anfly" + 5);
        studentBean.setAge(String.valueOf(5));
        DbHelper.getInstance().delete(studentBean);
    }

    private void insert() {
        for (int i = 0; i < 10; i++) {
            StudentBean studentBean = new StudentBean();
            studentBean.setId((long) i);
            studentBean.setName("anfly" + i);
            studentBean.setAge(String.valueOf(i));
            DbHelper.getInstance().insert(studentBean);
        }
    }
}
