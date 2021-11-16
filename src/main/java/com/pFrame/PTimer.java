package com.pFrame;

import log.Log;

public class PTimer implements Runnable {
    private int time;
    private boolean repeat;
    private PTimerTask tasker;
    private boolean stop;

    public void schedule(PTimerTask task, boolean repeat, int time) {
        this.tasker = task;
        this.repeat = repeat;
        this.time = time;
    }

    public PTimer() {
        this.time = 0;
        this.repeat = false;
        this.tasker = null;
        this.stop = false;
    }

    @Override
    public void run() {
        if (this.tasker == null || this.time <= 0) {
            Log.ErrorLog(this, String.format("Invalid args: %s %d", tasker, this.time));
        } else {
            if (repeat == true) {
                while (true && this.stop == false) {
                    try {
                        Thread.sleep(time);
                        this.tasker.doTask();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    Thread.sleep(this.time);
                    this.tasker.doTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        this.stop = true;
    }
}
