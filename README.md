# ExpandableTextView
an ExpandableTextView on Android platform which can shrink TextView height if its line count is greater than a certain number, it also can toggle state between expand and shrink 

* [English](#english)

#前言：
为了保持界面UI的整洁以及将尽可能多的内容显示在有限的空间中，往往需要将长度过长的TextView进行内容截取。本控件满足了TextView可在"完整内容"与"截取内容"两种模式下进行切换的需求，且可应用在ListView/RecyclerView中并可以动态更新内容。

##截图：
####静态截图如下：
<center>
![静态截图][1]
</center>
<br>
####动态效果图可点击如下链接：
[流量党慎点][2]


##主要功能：
1. 限制行数，行尾添加`ClickSpan`，点击可以"展开"/"收起"两种状态切换；
2. 可使用在`ListView`/`RecyclerView`中，效率较高；
3. 可在任意时刻更新`ExpandableTextView`内容（布局显示之前或者显示之后）；
4. 可自定义行数限制，默认最多显示2行；
5. 可自定义行尾`ClickSpan`是否显示，颜色，文字，按下的背景颜色；
6. 可添加点击此view后是否在"展开"/"收起"状态间切换；
7. 文字不足最大限制行数时，不截断文字，不显示末尾的"展开"/"收起"的指示标识；
8. 可自定义行尾省略语与行尾"展开"/"收起"的指示标识之间的gap文字；

##说明：
1. 效果参考了jQuery的readmore.js，部分代码参考了[ReadMoreTextView][3]
2. 与Github上star数最多的[ExpandableTextView][4]实现原理及UI完全不同。
3. 暂时未添加"收缩"/"展开"时的动画效果。

##优化：
1. 解决末尾显示的指示标识文字与原来文字宽度不一致时的显示问题（如原始文字与行尾指示标识文字为不同语言）。如当结尾指示标识文字较宽时，可能会显示到下一行。以此优化UI体验。
2. 解决末尾单词过长或者跟随标点后，换行留下的空白问题。此问题源于TextView自带的一个属性：当结尾为完整单词或者跟随标点时会连同之前的部分文字一起换行。
3. 解决文字过短时，截取文字超出边界的问题。
4. 解决任何时刻为`ExpandableTextView`更新文字的问题。

##不具有的功能：
1. 限制字符长度。此控件只限制最大行数，不限制字符长度；
2. 省略标识的位置自定义。省略标识的位置暂时只能显示在行尾，不能够指定是否在"行首"/"行中"/"行尾"
3. 暂时未添加"收缩"/"展开"时的动画效果。

##添加依赖
```groovy
compile 'cn.carbs.android:ExpandableTextView:1.0.2'
```

##使用方法：
有两种方法设置文字：
(1)在java中更新文字
```java
//普通视图中的更新
etv.setText(text);
//在ListView/RecyclerView中的应用
etv.updateForRecyclerView(text, etvWidth, state);//etvWidth为控件的真实宽度，state是控件所处的状态，“收缩”/“伸展”状态
```
(2)在xml中直接设置文字
```xml
<cn.carbs.android.expandabletextview.library.ExpandableTextView
                android:id="@+id/etv"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/long_poem" />
```

##实现原理：
1. 控件继承自`TextView`，`TextView`中的`setText(CharSequence text)`方法为 `final` 类型，且其内部最终调用了`setText(CharSequence text, BufferType type)`，因此`ExpandableTextView` Override了`setText(CharSequence text, BufferType type)`方法，且`TextView`在通过xml布局文件设置text时，同样最终是通过`setText(CharSequence text, BufferType type)`进行赋值，因此通过Override此方法达到自定义显示text的效果；
2. 采用android.text.Layout类来确定在一定宽度下，特定的文本所达到的行数，如果超过最大行数，则添加收缩/展开效果；
3. 为文本特定位置添加ClickableSpan，以此添加点击部分文本的响应效果；自定义`ClickableSpan`和`LinkMovementMethod`，达到添加点击`ClickableSpan`文字背景颜色改变的效果，感谢stackoverflow的解答；
4. 通过`Paint.measureText(String text)`方法，找到文本截取的最优位置，使得在行尾添加了ClickableSpan后，不会出现因文字宽度不同而导致的文本换行或者文本末尾空余过大的现象；


##感谢
####[ReadMoreTextView][3]
---------------------
#English


## License

    Copyright 2016 Carbs.Wang (ExpandableTextView)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



[1]: https://github.com/Carbs0126/Screenshot/blob/master/expandabletextview.jpg
[2]: https://github.com/Carbs0126/Screenshot/blob/master/expandabletextview.gif
[3]: https://github.com/borjabravo10/ReadMoreTextView
[4]: https://github.com/Manabu-GT/ExpandableTextView
