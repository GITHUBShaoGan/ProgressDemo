package com.julyapp.progressdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.julyapp.progressdemo.R;

/**
 * Created by xiaojian on 2016/7/6.
 */

public class ArcProgressView extends View {

    private Bitmap bitmap;
    private int imageResId;
    private float width = 100;
    private float height = 100;
    private float strokeWidth = 2f;
    private int backgroundColor;
    private int arcBackgroundColor;
    private int arcForegroundColor;
    private int max;
    private int min;
    private float progress;

    private final RectF rectF;
    private final Paint paint;
    private final Context context;

    public ArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        this.context = context;
        this.rectF = new RectF();
        this.paint = new Paint();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ArcProgressView);
        imageResId = typedArray.getResourceId(R.styleable.ArcProgressView_arc_image_res_id, R.mipmap.ic_launcher);//默认背景图片资源id
        width = typedArray.getDimension(R.styleable.ArcProgressView_arc_width, 100.f);//控件宽度，默认为100px
        height = typedArray.getDimension(R.styleable.ArcProgressView_arc_height, 100.f);//控件高度，默认为100px
        strokeWidth = typedArray.getDimension(R.styleable.ArcProgressView_stroke_width, 2.f);//进度条宽度，默认为2px

        backgroundColor = typedArray.getColor(R.styleable.ArcProgressView_background_color, Color.WHITE);//控件默认背景颜色，默认白色
        arcBackgroundColor = typedArray.getColor(R.styleable.ArcProgressView_arc_bg_color, Color.GRAY);//进度条背景色，默认灰色
        arcForegroundColor = typedArray.getColor(R.styleable.ArcProgressView_arc_fg_color, Color.GREEN);//进度条前景色，默认绿色

        max = typedArray.getInteger(R.styleable.ArcProgressView_max, 100);//最大刻度值
        min = typedArray.getInteger(R.styleable.ArcProgressView_min, 0);//最小刻度值
        progress = typedArray.getFloat(R.styleable.ArcProgressView_progress, 30.f);//进度值
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = this.width;
        float height = this.height;
        if (width != height) {
            float min = Math.min(width, height);
            width = height = min;
        }

        //设置画笔
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(arcBackgroundColor);
        canvas.drawColor(Color.TRANSPARENT);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);

        rectF.left = strokeWidth / 2;
        rectF.top = strokeWidth / 2;
        rectF.right = width - strokeWidth / 2;
        rectF.bottom = height - strokeWidth / 2;

        canvas.drawArc(rectF, -90, 360, false, paint);//绘制外框大圆
        paint.setColor(arcForegroundColor);//改变画笔颜色，准备绘制进度
        canvas.drawArc(rectF, -90, progress / max * 360, false, paint);//绘制进度

        bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId);//得到背景图片bitmap对象
        if (bitmap != null) {
            int bmpWidth = bitmap.getWidth();//获取bitmap宽
            int bmpHeight = bitmap.getHeight();//获取bitmap高
            Matrix matrix = new Matrix();//初始化矩阵
            float scaleWidth = width / 3 / bmpWidth;//图片宽为控件宽度1/3
            float scaleHeight = height / 3 / bmpHeight;//图片高为控件高度1/3
            matrix.setScale(scaleWidth, scaleHeight);//缩放倍数
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);//得到缩放后的bitmap
            float left = width / 2 - bitmap.getWidth() / 2;//得到中心点距左边的距离
            float top = height / 2 - bitmap.getHeight() / 2;//得到中心掉距顶部距离，保证居中
            canvas.drawBitmap(bitmap, left, top, paint);//绘制图片
        }
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        //每次新设置一张图片时，销毁原有bitmap对象，防止内存泄漏
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        this.imageResId = imageResId;
        postInvalidate();
    }

    public void setWidth(int width) {
        this.width = width;
        postInvalidate();
    }

    public void setHeight(int height) {
        this.height = height;
        postInvalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        postInvalidate();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        postInvalidate();
    }

    public int getArcBackgroundColor() {
        return arcBackgroundColor;
    }

    public void setArcBackgroundColor(int arcBackgroundColor) {
        this.arcBackgroundColor = arcBackgroundColor;
        postInvalidate();
    }

    public int getArcForegroundColor() {
        return arcForegroundColor;
    }

    public void setArcForegroundColor(int arcForegroundColor) {
        this.arcForegroundColor = arcForegroundColor;
        postInvalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        postInvalidate();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        postInvalidate();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        if (progress >= min && progress <= max) {
            this.progress = progress;
            postInvalidate();
        } else {
            this.progress = 0f;
            postInvalidate();
        }
    }

    public RectF getRectF() {
        return rectF;
    }

    public Paint getPaint() {
        return paint;
    }
}
