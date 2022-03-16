/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dmvcstopwatchfxmls21;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Austi
 */
public class Controller implements Initializable, PropertyChangeListener {
    
    @FXML
    private ImageView handImage;
    
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private NumberAxis lineChartYAxis;
    @FXML
    private CategoryAxis lineChartXAxis;
    
    @FXML
    private AreaChart<?, ?> areaChart;
    @FXML
    private NumberAxis areaChartYAxis;
    @FXML
    private CategoryAxis areaChartXAxis;
    
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    
    @FXML
    private Text currentTime;
    @FXML
    private Text timerText;
    @FXML
    private Text lapText;
    @FXML
    private Text averageLap;
    
    private DigitalModel digitalModel;
    private AnalogModel analogModel;
    
    private int lapCounter;
    private XYChart.Series series = new XYChart.Series();
    private XYChart.Series average = new XYChart.Series();
    private DecimalFormat secondsFormat = new DecimalFormat("00.00");
    private DecimalFormat minutesFormat = new DecimalFormat("00");
    private Alert timerAlert;
    
    @Override
    public void propertyChange(PropertyChangeEvent event){
        if(event.getPropertyName().equals("Analog")){
            handImage.setRotate(Double.parseDouble(event.getNewValue().toString()));
        }
        else if(event.getPropertyName().equals("Digital")){
            double seconds = ((double)event.getOldValue()%60);
            double minutes = ((double)event.getOldValue()%3600)/120;
            currentTime.setText(minutesFormat.format(minutes)+ ":" + secondsFormat.format(seconds));
            if(digitalModel.timerValue > 0){
                timerText.setText(String.format("Timer %2.2f", digitalModel.timerValue));
            }
            else{
                timerText.setText("Times up!");
            }
            
        }
    }
    
    public void createAlert(){
        timerAlert = new Alert(Alert.AlertType.INFORMATION, "Please Input a starting Timer value.");
    }
    
    public boolean isRunning(){
        return digitalModel.isRunning() && analogModel.isRunning();
    }
    
    @FXML
    public void handleStart(ActionEvent event){
        if(isRunning()){
            analogModel.stop();
            digitalModel.stop();
            startButton.setText("Start");
            resetButton.setText("Reset");
        }
        else{
            startButton.setText("Stop");
            resetButton.setText("Record");
            analogModel.start();
            digitalModel.start();
        }
    }
    
    @FXML
    public void handleReset(ActionEvent event){
        if(isRunning()){
            lapCounter++;
            lapText.setText("Lap " + lapCounter + " " + minutesFormat.format(digitalModel.getLapTime()/60)+ ":" + secondsFormat.format(digitalModel.getLapTime()%60));
            averageLap.setText("Average Lap Time" + minutesFormat.format(digitalModel.getSecondsElapsed()/60/lapCounter) + ":" + secondsFormat.format((digitalModel.getSecondsElapsed())/lapCounter));
            series.getData().add(new XYChart.Data(Integer.toString(lapCounter), digitalModel.getLapTime()));
            average.getData().add(new XYChart.Data(Integer.toString(lapCounter), (digitalModel.getSecondsElapsed())/lapCounter));
            digitalModel.resetLapTime();
        }
        else{
            analogModel.resetAnalog();
            digitalModel.resetDigital();
            currentTime.setText("00:00.00");
            lapCounter = 0;
            lapText.setText("Lap 0 00:00.00");
            averageLap.setText("Average Lap Time 00:00.00");
            series.getData().clear();
            average.getData().clear();
            timerText.setText("60.00");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        digitalModel = new DigitalModel();
        analogModel = new AnalogModel();
        
        digitalModel.createTimer();
        analogModel.createTimer();
        
        lineChart.getData().add(series);
        areaChart.getData().add(average);
        
//        createAlert();
//        timerAlert.show();
        // TODO
        digitalModel.addPropertyChangeListener(this);
        analogModel.addPropertyChangeListener(this);
    }    
    

}
