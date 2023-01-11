package tc_sb_35_api.tc_sb_35_api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TcSb35ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcSb35ApiApplication.class, args);
	}

	// Makes ModelMapper avaiable to be Autowired in all the app
	@Bean
	public ModelMapper modelMapper() {
    	return new ModelMapper();
	}

}
