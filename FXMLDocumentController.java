/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfxmlcpumonitors21;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 *
 * @author Austi
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private AreaChart<?, ?> recordedCPUUsageChart;
    @FXML
    private LineChart<?, ?> meanCPULoadChart;
    @FXML
    private Label peakUsage;
    @FXML
    private Label meanUsage;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    
    private static double cpu = 0;
    private static double max = 0;
    public double mean = 0;
    public int cycles = 0;
    public double sum = 0;
    public boolean isRunning = false;

    
    @FXML
    private void handleStart(ActionEvent event) {

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent) -> {

                if(getCPUUsage() > max)
                {
                    max = getCPUUsage();
                    peakUsage.setText(String.format("%.2f", (getCPUUsage()*100)) + "%");
                }
                cpu = this.getCPUUsage();
                System.out.println("CPU: " + cpu);
                
                sum += getCPUUsage();
                cycles++;
                meanUsage.setText(String.format("%.2f", (getMean()*100)) + "%");
            }));
            if(isRunning == false){
                startButton.setText("Stop");
                resetButton.setText("Record");
                timeline.setCycleCount(20);
                timeline.play();
                isRunning = true;
            }
            else{
                startButton.setText("Start");
                resetButton.setText("Reset");
                timeline.stop();
                isRunning = false;
            }
    }
    
    @FXML
    private void handleReset(ActionEvent event){
        if(isRunning == false)
        {
            meanUsage.setText("00.00%");
            peakUsage.setText("00.00%");
        }
        else 
        {
            
        }
    }
    
    public double getCPUUsage() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        double value = 0;
        
        for(Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            
            if (method.getName().startsWith("getSystemCpuLoad") && Modifier.isPublic(method.getModifiers())) {
                try {
                    value = (double) method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = 0;
                }
                return value;
            }
        }
        return value;
    }
    
    public double getMean(){
        mean = sum/cycles;
        return mean;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
