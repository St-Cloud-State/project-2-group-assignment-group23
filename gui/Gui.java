import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Gui {
    // Program Entry Point
    public static void main(String[] args) {
        // Create the states ORDER MATTERS!!!
        State states[] = {
            new Login_State(),      // S0
            new Manager_State(),    // S1
            new Clerk_State(),      // S2
            new Client_State()      // S3
        };

        // Adds products to make my testing easier
        if (true) {
            try {
                File path = new File(System.getProperty("user.dir"), "products.csv");
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                String in = br.readLine();
                br.close();
                String[] arr = in.split(",");
                for (int i = 0; i < arr.length/3; i++) {
                    String name = arr[i++];
                    int qty = Integer.parseInt(arr[i++]);
                    double price = Double.parseDouble(arr[i]);  // Dont iterate here, loop will do it.
                    Product.add_to_mpl(name, qty, price);
                }
            } catch (Exception e) {}
        }


        // Initialize and run the gui
        new Context(states).run();
    }
}