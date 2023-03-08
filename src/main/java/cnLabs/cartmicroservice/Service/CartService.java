package cnLabs.cartmicroservice.Service;

import cnLabs.cartmicroservice.Clients.ItemServiceClient;
import cnLabs.cartmicroservice.Clients.UserServiceClient;
import cnLabs.cartmicroservice.Model.Cart;
import cnLabs.cartmicroservice.Model.CartItem;
import cnLabs.cartmicroservice.Repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    ItemServiceClient itemServiceClient;

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
        if (cart == null) {
            cart = Cart.builder().userId(userId).items(new ArrayList<>()).build();
            cart = cartRepository.save(cart);
        }

        Optional<CartItem> cartItemOptional = Optional.ofNullable(itemServiceClient.getItemById(itemId));
        if (cartItemOptional.isPresent()) {
            for (CartItem item : cart.getItems()) {
                if (item.getItemId().equals(itemId)) {
                    item.setAmount(item.getAmount() + 1);
                    return cartRepository.save(cart);
                }
            }
            cart.addCartItem(itemId);
            cart = cartRepository.save(cart);
        }

        userServiceClient.reportCartEvent(userId);
        return cart;
    }

    @Transactional
    public Cart removeCartItem(Long itemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(itemId)) {
                item.setAmount(item.getAmount() - 1);
                return cartRepository.save(cart);
            }
        }

        cart.removeCartItem(itemId);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeCartItemCompletely(Long itemId, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(itemId)) {
                item.setAmount(0);
            }
        }

        cart.removeCartItem(itemId);
        cart = cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public void deleteCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cartRepository.delete(cart);
    }


    @Transactional
    public Cart updateAmount(Long userId, Long cartItemId, Integer amount) {
        Cart cart = cartRepository.findByUserId(userId);
//        cart.getItems().stream().filter(i -> i.getId().compareTo(cartItemId) == 0)  // I don't understand why this didn't work.
//                .findFirst().ifPresent(cartItem -> cartItem.setAmount(amount));
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(cartItemId)) {
                item.setAmount(amount);
            }
        }
        cart = cartRepository.save(cart);
        return cart;
    }
}