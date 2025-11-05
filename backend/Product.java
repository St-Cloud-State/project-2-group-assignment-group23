import java.util.ArrayList;
import java.util.List;

public class Product {
    // Contains a list of all products on hand.
    public static List<Product> master_product_list = new ArrayList<Product>();
    public static void add_to_mpl(String name, int qty, double price) {
        boolean found_it = false;
        for (Product product : master_product_list) {
            if (product.get_name() == name) {
                found_it = true;
                product.set_price(price);
                product.set_qty(qty);
                // Probs should check waitlists here
                break;
            }
        }

        if (found_it == false) {
            master_product_list.add(new Product(name, qty, price));
        }
    }

    // Ensures each uid is unique, would need to be stashed when a power cycle occurs
    private static int next_uid;
    private static int get_next_uid() {return next_uid++;}


    // Unique id for each product
    private int uid;
    public int get_uid() {return this.uid;}


    // Product name
    private String name;
    public String get_name() {return this.name;}
    public void set_name(String name) {this.name = name;}


    // Current qty of a product
    private int qty;
    public int get_qty() {return this.qty;}
    public void set_qty(int qty) {this.qty = qty;}


    // Price of a product
    private double price;
    public double get_price() {return this.price;}
    public void set_price(double price) {this.price = price;}


    // Used to prompt user for purchases
    public boolean prompt_user_to_purchase(Client client) {
        Context C = Context.get_instance();

        C.print(this.toString());
        C.print("      Do you want to purchase this? Y/N   ");

        String input = C.input().trim().toUpperCase();
        while (!input.equals("Y") && !input.equals("N")) {
            C.print("Please enter Y or N: ");
            input = C.input().trim().toUpperCase();
        }

        if (input.equals("Y")) {
            for (Product p : master_product_list) {
                if (this.uid == p.get_uid()) {
                    int qty_on_hand = p.get_qty();
                    int qty_purchasing = this.get_qty();

                    if (qty_purchasing > qty_on_hand) { // If we don't have enough
                        p.set_qty(0);                   // Set our on hand qty to 0
                        this.set_qty(qty_on_hand);      // Set the qty being purchased to  whatever we hand on hand
                        client.add_to_waitlist(p, qty_purchasing - qty_on_hand);  // Add the client to our list of people who have this on order
                    } else {
                        p.set_qty(p.get_qty() - this.qty);  // Update the qty on hand
                    }

                    return true;
                }
            }
        }
        return false;
    }


    // Primary Constructor
    public Product(String name, int qty, double price) {
        this.uid = get_next_uid();
        this.name = name;
        this.qty = qty;
        this.price = price;
    }


    // Copy Constructor
    public Product(Product product) {
        this.uid = product.get_uid();
        this.name = product.get_name();
        this.qty = product.get_qty();
        this.price = product.get_price();
    }


    // Operator overload for list comparison operations
    public boolean equals(Product other) {
        if (this.uid == other.get_uid()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.uid);
    }

    @Override
    public String toString() {
        return "Product UID-" + this.uid + "  Name-" + this.name + "  Qty-" + this.qty + "  Price-" + this.price;
    }
}