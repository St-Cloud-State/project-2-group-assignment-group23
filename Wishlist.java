import java.util.List;
import java.util.ArrayList;

public class Wishlist {
    // List of products
    private List<Product> product_list;
    public void add_product(Product product) {this.product_list.add(product);}
    public void remove_product(Product product) {this.product_list.remove(product);}
    protected List<Product> get_product_list() {return product_list;}

    // Empty Constructor
    public Wishlist() {
        this.product_list = new ArrayList<Product>();
    }

    // Primary Constructor
    public Wishlist(Product product) {
        this.product_list = new ArrayList<Product>();
        this.product_list.add(product);
    }

    // Copy Constructor
    public Wishlist(Wishlist list) {
        this.product_list = new ArrayList<Product>();

        for (Product product : list.get_product_list()) {
            this.product_list.add(new Product(product));
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (Product p : product_list) {
            ret += p;
            ret+= "\n";
        }
        return ret;
    }
}