/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Austi
 */
public class Amnd7dMyVisualizerS21 implements Visualizer {
    private final String name;
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage;
    
    private String vizPaneInitialStyle;
    
    private Double width;
    private Double height;
    
    private Double bandWidth;
    private Double bandHeight;
    private Double halfBandHeight;
    
    private final Double startHue;
    
    private Rectangle[] rectangles;
    
    public Amnd7dMyVisualizerS21(){
        name = "Austin's Audio Viz";
        bandHeightPercentage = 2.0;
        vizPaneInitialStyle = "";
        width = 0.0;
        height = 0.0;
        bandWidth = 0.0;
        bandHeight = 0.0;
        halfBandHeight = 0.0;
        startHue = 260.0;
    }
    
    @Override
    public String getVizName() {
        return name;
    }
    
    @Override
    public void setup(Integer numBands, AnchorPane vizPane){
        destroy();
        
        vizPaneInitialStyle = vizPane.getStyle();
        
        this.numOfBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        
        rectangles = new Rectangle[numBands];
        
        for (int i = 0; i < numBands; i++){
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            rectangle.setWidth(bandWidth);
            rectangle.setX((bandWidth / 2 + bandWidth * i) - 10);
            rectangle.setY(0);
            vizPane.getChildren().add(rectangle);
            rectangles[i] = rectangle;
            System.out.println("rectangle +" + i);
        }
        
        vizPane.setStyle("-fx-background-color: black");
    }
    
    @Override
    public void destroy(){
        if (rectangles != null) {
            for (Rectangle rectangle : rectangles) {
                vizPane.getChildren().remove(rectangle);
            }
            rectangles = null;
            vizPane.setClip(null);
        }
    }
    
    @Override
    public void visualize(double timestamp, double lenght, float[] magnitudes, float[] phases){
        if (rectangles == null){
            return;
        }
        
        Integer num = min(rectangles.length, magnitudes.length);
        
        for (int i = 0; i < num; i++){
            rectangles[i].setHeight((((60.0 + magnitudes[i])/60.0) * halfBandHeight));
            rectangles[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
        }
        
        int j = 0; 
        for (int i = num - 1; i >= num / 2; i--) {
            rectangles[i].setHeight(((60.0 + magnitudes[j])/60.0) * halfBandHeight);
            rectangles[i].setFill(Color.hsb(startHue - (magnitudes[j] * -6.0), 1.0, 1.0, 1.0));
            j++;
        }
    }
    
}
