package cn.leo.mybottombar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.leo.mybottombar.MyBottomBar.BottomBar;
import cn.leo.mybottombar.MyBottomBar.BottomTab;

public class MainActivity extends AppCompatActivity implements BottomBar.OnTabSelectListener {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        View viewById = findViewById(R.id.ll);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "------", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        mBottomBar = findViewById(R.id.bottom_bar);
        for (int i = 0; i < 5; i++) {
            BottomTab bottomTab = new BottomTab(this,
                    "测试" + i,
                    R.mipmap.icon_bottom_bar_setting_pressed,
                    R.mipmap.icon_bottom_bar_setting_normal,
                    45);
            if (i == 2) {
                bottomTab = new BottomTab(this,
                        "测试" + i,
                        R.mipmap.icon_bottom_invoice,
                        R.mipmap.icon_bottom_invoice,
                        60);
                bottomTab.invisibleTitle();
            }
            bottomTab.setBubbleNum(i + 1);
            mBottomBar.addTab(bottomTab);
        }
        mBottomBar.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(BottomTab tabView, String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }
}
