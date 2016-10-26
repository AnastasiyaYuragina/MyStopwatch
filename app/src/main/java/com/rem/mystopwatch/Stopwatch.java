package com.rem.mystopwatch;

import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by anastasiyayuragina on 7/20/16.
 *
 */
class Stopwatch {

    private long startTime;
    private long millis;
    private int seconds;
    private int minutes;
    private int hours;
    private TextView view;
    private Handler handler;
    private boolean isWork;

    Stopwatch(TextView view) {
        this.view = view;
        handler = new Handler();
        isWork = false;
    }

    void startStopwatch() {
        if (!isWorkStopwatch()) {
            startTime = System.currentTimeMillis();
            millis = 0;
            seconds = 0;
            minutes = 0;
            hours = 0;
            isWork = true;
            handler.post(showTime);
        }
    }

    void stopStopwatch() {
        handler.removeCallbacks(showTime);
        isWork = false;
    }

    public String circleStopwatch() {
        String temp = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (millis %= 10));
        handler.removeCallbacks(showTime);
        startTime = System.currentTimeMillis();
        millis = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;

        if (isWork) {
            isWork = false;
            startStopwatch();
        }

        return temp;
    }

    void clearStopwatch() {
        handler.removeCallbacks(showTime);
        isWork = false;
        millis = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;

        view.setText(String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (millis %= 10)));
    }

    private boolean isWorkStopwatch() {
        return isWork;
    }

    private Runnable showTime = new Runnable() {
        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            hours = minutes / 60;
            seconds %= 60;

            view.setText(String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (millis %= 10)));
            handler.postDelayed(showTime, 10);
        }
    };

}
