package cn.leo.mybottombar.MyBottomBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import cn.leo.mybottombar.R;

/**
 * Created by Leo on 2018/1/29.
 */

public class BottomTab extends ConstraintLayout {

    private TextView mSubTitle;
    private ImageView mIcon;
    private BubbleNum mBubbleNum;
    private float mOneDip;
    private String mTitle;
    private int mUnSelectedRes;
    private int mSelectedRes;
    private int mHeightDp;
    private int mTextSelectedColor;
    private int mTextUnSelectedColor;

    public BottomTab(Context context) {
        this(context, null);
    }

    public BottomTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomTab);
        mTitle = a.getString(R.styleable.BottomTab_bottomTab_title_text);
        mSelectedRes = a.getResourceId(R.styleable.BottomTab_bottomTab_icon_selected, 0);
        mUnSelectedRes = a.getResourceId(R.styleable.BottomTab_bottomTab_icon_unselected, 0);
        mTextSelectedColor = a.getColor(R.styleable.BottomTab_bottomTab_title_selectedColor, getResources().getColor(R.color.colorPrimary));
        mTextUnSelectedColor = a.getColor(R.styleable.BottomTab_bottomTab_title_unselectedColor, Color.GRAY);
        int bubbleStyle = a.getInt(R.styleable.BottomTab_bottomTab_icon_bubbleStyle, 0);
        int bubbleNum = a.getInt(R.styleable.BottomTab_bottomTab_icon_bubbleNum, 0);
        boolean isHideTitle = a.getBoolean(R.styleable.BottomTab_bottomTab_title_hide, false);
        a.recycle();
        init();
        if (bubbleStyle > 0) {
            setBubbleDot();
        } else {
            mBubbleNum.setJustBubble(false);
        }
        setBubbleNum(bubbleNum);
        if (isHideTitle) {
            hideTitle();
        }
    }

    public BottomTab(Context context,
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
        if (mHeightDp == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int height = MeasureSpec.makeMeasureSpec((int) (mHeightDp * mOneDip), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, height);
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mOneDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics);
        //文本标题
        mSubTitle = new TextView(getContext());
        mSubTitle.setText(mTitle);
        mSubTitle.setTextColor(Color.GRAY);
        mSubTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        mSubTitle.setId(R.id.showTitle);
        mSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
        //图标
        mIcon = new ImageView(getContext());
        mIcon.setImageResource(mUnSelectedRes);
        mIcon.setId(R.id.showHome);
        //气泡
        mBubbleNum = new BubbleNum(getContext());
        mBubbleNum.setId(R.id.showCustom);
        //添加到本容器
        addView(mSubTitle);
        addView(mIcon);
        addView(mBubbleNum);
        //位置调整
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        //设置文本底部居中
        set.connect(mSubTitle.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.connect(mSubTitle.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(mSubTitle.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(mSubTitle.getId(), ConstraintSet.TOP, mIcon.getId(), ConstraintSet.BOTTOM);
        //设置图片在文本上面
        set.connect(mIcon.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) (3.5 * mOneDip));
        set.connect(mIcon.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(mIcon.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(mIcon.getId(), ConstraintSet.BOTTOM, mSubTitle.getId(), ConstraintSet.TOP);
        //设置气泡在图片右上角
        set.constrainCircle(mBubbleNum.getId(), mIcon.getId(), (int) (mHeightDp * mOneDip / 3), 50f);
        set.applyTo(this);
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
        mSubTitle.setTextColor(mTextSelectedColor);
        mIcon.setImageResource(mSelectedRes);
    }

    //条目取消选中
    public void setUnselected() {
        mSubTitle.setTextColor(mTextUnSelectedColor);
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
