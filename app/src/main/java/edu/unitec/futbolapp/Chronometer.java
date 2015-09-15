package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by nivx1 on 09/14/2015.
 */
public class Chronometer extends  Thread{
    private Date INIT_DATE;
    private long timePassed;
    private boolean Pause;

    private Context CONTEXTO;

    private TextView TXT;

    public boolean isPause() {
        return Pause;
    }

    public void setPause(boolean Pause) {
        this.Pause = Pause;
    }

    public void setAlive(boolean Alive) {
        this.Alive = Alive;
    }

    private boolean Alive;

    public Date getINIT_DATE() {
        return INIT_DATE;
    }

    public void setINIT_DATE(Date INIT_DATE) {
        this.INIT_DATE = INIT_DATE;
    }

    public String getTime() {

        StringBuilder SB = new StringBuilder();
        int min = (int)timePassed/(60);
        int sec = (int)(timePassed-(min*60));
        if (min < 10)
            SB.append(0);
        SB.append(min);
        SB.append(":");
        if (sec < 10)
            SB.append(0);
        SB.append(sec);
        return SB.toString();
    }




    public long getTimePassed() {
        return timePassed;
    }

    public String getDifference(long timePassed){
        timePassed = this.timePassed - timePassed;
        StringBuilder SB = new StringBuilder();
        int min = (int)timePassed/(60);
        int sec = (int)(timePassed-(min*60));
        if (min < 10)
            SB.append(0);
        SB.append(min);
        SB.append(":");
        if (sec < 10)
            SB.append(0);
        SB.append(sec);
        return SB.toString();
    }


    public Chronometer(Date INIT_DATE,TextView E, Context C) {
        this.INIT_DATE = INIT_DATE;
        timePassed = 0;
        Alive = true;
        Pause = false;
        TXT = E;
        CONTEXTO = C;
    }

    public void run() {
        while (Alive){
                ((Activity)CONTEXTO).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!Pause) {
                            timePassed += 1;
                            TXT.setText(getTime());
                        }
                    }

                });
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Chronometer.class.getName()).log(Level.SEVERE, null, ex);
            }
                //System.out.println(getTime());
            }
        }
    }
