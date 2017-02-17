# XyzInfo

  自定义Switch 和 Ruler控件[(博客链接)](https://zhangxuyang321.github.io)

### UI
   [Download Demo](https://github.com/zhangxuyang321/XyzInfo/blob/master/apk/demo.apk)
   
   ![1](https://github.com/zhangxuyang321/XyzInfo/blob/master/ui/2.gif)

###介绍

      xSwitch | xRuler
      --- | ---
      支持背与滑块颜色自定义          | 支持两种显示模式
      支持颜色过渡动画               | 支持上下两种刻度
      支持自定义圆角弧度             | 支持自定义指示器颜色大小
      支持自定义宽高比               |支持自定义刻度之间像素数目(间距)
      支持自定义滑块与背景大小比例     |支持自定义刻度线宽度
      支持自定义图片(建议灰色)以及大小 |支持自定义刻度基本单位(最小刻度单位)
      支持自定义滑动时间             |支持自定义起止范围
                                  |支持自定义字体颜色大小
                                  |支持自定义最小滑动速率
                                  |支持自定义刻度与文字之间距离
                                  |支持边界回弹动画
                                  |支持Fling滑动
                                  |支持设置默认值
                                  |支持自定义设置回弹动画基本时间
   
### xswitch使用

      因为使用ColorMatrix使灰色图片变成白色,所以icon需要为灰色图片

###### Gradle 

       compile 'com.xyz.xswitch:xswitch:1.0.1'
    
###### 属性介绍
    
    属性 | 介绍 | 类型 | 默认 | 是否必须
    --- | --- | --- | --- | ---
    xRadian | 圆角弧度 | dimension | 45 |否
    xASPECTRATIO | 宽高比 | float | 0.37f | 否
    xASPEERRATIO | 滑块与背景宽度比 | float | 0.56f | 否
    xIconWidth | 图片宽度 |dimension | 80 | 否
    xIconHeight | 图片高度 | dimension | 80 | 否
    xBackgroundFromColor | 背景起始颜色 | color |0xff443c6f | 否
    xBackgroundToColor |背景终止颜色 | color | 0xffff5a73 | 否
    xSliderFromColor | 滑块起始颜色 | color | 0xffff5a73 | 否
    xSliderToColor |滑块终止颜色 | color | 0xff443c6f | 否
    xChangedTime | 滑块滑动时间 | int | 300 | 否
    xLeftIcon | 左侧图片 | reference | null | 是
    xRightIcon | 右侧图片 | reference | null |是
    
###### 代码
      
     ```
     XyzSwitch xSwitch =  (XyzSwitch) findViewById(R.id.xswitch);
     xSwitch.setOnXyzSwitchChangeListener(new XyzSwitch.XyzSwitchChange() {
            @Override
            public void changed(boolean isRight) {
                Toast.makeText(MainActivity.this,isRight?"男":"女",Toast.LENGTH_SHORT).show();
            }
        });
     
     ```
    
###### layout

      ```
      <com.xyz.xswitch.XyzSwitch
        android:id="@+id/xswitch"
        android:layout_width="200dp"
        android:layout_height="80dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="35dp"
        app:xIconHeight="40dp"
        app:xIconWidth="40dp"
        app:xLeftIcon="@mipmap/lady"
        app:xRadian="40dp"
        app:xChangedTime="100"
        app:xRightIcon="@mipmap/man" />
      ```
      
### xRuler使用

###### Gradle   
            
         compile 'com.xyz.xruler:xruler:1.0.3' 

###### 属性介绍

    属性 | 介绍 | 类型 | 默认 | 是否必须
    --- | --- | --- | --- | ---
    rBorderWidth | 边框宽度(指针模式下为指针宽度)| dimension | 8 | 否
    rBorderColor | 边框颜色(指针模式下为指针颜色)| color | Color.BLUE | 否
    rLineColor | 刻度线的颜色 | color | Color.WHITE | 否
    rTrigonSize | 边框模式下三角形大小 | dimension | 20 | 否
    rPixel | 最小刻度像素大小 | integer | 15 | 否
    rStep | 刻度最小单位 | integer | 1 | 否
    rLineWidth | 刻度线宽度 | dimension | 2 | 否
    rTextSize | 刻度文字大小 | dimension | 30 | 否
    rTextColor | 刻度文字颜色 | color | Color.WHITE | 否
    rLineHeight | 刻度线高度 | dimension | 25 | 否
    rLineToText | 刻度文字与刻度线距离 | dimension | 35 | 否
    rBegin | 刻度尺范围开始值 | integer | 0 | 否
    rEnd | 刻度尺范围结束值 | integer | 1000 | 否
    rMinVelocity | 触发惯性滑动的最小速率 | integer | 500 | 否
    rAnimTime | 回弹动画时间基准值 | integer | 300 | 否
    rIndicateHeight | 指针高度差值(数值越大高度越小) | dimension | 0 | 否
    rIsRect | 是否是边框模式 | boolean | true | 否
    rIsTop | 刻度线是否在顶部 | boolean | true | 否
    
###### 代码

      ```
       /**
         * 设置选中的条目
         */
        ruler1.setOnSelectItem(new XyzRuler.SelectItem() {
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
      ```
   
###### layout

      ```
      <com.xyz.xruler.XyzRuler
            android:id="@+id/ruler1"
            android:layout_width="242dp"
            android:layout_height="56dp"
            app:rAnimTime="100"
            app:rBegin="40"
            app:rBorderColor="#acF1ff"
            app:rBorderWidth="5dp"
            app:rEnd="300"
            app:rLineHeight="10dp"
            app:rLineWidth="3dp"
            app:rTextSize="10dp"
            app:rTrigonSize="20dp" />
      
      ```
      
### LICENSE 开源协议

    Apache License Version 2.0
