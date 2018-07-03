package cn.leo.bottombar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Leo on 2018/1/29.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {

    private float mOneDip;
    private OnTabSelectListener mOnTabSelectListener;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mOneDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof BottomTab) {
                LayoutParams layoutParams = getLayoutParams((BottomTab) child);
                child.setLayoutParams(layoutParams);
            }
        }
    }

    public void addTab(BottomTab tab) {
        //tab.measure(0, 0);
        LayoutParams params = getLayoutParams(tab);
        attachViewToParent(tab, -1, params);
    }

    @NonNull
    private LayoutParams getLayoutParams(BottomTab tab) {
        ViewGroup.LayoutParams layoutParams = tab.getLayoutParams();
        LinearLayout.LayoutParams params;
        if (layoutParams == null) {
            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.BOTTOM;
        } else {
            params = (LayoutParams) layoutParams;
        }
        tab.setOnClickListener(this);
        return params;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        BottomTab bottomTab = (BottomTab) v;
        syncState();
        bottomTab.setSelected();
        if (mOnTabSelectListener != null) {
            mOnTabSelectListener.onTabSelected(bottomTab, bottomTab.getTitle());
        }
    }

    //同步选中状态
    private void syncState() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            BottomTab child = (BottomTab) getChildAt(i);
            child.setUnselected();
        }
    }

    //指定选中条目
    public void selectTabAtPosition(int index) {
        BottomTab child = (BottomTab) getChildAt(index);
        if (child != null) {
            syncState();
            child.setSelected();
        }
    }

    //设置点击监听
    public void setOnTabSelectListener(@NonNull OnTabSelectListener listener) {
        mOnTabSelectListener = listener;
    }

    public interface OnTabSelectListener {
        void onTabSelected(BottomTab tabView, String title);
    }
}
