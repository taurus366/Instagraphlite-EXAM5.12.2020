package softuni.exam.instagraphlite.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.instagraphlite.util.ValidatorUtil;
import softuni.exam.instagraphlite.util.ValidatorUtilImpl;
import softuni.exam.instagraphlite.util.XmlParser;
import softuni.exam.instagraphlite.util.XmlParserImpl;

import javax.validation.Validation;
import javax.validation.Validator;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }


    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }

}
