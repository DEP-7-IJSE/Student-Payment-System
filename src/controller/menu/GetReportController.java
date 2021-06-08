package controller.menu;

import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetReportController {
    public Label lblMonth;

    public void initialize(){
        Date month = new Date();
        String format = new SimpleDateFormat("yyyy-MMMM").format(month);
        lblMonth.setText(format);
    }
}
