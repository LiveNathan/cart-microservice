package cnLabs.cartmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CartMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartMicroserviceApplication.class, args);
	}

}
