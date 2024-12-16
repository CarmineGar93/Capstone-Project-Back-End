package CarmineGargiulo.Capstone_Project_Back_End.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary uploader(@Value("${cloudinary.name}") String cloudname,
                               @Value("${cloudinary.key}") String cloudapiKey,
                               @Value("${cloudinary.secret}") String cloudSecret) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudname);
        config.put("api_key", cloudapiKey);
        config.put("api_secret", cloudSecret);
        return new Cloudinary(config);
    }
}
