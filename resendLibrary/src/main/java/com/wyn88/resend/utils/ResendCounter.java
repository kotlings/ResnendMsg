package com.wyn88.resend.utils;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

/**
 * 计数器
 *
 * @version 0.1
 * <p>
 * Created by zxj on 2/8/2018.
 */

public final class ResendCounter {

    private final Listener listener;

    private Handler handler;

    private int step;
    private int start;
    private int end;
    private int interval;
    private int current;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            next();
        }
    };

    private ResendCounter(@NonNull Handler handler, int start, int end, int step, int interval,
                          @NonNull Listener listener) {
        this.handler = handler;
        this.listener = listener;
        this.start = start;
        this.step = step;
        this.end = end;
        this.interval = interval;
        this.current = start;

        if ((start - end) * step >= 0) {
            listener.complete();
            // throw new IllegalArgumentException("start: " + start + "; end: " + end + "; step: " + step);
        }
    }

    public static ResendCounter create(int start, int end, int step, int interval,
                                       @NonNull Listener listener) {
        return new ResendCounter(new Handler(Looper.getMainLooper()), start, end, step, interval, listener);
    }

    public static ResendCounter create(@NonNull Handler handler, int start, int end, int step, int interval,
                                       @NonNull Listener listener) {
        return new ResendCounter(handler, start, end, step, interval, listener);
    }

    public void start() {
        next();
    }

    private void next() {
        if ((current - end) * step < 0) {
            if (current != start) {
                listener.update(current);
                current += step;
            } else {
                current += step;
            }
            handler.postDelayed(runnable, interval);
        } else {
            listener.complete();
        }
    }

    public void stop() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    public interface Listener {

        void update(int count);

        void complete();

    }

}
