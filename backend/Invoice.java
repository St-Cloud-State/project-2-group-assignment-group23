import java.time.LocalDateTime;

public class Invoice {
    // The cost of everything in this.product_list
    private double total_price;
    public double get_total_price() {return this.total_price;}

    private LocalDateTime transaction_date;
    public LocalDateTime get_transaction_date() {return this.transaction_date;}

    // Reusing Wishlist to store the products of an invoice
    private Wishlist product_list;
    // Our list getter will return a copy so that this remains const
    private Wishlist get_product_list() {return new Wishlist(this.product_list);}

    // Primary Constructor
    public Invoice(Wishlist list) {
        // Copy the list
        this.product_list = new Wishlist(list);
        this.transaction_date = LocalDateTime.now();

        // Add price for everything in the list
        for (Product product : this.product_list.get_product_list()) {
            this.total_price += product.get_price() * product.get_qty();
        }
    }

    // Copy Constructor
    public Invoice(Invoice invoice) {
        this.product_list = new Wishlist(invoice.get_product_list());
        this.transaction_date = invoice.get_transaction_date();

        // Add price for everything in the list
        for (Product product : this.product_list.get_product_list()) {
            this.total_price += product.get_price() * product.get_qty();
        }

        // If it's not in the requirements docs its up to me and I support the unethical business practices described in the movie Office Space so I'm gonna drop the fraction here and let someone else handle it.
        this.total_price = Math.floor(this.total_price * 100) /100;
    }


    @Override
    public String toString() {
        String ret = "Invoice total_price: $" + this.total_price + "    Transaction Date: " + this.transaction_date;
        for (Product product : product_list.get_product_list()) {
            ret += "\n" +  "    " + product;
        }
        return ret;
    }
}