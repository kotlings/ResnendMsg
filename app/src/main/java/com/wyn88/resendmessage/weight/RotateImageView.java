package com.wyn88.resendmessage.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.wyn88.resendmessage.R;

/**
 * 旋转的图片
 *
 * @version 1.0
 *          <p>
 *          Created by TeslaLiu on 2015/9/6.
 */
public class RotateImageView extends ImageView {

    // 旋转之前角度
    private float preRotation = 0;
    // 时间间隔
    private int intervalTime = 100;
    // 旋转角度
    private float intervalDegree = 30.0f;
    // 是否连续旋转
    private boolean isRounded = false;
    // 是否顺时针
    private boolean isClockwise = true;
    // 持续时间
    private int during = 500;
    private long preRotationTime, startTime;
    private boolean alive = true;
    //默认为自动播放
    private boolean autoPlay = true;

    public RotateImageView(Context context) {
        this(context, null);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateImageView);
        intervalDegree = typedArray.getFloat(
                R.styleable.RotateImageView_interval_degree, intervalDegree);
        intervalTime = typedArray.getInteger(
                R.styleable.RotateImageView_interval_time, intervalTime);
        isRounded = typedArray.getBoolean(R.styleable.RotateImageView_rounded, isRounded);
        during = typedArray.getInteger(R.styleable.RotateImageView_during, during);
        isClockwise = typedArray.getBoolean(R.styleable.RotateImageView_is_clockwise, isClockwise);

        typedArray.recycle();
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!alive || !isShown()) return;
        if (0 == startTime) {
            startTime = System.currentTimeMillis();
        }
        long current = System.currentTimeMillis();
        if (isRounded) {
            setRotation(!isClockwise
                    ? (360 - ((current - startTime) * 1f % during) / during * 360)
                    : (((current - startTime) % during) * 1f / during * 360));
        } else {
            if (0 == preRotationTime || current - preRotationTime > intervalTime) {
                setRotation(preRotation += (isClockwise ? 1 : -1) * intervalDegree);
                preRotationTime = System.currentTimeMillis();
            }
        }

        invalidate();
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (autoPlay) {
            if (VISIBLE != visibility) {
                stop();
            } else {
                start();
            }
        } else {
            if (alive) {
                if (VISIBLE != visibility) {
                    stop();
                }
            }
        }
    }

    public void stop() {
        this.alive = false;
        setVisibility(GONE);
    }

    public boolean isRun() {
        return alive;

    }


    public void start() {
        this.alive = true;
        setVisibility(VISIBLE);
        invalidate();
    }

}
