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
                                  |支持自定义起止范围
                                  |支持自定义字体颜色大小
                                  |支持自定义最小滑动速率
                                  |支持自定义刻度与文字之间距离
                                  |支持边界回弹动画
                                  |支持Fling滑动
                                  |支持设置默认值
      
### UI
   ![1](https://github.com/zhangxuyang321/XyzInfo/blob/master/ui/2.gif)
   
### xswitch使用

    Gradle compile 'com.xyz.xswitch:xswitch:1.0.0'
    
    属性介绍
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
    xLeftIcon | 左侧图片 | reference | null | 是
    xRightIcon | 右侧图片 | reference | null |是
   
### LICENSE 开源协议

    Apache License Version 2.0
