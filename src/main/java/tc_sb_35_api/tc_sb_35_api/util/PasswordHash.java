package tc_sb_35_api.tc_sb_35_api.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordHash {
    
    // Returns sha256 hashed string using apache.commons.codec dependency
    // See version and details in gradle.build file
    public String passwordHash(String plainText) {
        return DigestUtils.sha256Hex(plainText);
    }

}
