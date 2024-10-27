package com.example.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class CircularProgressBar extends View {

    private Paint progressPaint;
    private Paint backgroundPaint;
    private int progress = 0;
    private int maxProgress = 100;
    private int strokeWidth = 20;
    private Handler handler;
    private Runnable runnable;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setColor(Color.BLUE);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.LTGRAY);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setAntiAlias(true);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (progress < maxProgress) {
                    progress += 1;
                    invalidate();
                    handler.postDelayed(this, 50);  // Adjust the speed here
                } else {
                    // Reset the progress once completed
                    Toast.makeText(getContext(),"Progress Completed", Toast.LENGTH_SHORT).show();
                    progress = 0;
                    invalidate();
                    handler.postDelayed(this, 1000);  // Restart after 1 second
                }
            }
        };
        handler.post(runnable);  // Start the progress
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - strokeWidth;

        // Draw background circle
        canvas.drawCircle(width / 2, height / 2, radius, backgroundPaint);

        // Draw progress arc
        float sweepAngle = (360f * progress) / maxProgress;
        canvas.drawArc(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth,
                -90, sweepAngle, false, progressPaint);
    }
}

