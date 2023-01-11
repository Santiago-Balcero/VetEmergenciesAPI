package tc_sb_35_api.tc_sb_35_api.util;

import org.springframework.context.annotation.Configuration;

// Annotation allows this class to be autowired
@Configuration
public class LastActionToSpanish {

    // Method to translate into spanish(for frontend) english info from DB, columns "last_action_action"    
    public String lastActionToSpanish(String lastAction) {
        if (lastAction.equals("Creation")) {
            return "creación de nuevo registro";
        }
        else if (lastAction.equals("Data update")) {
            return "actualización de datos";
        }
        else if (lastAction.equals("Activate")) {
            return "activación";
        }
        else if (lastAction.equals("Deactivate")) {
            return "desactivación";
        }
        return "";
    }

}
