package com.faceswiping.app.widget;

/**
 * Created by John on 15/8/6.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 可以拖动的ScrollView
 */
public class CustomerScrollView extends ScrollView {

    //size=a4 表示 拖动的距离为屏幕的高度的1/a4
    private static final int size = 4;

    //第一个子控件
    private View inner;

    //按下的坐标
    private float y;


    // 矩形(这里只是个形式，只是用于判断是否需要动画.)
    private Rect normal = new Rect();

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public CustomerScrollView(Context context) {
        super(context);
    }

    public CustomerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //布局加载完了之后获取第一个子控件，估计测量高度用的
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            //子控件不为空时调用
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }


    //子控件不为空时调用
    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    // Log.v("mlguitar", "will up and animation");
                    animation();
                    //System.out.println("currentY:"+ev.getY()+"--y:"+initY);


                }
                break;
            case MotionEvent.ACTION_MOVE:
                //之前的Y
                final float preY = y;

                //现在的Y
                float nowY = ev.getY();
                /**
                 * size=a4 表示 拖动的距离为屏幕的高度的1/a4
                 */
                int deltaY = (int) (preY - nowY) / size;
                // 滚动
                // scrollBy(0, deltaY);

                //当前的Y
                y = nowY;
                //是否需要移动,而且向下滑动，向上不滑动
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                        return;
                    }
                    int yy = inner.getTop() - deltaY;

                    // 移动布局
                    inner.layout(inner.getLeft(), yy, inner.getRight(),
                            inner.getBottom() - deltaY);
                }
                break;
            default:
                break;
        }
    }

    public void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();

    }

    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }


    /**
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度
     * <p/>
     * getHeight()：获取的是屏幕的高度
     *
     * @return
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        //scrollView的顶部高度
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }

        return false;
    }

    public interface OnRefreshListener {

        //下滑一定程度触发
        public void OnRefresh();

    }

}
