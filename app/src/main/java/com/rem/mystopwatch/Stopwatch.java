package com.rem.mystopwatch;

import android.os.Handler;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public boolean isWork() {
        return isWork;
    }

    Stopwatch(TextView view) {
        this.view = view;
        handler = new Handler();
        isWork = false;
    }

    void startStopwatch() {
        if (!isWorkStopwatch()) {
            startTime = System.currentTimeMillis();
            millis = seconds = minutes = hours = 0;
            isWork = true;
            handler.post(showTime);
        }
    }

    void stopStopwatch() {
        handler.removeCallbacks(showTime);
        isWork = false;
    }

    String circleStopwatch() {
        String temp = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (millis %= 100));
        handler.removeCallbacks(showTime);
        startTime = System.currentTimeMillis();
        millis = seconds = minutes = hours = 0;

        if (isWork) {
            isWork = false;
            startStopwatch();
        }

        return temp;
    }

    void clearStopwatch() {
        handler.removeCallbacks(showTime);
        isWork = false;
        millis = seconds = minutes = hours = 0;

        view.setText(String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (millis %= 100)));
    }

    private boolean isWorkStopwatch() {
        return isWork;
    }

    private Runnable showTime = new Runnable() {
        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            Date time = new Date(millis);
            Calendar times = new GregorianCalendar();
            times.setTime(time);
            seconds = times.get(Calendar.SECOND);
            minutes = times.get(Calendar.MINUTE);
            hours = times.get(Calendar.HOUR) - 3;

            view.setText(String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (millis %= 100)));
            handler.postDelayed(showTime, 80);
        }
    };

}
