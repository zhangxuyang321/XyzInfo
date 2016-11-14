package com.xyz.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.xyz.xruler.XyzRuler;
import com.xyz.xswitch.XyzSwitch;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XyzSwitch xSwitch =  (XyzSwitch) findViewById(R.id.xswitch);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        XyzRuler ruler1 = (XyzRuler) findViewById(R.id.ruler1);
        XyzRuler ruler2 = (XyzRuler) findViewById(R.id.ruler2);
        XyzRuler ruler3 = (XyzRuler) findViewById(R.id.ruler3);
        XyzRuler ruler4 = (XyzRuler) findViewById(R.id.ruler4);

        xSwitch.setOnXyzSwitchChangeListener(new XyzSwitch.XyzSwitchChange() {
            @Override
            public void changed(boolean isRight) {
                Toast.makeText(MainActivity.this,isRight?"男":"女",Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 设置选中的条目
         */
        ruler1.setOnSelectItem(new XyzRuler.SelectItem() {
            @Override
            public int setSelectItem() {
                return 60;
            }
        });
        ruler2.setOnSelectItem(new XyzRuler.SelectItem() {
            @Override
            public int setSelectItem() {
                return 170;
            }
        });
        ruler3.setOnSelectItem(new XyzRuler.SelectItem() {
            @Override
            public int setSelectItem() {
                return 1990;
            }
        });
        ruler4.setOnSelectItem(new XyzRuler.SelectItem() {
            @Override
            public int setSelectItem() {
                return 60;
            }
        });

        /**
         * 监听拖动时值得变化
         */
        ruler1.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                tv1.setText("体重\n"+value+"\nkg");
            }
        });
        ruler2.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                tv2.setText("身高\n"+value+"\ncm");
            }
        });
        ruler3.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                tv3.setText("出生年\n"+value);
            }
        });
        ruler4.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                tv4.setText("每日运动\n"+value+"\n分钟");
            }
        });
    }
}
