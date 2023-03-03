package cnLabs.cartmicroservice.Repo;

import cnLabs.cartmicroservice.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}