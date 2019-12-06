package lathigara.harsh.flipkart.Models;
// my Cart Fragment //
public class CartItemModel {
    public static  final int CART_ITEM = 0;
    public static  final int TOTAL_AMOUNT = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //cart Item //
    private String productId;
    private String productImage;
    private String productTitle;
    private Long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private Long ProductQuantity;
    private Long offersApplied;
    private Long coupensApplied;
    private boolean inStock;

    public CartItemModel(int type,String productId ,String productImage, String productTitle, Long freeCoupens, String productPrice, String cuttedPrice, Long productQuantity, Long offersApplied, Long coupensApplied,boolean inStock) {
        this.type = type;
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        ProductQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
        this.inStock = inStock;
    }

    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }



    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public Long getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        ProductQuantity = productQuantity;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(Long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

//cart Item //

    /// cart total //
   /* private int totalItems;
    private String totalItemPrice;
    private String deliveryPice;
    private String savedAmount;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemPrice, String deliveryPice,String totalAmount, String savedAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPice = deliveryPice;
        this.savedAmount = savedAmount;
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPice() {
        return deliveryPice;
    }

    public void setDeliveryPice(String deliveryPice) {
        this.deliveryPice = deliveryPice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
/// cart total //*/
        // cart total changes part 70 //
    public CartItemModel(int type) {
        this.type = type;
    }

    // cart total changes part 70 //
}
