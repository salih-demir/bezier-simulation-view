package com.cascade.beziersimulationview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class BezierSimulationView extends View {
    private static final int STROKE_SIZE_IN_DP = 2;

    private double ratio = 1;
    private int times = 0;
    private Paint wavePaint;
    private Paint linePaint;

    public BezierSimulationView(Context context) {
        super(context);
        initialize();
    }

    public BezierSimulationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BezierSimulationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        int storeSizeInPx = convertDpToPixel(STROKE_SIZE_IN_DP);

        wavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavePaint.setColor(Color.WHITE);
        wavePaint.setStrokeWidth(storeSizeInPx);
        wavePaint.setStyle(Paint.Style.STROKE);
        wavePaint.setStrokeJoin(Paint.Join.ROUND);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(storeSizeInPx);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setAlpha(255 / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        times++;
        canvas.drawPath(buildWavePath(width, height, ratio, wavePaint), wavePaint);
        canvas.drawPath(buildLinePath(width, height, linePaint), linePaint);
    }

    private int convertDpToPixel(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp);
    }

    private Path buildLinePath(int widthMax, int heightMax, Paint paint) {
        double targetHeight = heightMax - paint.getStrokeWidth();

        Path path = new Path();
        path.moveTo(0, (int) targetHeight / 2);
        path.lineTo(widthMax, (int) targetHeight / 2);
        return path;
    }

    private Path buildWavePath(int widthMax, int heightMax, double ratio, Paint paint) {
        double targetHeight = (int) (heightMax * ratio) - paint.getStrokeWidth();
        double halfWidth = widthMax / 2.0f;
        double sinRatio = widthMax / 20;

        Path path = new Path();
        boolean firstTime = true;
        for (int x = 0; x < widthMax; x++) {
            double heightRatio = x / halfWidth;
            if (heightRatio > 1)
                heightRatio = 2 - heightRatio;

            int y;
            if (times % 6 == 0)
                y = (int) ((Math.cos(x / sinRatio * 3) * Math.sin(x / sinRatio / 1.9)) * (targetHeight * heightRatio) / 2) + heightMax / 2;
            else
                y = (int) ((Math.cos(x / sinRatio * 3) * Math.sin(x / sinRatio / 2)) * (targetHeight * heightRatio) / 2) + heightMax / 2;

            if (firstTime) {
                firstTime = false;
                path.moveTo(x, y);
            } else
                path.lineTo(x, y);
        }
        return path;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
        invalidate();
    }
}