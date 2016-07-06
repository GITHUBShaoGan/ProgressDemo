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
        imageResId = typedArray.getResourceId(R.styleable.ArcProgressView_arc_image_res_id, R.mipmap.ic_launcher);
        width = typedArray.getDimension(R.styleable.ArcProgressView_arc_width, 100.f);
        height = typedArray.getDimension(R.styleable.ArcProgressView_arc_height, 100.f);
        strokeWidth = typedArray.getDimension(R.styleable.ArcProgressView_stroke_width, 2.f);

        backgroundColor = typedArray.getColor(R.styleable.ArcProgressView_background_color, Color.WHITE);
        arcBackgroundColor = typedArray.getColor(R.styleable.ArcProgressView_arc_bg_color, Color.GRAY);
        arcForegroundColor = typedArray.getColor(R.styleable.ArcProgressView_arc_fg_color, Color.GREEN);

        max = typedArray.getInteger(R.styleable.ArcProgressView_max, 100);
        min = typedArray.getInteger(R.styleable.ArcProgressView_min, 0);
        progress = typedArray.getFloat(R.styleable.ArcProgressView_progress, 30.f);
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

        paint.setAntiAlias(true);
        paint.setColor(arcBackgroundColor);
        canvas.drawColor(Color.TRANSPARENT);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);

        rectF.left = strokeWidth / 2;
        rectF.top = strokeWidth / 2;
        rectF.right = width - strokeWidth / 2;
        rectF.bottom = height - strokeWidth / 2;

        canvas.drawArc(rectF, -90, 360, false, paint);//绘制外框大圆
        paint.setColor(arcForegroundColor);
        canvas.drawArc(rectF, -90, progress / max * 360, false, paint);//绘制进度

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        if (bitmap != null) {
            int bmpWidth = bitmap.getWidth();
            int bmpHeight = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = (float) width / 3 / bmpWidth;//图片大小为控件的1/3
            float scaleHeight = (float) height / 3 / bmpHeight;
            matrix.setScale(scaleWidth, scaleHeight);//缩放倍数
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
            float left = width / 2 - bitmap.getWidth() / 2;
            float top = height / 2 - bitmap.getHeight() / 2;
            canvas.drawBitmap(bitmap, left, top, paint);//绘制图片
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap = null;
            bitmap.recycle();
        }
        this.bitmap = bitmap;
        postInvalidate();
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
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
