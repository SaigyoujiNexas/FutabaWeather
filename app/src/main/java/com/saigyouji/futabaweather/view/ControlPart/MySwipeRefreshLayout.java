package com.saigyouji.futabaweather.view.ControlPart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySwipeRefreshLayout extends SwipeRefreshLayout
{
    private float startX;
    private float startY;
    private boolean isVpDragger;
    private final int TouchSlop;

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                    startX = ev.getX();
                    startY = ev.getY();
                    isVpDragger = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(isVpDragger)
                        return false;
                    float endX = ev.getX();
                    float endY = ev.getY();
                    float distanceX = Math.abs(endX - startX);
                    float distanceY = Math.abs(endY - startY);
                    if(distanceX > TouchSlop && distanceX > distanceY)
                    {
                        isVpDragger = true;
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isVpDragger = false;
                    break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
