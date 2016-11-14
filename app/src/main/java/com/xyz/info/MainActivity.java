package com.xyz.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xyz.xruler.XyzRuler;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private XyzRuler ruler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        ruler = (XyzRuler) findViewById(R.id.ruler);
        /**
         * 设置选中的条目
         */
        ruler.setOnSelectItem(new XyzRuler.SelectItem() {
            @Override
            public int setSelectItem() {
                return 10;
            }
        });

        /**
         * 监听拖动时值得变化
         */
        ruler.setOnRulerValueChangeListener(new XyzRuler.RulerValue() {
            @Override
            public void value(int value) {
                tv.setText(value + "kg");
            }
        });
    }
}
