package dailynailheroku.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import dailynailheroku.handlers.CustomAccessDeniedHandler;
import dailynailheroku.models.validators.ServiceLayerValidationUtil;
import dailynailheroku.models.validators.ServiceLayerValidationUtilImpl;
import dailynailheroku.utils.FileIOUtil;
import dailynailheroku.utils.FileIOUtilImpl;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ServiceLayerValidationUtil serviceLayerValidationUtil() {
        return new ServiceLayerValidationUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public FileIOUtil fileIOUtil() {
        return new FileIOUtilImpl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

}
