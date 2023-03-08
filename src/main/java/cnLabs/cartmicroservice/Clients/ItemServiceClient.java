package cnLabs.cartmicroservice.Clients;

import cnLabs.cartmicroservice.Model.Cart;
import cnLabs.cartmicroservice.Model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ItemServiceClient {

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public CartItem getItemById(Long itemId) {
        ResponseEntity<CartItem> response = restTemplate.getForEntity("http://ITEM-MICROSERVICE/item/" + itemId, CartItem.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
