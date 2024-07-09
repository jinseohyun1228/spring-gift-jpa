package gift.main.entity;

import jakarta.persistence.*;

@Entity
public class WishlistProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  Long productId;
    @Column(nullable = false)
    private  Long userId;

    public WishlistProduct(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public WishlistProduct() {

    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}