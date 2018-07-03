package cn.leo.mybottombar.MyBottomBar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.leo.mybottombar.R;

/**
 * Created by Leo on 2018/1/29.
 */

public class BottomTabBackup extends RelativeLayout {

    private TextView mSubTitle;
    private ImageView mIcon;
    private BubbleNum mBubbleNum;
    private float mOneDip;
    private String mTitle;
    private int mUnSelectedRes;
    private int mSelectedRes;
    private int mHeightDp;

    public BottomTabBackup(Context context) {
        this(context, null);
    }

    public BottomTabBackup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabBackup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BottomTabBackup(Context context,
                           String title,
                           @DrawableRes int selectedRes,
                           @DrawableRes int unSelectedRes,
                           int heightDp) {
        super(context);
        mTitle = title;
        mSelectedRes = selectedRes;
        mUnSelectedRes = unSelectedRes;
        mHeightDp = heightDp;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec((int) (mHeightDp * mOneDip), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, height);
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mOneDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics);
        mSubTitle = new TextView(getContext());
        mIcon = new ImageView(getContext());
        mBubbleNum = new BubbleNum(getContext());
        addView(mSubTitle);
        addView(mIcon);
        addView(mBubbleNum);
        //设置文本底部居中
        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mSubTitle.setText(mTitle);
        mSubTitle.setTextColor(Color.GRAY);
        mSubTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        mSubTitle.setLayoutParams(params);
        mSubTitle.setId(R.id.showTitle);
        mSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);

        //设置图片在文本上面
        LayoutParams params1 =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params1.addRule(RelativeLayout.ABOVE, R.id.showTitle);
        mIcon.setImageResource(mUnSelectedRes);
        mIcon.setLayoutParams(params1);
        mIcon.setId(R.id.showHome);

        //设置气泡在图片右上角
        LayoutParams params2 =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //params2.addRule(RelativeLayout.ABOVE, R.id.showHome);
        params2.addRule(RelativeLayout.ALIGN_RIGHT, R.id.showHome);
        params2.rightMargin = (int) (mOneDip * 15);
        mBubbleNum.setLayoutParams(params2);
    }

    //设置消息泡泡数
    public void setBubbleNum(int num) {
        mBubbleNum.setNum(num);
    }

    //不显示数字只显示小红点
    public void setBubbleDot() {

        mBubbleNum.setJustBubble(true);
    }

    //获取条目标题
    public String getTitle() {
        return mTitle;
    }

    //条目被选中
    public void setSelected() {
        mSubTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        mIcon.setImageResource(mSelectedRes);
    }

    //条目取消选中
    public void setUnselected() {
        mSubTitle.setTextColor(Color.GRAY);
        mIcon.setImageResource(mUnSelectedRes);
    }

    //隐藏标题且不占位
    public void hideTitle() {
        mSubTitle.setVisibility(GONE);
    }

    //隐藏标题占位
    public void invisibleTitle() {
        mSubTitle.setVisibility(INVISIBLE);
    }
}
