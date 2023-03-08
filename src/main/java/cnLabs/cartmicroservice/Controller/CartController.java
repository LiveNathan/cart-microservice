package cnLabs.cartmicroservice.Controller;

import cnLabs.cartmicroservice.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.getCartByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addNewCartItem(@RequestParam("item-id") Long itemId, @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.addCartItem(itemId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable("cartItemId") Long cartItemId,
                                            @PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(cartService.removeCartItem(cartItemId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-item/{userId}/{cartItemId}")
    public ResponseEntity<?> removeCartItemCompletely(@PathVariable("cartItemId") Long cartItemId,
                                            @PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(cartService.removeCartItemCompletely(cartItemId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteCart(@PathVariable("userId") Long userId) {
        try {
            cartService.deleteCartByUserId(userId);
            return ResponseEntity.ok().body("User " + userId + " cart deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/{cartItemId}")
    public ResponseEntity<?> updateItemAmount(@PathVariable("userId") Long userId,
                                              @PathVariable("cartItemId") Long cartItemId,
                                              @RequestParam("amount") Integer amount) {
        try {
            return ResponseEntity.ok(cartService.updateAmount(userId, cartItemId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
