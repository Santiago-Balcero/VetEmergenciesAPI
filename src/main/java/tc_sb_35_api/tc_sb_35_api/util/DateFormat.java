package tc_sb_35_api.tc_sb_35_api.util;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;

// Annotation allows class to be autowired
@Configuration
public class DateFormat {
    
    // Method receives a LocalDateTime format (2022-12-12T08:15:04) and returns a string ("2022-12-12 08:15:04") for frontend
    public String dateFormat(LocalDateTime dateTime) {
        return String.join(" ", dateTime.toString().split("T"));
    }

}
