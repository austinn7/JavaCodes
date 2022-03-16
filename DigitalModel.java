/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dmvcstopwatchfxmls21;

/**
 *
 * @author Austin Neumann
 */
public class DigitalModel extends AbstractModel{
    
    public double lapTime;
    public double timerValue;
    
    public DigitalModel(){
        tickTimeInSeconds = 0.01; 
        lapTime = 0.0;
        timerValue = 60.0;
    }
    
    @Override
    protected void updateTimer(){
        super.updateTimer();
        updateDigital();
    }
    
    public void updateDigital(){
        Double oldTime = secondsElapsed;
        secondsElapsed += tickTimeInSeconds;
        lapTime += tickTimeInSeconds;
        timerValue -= tickTimeInSeconds;
        firePropertyChange("Digital", oldTime, secondsElapsed);
    }
    
    public void resetDigital(){
        secondsElapsed = 0.0;
        lapTime = 0.0;
        timerValue = 60.0;
    }
    
    public double getLapTime(){
        return lapTime;
    }
    
    public void resetLapTime(){
        lapTime = 0;
    }
    
}
