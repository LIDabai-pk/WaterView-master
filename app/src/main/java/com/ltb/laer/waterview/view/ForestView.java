package com.ltb.laer.waterview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ltb.laer.waterview.BuildConfig;

public class ForestView extends ViewGroup {

    private int mLandHeight;
    private int mLandWidth;

    public ForestView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        double cWidth = 0;
        double cHeight = 0;
        MarginLayoutParams cParams = null;

        //  Traversal all the ChildView

        for (int i = 0; i < cCount; i++){
            View childView = getChildAt(i);
            cWidth = (1.0 / 5) * mLandWidth;
            cHeight = (1.0 / 5) * mLandHeight;
            double cl = 0.0, ct = 0.0, cr = 0.0, cb = 0.0;

            switch (i){
                case 0:
                    cl = 0.0;
                    ct = 0.0;
                    cWidth = mLandWidth;
                    cHeight = mLandHeight;
                    cr = cl + cWidth;
                    cb = cHeight + ct;
                    childView.layout((int)cl, (int)ct, (int)cr, (int)cb);
                    continue;

//                case 1:
//                    cl = (2.0 / 5.0) * mLandWidth;
//                    ct = 0.0;
//                    break;
//
//                case 2:
//                    cl = (3.0 / 10.0) * mLandWidth;
//                    ct = (1.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 3:
//                    cl = (1.0 / 2.0) * mLandWidth;
//                    ct = (1.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 4:
//                    cl = (1.0 / 5.0) * mLandWidth;
//                    ct = (1.0 / 5.0) * mLandHeight;
//                    break;
//
//                case 5:
//                    cl = (2.0 / 5.0) * mLandWidth;
//                    ct = (1.0 / 5.0) * mLandHeight;
//                    break;
//
//                case 6:
//                    cl = (3.0 / 5.0) * mLandWidth;
//                    ct = (1.0 / 5.0) * mLandHeight;
//                    break;
//
//                case 7:
//                    cl = (1.0 / 10.0) * mLandWidth;
//                    ct = (3.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 8:
//                    cl = (3.0 / 10.0) * mLandWidth;
//                    ct = (3.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 9:
//                    cl = (5.0 / 10.0) * mLandWidth;
//                    ct = (3.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 10:
//                    cl = (7.0 / 10.0) * mLandWidth;
//                    ct = (3.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 11:
//                    cl = 0.0;
//                    ct = (4.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 12:
//                    cl = (2.0 / 10.0) * mLandWidth;
//                    ct = (4.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 13:
//                    cl = (4.0 / 10.0) * mLandWidth;
//                    ct = (4.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 14:
//                    cl = (6.0 / 10.0) * mLandWidth;
//                    ct = (4.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 15:
//                    cl = (8.0 / 10.0) * mLandWidth;
//                    ct = (4.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 16:
//                    cl = (1.0 / 10.0) * mLandWidth;
//                    ct = (5.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 17:
//                    cl = (3.0 / 10.0) * mLandWidth;
//                    ct = (5.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 18:
//                    cl = (5.0 / 10.0) * mLandWidth;
//                    ct = (5.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 19:
//                    cl = (7.0 / 10.0) * mLandWidth;
//                    ct = (5.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 20:
//                    cl = (2.0 / 10.0) * mLandWidth;
//                    ct = (6.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 21:
//                    cl = (4.0 / 10.0) * mLandWidth;
//                    ct = (6.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 22:
//                    cl = (6.0 / 10.0) * mLandWidth;
//                    ct = (6.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 23:
//                    cl = (3.0 / 10.0) * mLandWidth;
//                    ct = (7.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 24:
//                    cl = (5.0 / 10.0) * mLandWidth;
//                    ct = (7.0 / 10.0) * mLandHeight;
//                    break;
//
//                case 25:
//                    cl = (4.0 / 10.0) * mLandWidth;
//                    ct = (8.0 / 10.0) * mLandHeight;
//                    break;

                default:
                    break;

            }

            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout((int)cl, (int)ct, (int)cr, (int)cb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        get width and height
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

//        calculate all the height and width of childView
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width;
        int height;
        int cWidth;
        int cHeight;
        MarginLayoutParams cParams = null;

        View childView = getChildAt(0);
        cWidth = childView.getMeasuredWidth();
        cHeight = childView.getMeasuredHeight();
        mLandHeight = childView.getMeasuredHeight();
        mLandWidth = childView.getMeasuredWidth();
        cParams = (MarginLayoutParams)childView.getLayoutParams();
        width = cWidth + cParams.leftMargin + cParams.rightMargin;
        height = cHeight + cParams.topMargin + cParams.bottomMargin;

        if(BuildConfig.DEBUG)Log.d("MyViewFieldGroup", "height:" + height);

        if(BuildConfig.DEBUG)Log.d("MyViewFieldGroup", "width:" + width);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }



}
