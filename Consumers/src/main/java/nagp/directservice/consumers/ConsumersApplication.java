package nagp.directservice.consumers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumersApplication.class, args);
	}

	@Bean(name = "restTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
