package cnLabs.cartmicroservice.Model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> items;

    public void addCartItem(Long itemId) {
        items.add(CartItem.builder().itemId(itemId).amount(1).build());
    }

    public void removeCartItem(Long cartItemId) {
        items.removeIf(ci -> ci.getItemId().equals(cartItemId));
    }
}