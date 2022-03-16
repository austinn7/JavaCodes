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
public class AnalogModel extends AbstractModel{
    
    private double rotation;
    
    public AnalogModel(){
        rotation = 0.0;
        secondsElapsed = 0.0;
        tickTimeInSeconds = 0.01;
    }
    
    @Override
    protected void updateTimer(){
        super.updateTimer();
        updateAnalog();
    }
    
    public void updateAnalog(){
        double oldAngle = rotation;
        secondsElapsed += tickTimeInSeconds;
        rotation = getRotation();
        firePropertyChange("Analog", oldAngle, rotation);
    }
    private double getRotation(){
        return secondsElapsed * angleDeltaPerSeconds;
    }
    
    public void resetAnalog(){
        double oldAngle = rotation;
        rotation = 0;
        secondsElapsed = 0;
        firePropertyChange("Analog", oldAngle, rotation);
    }
}
