package romain.com.multigames.utils;

import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimerUtils {

    private static final int TIME_BEFORE_FIRST_EXECUTION = 0;
    private static final int DELAY_BETWEEN_TRIGGER = 1000;
    private int timerText = 10;
    private TextView textViewTimer;
    private final Timer timer = new Timer();

    protected TimerUtils(TextView textViewTimer) {
        this.textViewTimer = textViewTimer;
    }

    public void startTimer(){
        final Handler handler = new Handler();

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        TimerUtils.this.timerText--;
                        if(TimerUtils.this.timerText == 0){
                            TimerUtils.this.timer.cancel();
                            endGame();
                        }
                        TimerUtils.this.textViewTimer.setText(String.valueOf(TimerUtils.this.timerText));
                    }
                });
            }
        };
        timer.schedule(timerTask, TIME_BEFORE_FIRST_EXECUTION, DELAY_BETWEEN_TRIGGER);
    }

    protected abstract void endGame();


    public void stopTimer(){
        TimerUtils.this.timer.cancel();
    }
}
