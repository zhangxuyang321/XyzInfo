# XyzInfo

      自定义Switch 和 Ruler控件
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
      
### UI
   ![1](https://github.com/zhangxuyang321/XyzInfo/blob/master/ui/2.gif)
   
### xswitch使用

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
            
           
   
### LICENSE 开源协议

    Apache License Version 2.0
