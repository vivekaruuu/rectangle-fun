package com.example.rectanglefun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCanvas extends View {

    private static final String TAG = "MyCanvas";
    Paint mPaint;
    float mLeft=0,mTop=0,mRight=500,mBottom=500;
    float dx1,dx2,dy1,dy2;
    float getBorderX(){
        return (mRight-mLeft)/3;
    }
    float getBorderY(){
        return (mBottom-mRight)/3;
    }
    int flag=1;
    public MyCanvas(Context context) {
        super(context);

        init(null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    public void init(@Nullable AttributeSet set){
        mPaint=new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mLeft,mTop,mRight,mBottom,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if(x-getBorderX()>mLeft&&x+getBorderX()<mRight&&y+getBorderY()<mBottom&&y-getBorderY()>mTop){
                    dx1=x;
                    dx2=x;
                    dy1=y;
                    dy2=y;
                    flag=1;
                }


            }break;

            case MotionEvent.ACTION_MOVE:{
                if(x-getBorderX()>mLeft&&x+getBorderX()<mRight&&y+getBorderY()<mBottom&&y-getBorderY()>mTop) {
                    mLeft+= x - dx1;
                    mRight+= x - dx2;
                    mTop+= y - dy1;
                    mBottom+= y - dy2;
                    invalidate();
                    dx1 =x;
                    dx2=x;
                    dy1=y;
                    dy2=y;
                    flag=1;


                }

                else {

                    if ((x > mLeft && x < mRight && y < mBottom && y > mTop) || flag == 0) {
                        flag = 0;

                        if (x < mLeft + getBorderX()) {
                            if(y<(mTop+(mBottom-mTop)/2)){
                                Log.d(TAG, "onTouchEvent: A");
                                mLeft = x;
                                mTop = y;
                            }
                            else{
                                Log.d(TAG, "onTouchEvent: B");
                                mLeft = x;
                                mBottom = y;
                            }
                            invalidate();
                        } else if (x > mRight - getBorderX()) {
                            if(y<(mTop+(mBottom-mTop)/2)) {
                                mRight = x;
                                mTop = y;
                                invalidate();
                            }
                            else{
                                mRight = x;
                                mBottom = y;
                                invalidate();

                            }
                        }
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP:{
                flag=1;

            }

        }
        return true;


    }
}
