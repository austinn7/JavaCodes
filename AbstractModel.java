/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dmvcstopwatchfxmls21;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;



/**
 *
 * @author Austi
 */
public abstract class AbstractModel {
    
    protected PropertyChangeSupport propertyChangeSupport;
    
    protected double angleDeltaPerSeconds;
    protected double secondsElapsed;
    protected double tickTimeInSeconds;
    protected double averageLap;
    
    protected Timeline timeline;
    protected KeyFrame keyFrame;
    
    public AbstractModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
        tickTimeInSeconds = 0.01;
        angleDeltaPerSeconds = 6.0;
        secondsElapsed = 0.0;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue){
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    public void createTimer(){
        keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds * 1000), (ActionEvent event) -> {
            updateTimer();
            
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    protected void updateTimer(){
        
    }
    
    public boolean isRunning(){
        if(timeline != null){
            if(timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;
    }
    
    public void start(){
        timeline.play();
    }
    
    public void stop(){
        timeline.stop();
    }
    
    public double getSecondsElapsed(){
        return this.secondsElapsed;
    }
    
    public void setTimeline(Timeline timeline){
        this.timeline = timeline;
    }
    
    public Timeline getTimeline(){
        return this.timeline;
    }
    
    public double getTickTimeInSeconds(){
        return this.tickTimeInSeconds;
    }
    
    public void setTickTimeInSeconds(Double tickTimeInSeconds){
        this.tickTimeInSeconds = tickTimeInSeconds;
    }
    
    public void setKeyFrame(KeyFrame keyFrame){
        this.keyFrame = keyFrame;
    }
    
    public KeyFrame getKeyFrame(){
        return this.keyFrame;
    }
    
}
