package com.ltb.laer.waterview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import com.ltb.laer.waterview.R;
import com.ltb.laer.waterview.model.Point;

import java.util.ArrayList;
import java.util.List;


public class TreeView2 extends View {


    private int mPanelWidth;
    private static int MAX_LINE = 5;
    private int mLineHeight;

    private Paint paint = new Paint();

    //图片
    private Bitmap tree0;
    private Bitmap tree1;
    private Bitmap tree2;
    private Bitmap tree3;

    private Bitmap land;
    private int count;
    private List<Integer> list_draw = new ArrayList<>();

    private float pieceLineHeight = 0.75f;

    private List<Point> list = new ArrayList<>();


    public TreeView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources resources = getResources();
        tree0 = BitmapFactory.decodeResource(resources, R.drawable.tree0);
        tree1 = BitmapFactory.decodeResource(resources, R.drawable.tree1);
        tree2 = BitmapFactory.decodeResource(resources, R.drawable.tree2);
        tree3 = BitmapFactory.decodeResource(resources, R.drawable.tree3);

        land = BitmapFactory.decodeResource(resources, R.drawable.isomorphic_forest);
        initPaint();
    }

    public void setPoint(int count, List<Integer> list1) {
        mPanelWidth = 900;
        mLineHeight = mPanelWidth / MAX_LINE - 6;
//        mPanelWidth -=mLineHeight;//出去周边多余的距离
//        //重新计算每个格子的尺寸
//        mLineHeight = mPanelWidth / MAX_LINE;
        //所有绘制图片的集合
        list_draw = list1;
        //数目
        count = count;
        //实际行数
        int line = 0;
        //计算实际行数
        //中心点位置
//        int centerPointX = mPanelWidth/2 + mLineHeight/8;
//        int centerPointY = mPanelWidth/2 - 2*mLineHeight - mLineHeight*2/5;
        //起始点位置
        int centerPointX = 0 + mLineHeight / 8;
        int centerPointY = 2 * mLineHeight - mLineHeight / 8;
//        list.add(new Point(centerPointX,centerPointY));
        line = count / 5 + 1;
        int x = 0;
        int y = 0;
        for (int j = 0; j < line; j++) {

            for (int i = 0; i < 5; i++) {
                if (j * 5 + i >= count) {
                    break;
                } else {
                    //起点变化
                    int startX = 0;
                    int startY = 0;
                    if (j == 0) {
                        startX = centerPointX;
                        startY = centerPointY;
                    }
                    if (j > 0) {
                        startX += centerPointX + (mLineHeight / 2 * j);
                        startY += centerPointY + (mLineHeight / 2 * j);
                    }
                    y = startY - (mLineHeight / 2 * i);
                    x = startX + ((i % 5) * (mLineHeight / 2));
                    list.add(new Point(x,y));
                }
            }
        }
        //重绘
        requestLayout();
        invalidate();
    }


    private void initPaint() {
        paint.setColor(0x88000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int size = 0;
        if (widthModel == MeasureSpec.UNSPECIFIED) {
            size = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            size = widthSize;
        } else {
            size = Math.min(widthSize, heightSize);
        }
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth / MAX_LINE;
        int pieceWidth = (int) (mLineHeight * pieceLineHeight);
        tree0 = Bitmap.createScaledBitmap(tree0, pieceWidth, pieceWidth, false);
        tree1 = Bitmap.createScaledBitmap(tree1, pieceWidth, pieceWidth, false);
        tree2 = Bitmap.createScaledBitmap(tree2, pieceWidth, pieceWidth, false);
        tree3 = Bitmap.createScaledBitmap(tree3, pieceWidth, pieceWidth, false);
//        setPoint(count);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawChessBoard(canvas);
        drawPieces(canvas);
    }

    private void drawPieces(Canvas canvas) {
        Bitmap bitmap = null;
//        for (Point point : list) {
//            if (point.getY() == 0) {
//                continue;
//            }
//            canvas.drawBitmap(tree0,
//                    point.getX(),
//                    point.getY(),
//                    null);
//        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getY() == 0) {
                continue;
            }
            if (list_draw.get(i) == 0) {
                bitmap = tree0;
            } else if (list_draw.get(i) == 1) {
                bitmap = tree1;
            } else if (list_draw.get(i) == 2) {
                bitmap = tree2;
            } else {
                bitmap = tree3;
            }
            canvas.drawBitmap(bitmap,
                    list.get(i).getX(),
                    list.get(i).getY(),
                    null);
        }

    }

    private void drawChessBoard(Canvas canvas) {
//        int w = mPanelWidth;
//        float lineHeight = mLineHeight;

//        canvas.drawRect(0, 0, 900, 900, mPaint);
        Paint mPaint = new Paint();
        mPaint.setColor(getContext().getColor(R.color.green));
        mPaint.setStyle(Paint.Style.FILL); //fill the background with blue color
        canvas.drawRect(0, 0, 900, 900, mPaint);

        land = Bitmap.createScaledBitmap(land, 900, 900, false);
        canvas.drawBitmap(land, 0, 0, null);

//        for (int i = 0; i < MAX_LINE; i++) {

//            float startX = 0;
//            float endX = w;
//            float y = (float) ((i) * lineHeight);
//            canvas.drawLine(startX, y, endX, y, paint);
//            canvas.drawLine(y, startX, y, endX, paint);
//        }

    }

}
