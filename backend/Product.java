import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Product {
    // Contains a list of all products on hand.
    public static List<Product> master_product_list = new ArrayList<Product>();
    public static void add_to_mpl(String name, int qty, double price) {
        boolean found_it = false;

        if (name.startsWith("\"") && name.endsWith("\"") && name.length() >= 2) {
            name = name.substring(1, name.length() - 1);
        }

        for (Product product : master_product_list) {
            if (product.get_name().equals(name)) {
                found_it = true;
                product.set_price(price);
                qty = product.check_waitlist(qty);
                product.set_qty(qty + product.get_qty());
                break;
            }
        }

        if (found_it == false) {
            master_product_list.add(new Product(name, qty, price));
        }
    }

    // Ensures each uid is unique, would need to be stashed when a power cycle occurs
    private static int next_uid = 1;
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

        String input = C.input("      Do you want to purchase this? Y/N   ").trim().toUpperCase();
        while (!input.equals("Y") && !input.equals("N")) {
            input = C.input("Please enter Y or N: ").trim().toUpperCase();
        }

        if (input.equals("Y")) {
            for (Product p : master_product_list) {
                if (this.uid == p.get_uid()) {
                    int num_wanted = this.qty;
                    int num_on_hand = p.get_qty();

                    if (num_on_hand >= num_wanted) {
                        p.set_qty(num_on_hand - num_wanted);
                    } else {
                        p.set_qty(0);
                        p.add_to_waitlist(client, num_wanted - num_on_hand);
                        this.qty = num_on_hand;
                    }

                    return true;
                }
            }
        }
        return false;
    }


    // Keeps track of clients waiting for this product
    private Map<Client, Integer> waitlist;
    public Map<Client, Integer> get_waitlist() {
         return new LinkedHashMap<>(this.waitlist); // Returns a copy so that no one can modify
    }
    private void add_to_waitlist(Client client, int qty) {
        this.waitlist.put(client, this.waitlist.getOrDefault(client, 0) + qty);
    }
    private int check_waitlist(int qty) {
        int ret = qty;

        Client c = this.waitlist.keySet().stream().findFirst().orElse(null);
        while (c != null && ret > 0) {
            int buy_this_many = this.waitlist.get(c);
            this.waitlist.remove(c);

            if (buy_this_many > ret) {
                this.waitlist.put(c, buy_this_many - ret);
                buy_this_many = ret;
                ret = 0;
            } else {
                ret -= buy_this_many;
            }

            c.purchase_one(this, buy_this_many);

            c = this.waitlist.keySet().stream().findFirst().orElse(null);
        }

        return ret;
    }


    // Primary Constructor
    public Product(String name, int qty, double price) {
        this.uid = get_next_uid();
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.waitlist = new LinkedHashMap<Client, Integer>(); // Useing LinkedHashMap to maintain fifo
    }


    // Copy Constructor
    public Product(Product product) {
        this.uid = product.get_uid();
        this.name = product.get_name();
        this.qty = product.get_qty();
        this.price = product.get_price();
        this.waitlist = product.get_waitlist();
    }


    // // Operator overload for list comparison operations
    // public boolean equals(Product other) {
    //     if (this.uid == other.get_uid()) {
    //         return true;
    //     }
    //     return false;
    // }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            Product other = (Product)obj;
            return this.uid == other.get_uid();
        }
        
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