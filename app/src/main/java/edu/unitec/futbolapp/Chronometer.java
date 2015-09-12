package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/06/2015.
 */
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nivx1
 */
public class Chronometer extends Thread {
    private Date INIT_DATE;
    private long timePassed;
    private boolean Pause;

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


    public Chronometer(Date INIT_DATE) {
        this.INIT_DATE = INIT_DATE;
        timePassed = 0;
        Alive = true;
        Pause = false;
    }




    @Override
    public void run() {
        while (Alive){
            if (!Pause){
                timePassed += 1;
                //System.out.println(getTime());
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Chronometer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
