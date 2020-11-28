package com.finalproject.ecommercestore.model.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    @Column(name = "shipping_weight")
    private double shippingWeight;
    @Column(name = "in_stock")
    private Boolean inStock = false;

    @Transient
    private MultipartFile productImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"))
    private List<Category> productCategories = new ArrayList<>();

    public Product() {
    }

  /*  public Product(String name, BigDecimal price, String description, double shippingWeight, boolean inStock, MultipartFile productImage, CartItem cartItem, List<Category> productCategories) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.shippingWeight = shippingWeight;
        this.inStock = inStock;
        this.productImage = productImage;
        this.cartItem = cartItem;
        this.productCategories = productCategories;
    }
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public List<Category> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Category> productCategories) {
        this.productCategories = productCategories;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
