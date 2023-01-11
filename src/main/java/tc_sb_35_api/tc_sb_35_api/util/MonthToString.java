package tc_sb_35_api.tc_sb_35_api.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MonthToString {
    
    public String monthToString(int month) {
        if(month == 1) {
            return "Enero";
        }
        else if(month == 2) {
            return "Febrero";
        }
        else if(month == 3) {
            return "Marzo";
        }
        else if(month == 4) {
            return "Abril";
        }
        else if(month == 5) {
            return "Mayo";
        }
        else if(month == 6) {
            return "Junio";
        }
        else if(month == 7) {
            return "Julio";
        }
        else if(month == 8) {
            return "Agosto";
        }
        else if(month == 9) {
            return "Septiembre";
        }
        else if(month == 10) {
            return "Octubre";
        }
        else if(month == 11) {
            return "Noviembre";
        }
        else if(month == 12) {
            return "Diciembre";
        }
        return "";
    }

}
