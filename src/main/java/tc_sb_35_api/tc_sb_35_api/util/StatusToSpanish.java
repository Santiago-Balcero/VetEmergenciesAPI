package tc_sb_35_api.tc_sb_35_api.util;

import org.springframework.context.annotation.Configuration;

// Annotation allows this class to be autowired
@Configuration
public class StatusToSpanish {

    // Method to translate into spanish(for frontend) english info from DB, column "emergency_status"    
    public String statusToSpanish(String status) {
        if (status.equals("Pending")) {
            return "PENDIENTE DE APROBACIÓN";
        }
        else if (status.equals("Rejected")) {
            return "RECHAZADA POR LÍMITE DE COBERTURA ALCANZADO O SUSCRIPCIÓN INACTIVA";
        }
        else if (status.equals("Approved")) {
            return "APROBADA";
        }
        else if (status.equals("Confirmed")) {
            return "CONFIRMADA - ATENCIÓN RECIBIDA";
        }
        return "";
    }

}
