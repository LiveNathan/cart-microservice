package cnLabs.cartmicroservice.Service;

import cnLabs.cartmicroservice.Model.Cart;
import cnLabs.cartmicroservice.Model.CartItem;
import cnLabs.cartmicroservice.Repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = Cart.builder().userId(userId).build();
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    @Transactional
    public Cart addCartItem(Long itemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(itemId)) {
                item.setAmount(item.getAmount() + 1);
                return cartRepository.save(cart);
            }
        }
        cart.addCartItem(itemId);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeCartItem(Long cartItemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.removeCartItem(cartItemId);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateAmount(Long userId, Long cartItemId, Integer amount) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.getItems().stream().filter(i -> i.getId().compareTo(cartItemId) == 0)
                .findFirst().ifPresent(cartItem -> cartItem.setAmount(amount));
        return cart;
    }
}