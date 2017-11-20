## 基类的封装

> 需要导入的依赖库

    //添加 cardview 和 design
    implementation "com.android.support:cardview-v7:v26.1.0"
    implementation "com.android.support:design:v26.1.0"<br>
    //RxLifecycle
    implementation 'com.trello.rxlifecycle2:rxlifecycle-components:2.0.1'<br>
    //黄油刀
    compile 'com.jakewharton:butterknife:7.0.1'<br>
    // 简化版 Adapter
    implementation 'me.drakeet.multitype:multitype:3.3.3'
***
> 布局文件的添加

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:app="http://schemas.android.com/apk/res-auto"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent"
                  android:background="@color/White"
                  android:orientation="vertical"
                  app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <android.support.v4.widget.SwipeRefreshLayout
            android:id="@+id/refresh_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fadeScrollbars="true"
                android:scrollbarFadeDuration="1"
                android:scrollbars="vertical"
                app:layoutManager="LinearLayoutManager"/>
        </android.support.v4.widget.SwipeRefreshLayout>
    </LinearLayout>




