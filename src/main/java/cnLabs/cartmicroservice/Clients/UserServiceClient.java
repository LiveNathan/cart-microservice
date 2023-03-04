package cnLabs.cartmicroservice.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public void reportCartEvent(Long userId) {
        restTemplate.postForEntity("http://USER-MICROSERVICE/user/events/" + userId, null, String.class);  // Removed the ResponseEntity because I'm not sure what to do with it.
    }
}
