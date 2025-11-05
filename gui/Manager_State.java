import java.io.*;
import java.nio.charset.StandardCharsets;

public class Manager_State extends State{
    // Primary Constructor
    public Manager_State() {
        super(new int[]{0, 1, 2, 3});
    }

    // Enters the state without a gui
    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        int next_state = 0;

        C.clear_console();
        C.print("Select Operation:");
        C.print("  1. Add Product");
        C.print("  2. Display Product Waitlist");
        C.print("  3. Recieve Shipment");
        C.print("  4. Become Clerk");
        C.print("  5. Logout");

        switch (C.input()) {
            case "1": {
                try {
                    C.print("Input Product Name:");
                    String name = C.input();
                    C.print("Input Product Quantity:");
                    int quantity = Integer.parseInt(C.input());
                    System.out.print("Input Product Price: ");
                    Double price = Double.parseDouble(C.input());
                    Product.master_product_list.add(new Product(name, quantity, price));
                } catch (NumberFormatException e) {
                    C.print("Invalid Value: " + e);
                    C.wait_a_sec();
                }
                next_state = 1; // Stay here
                break;
            }
            case "2": {
                C.print("Not Implemented yet!");
                C.wait_a_sec();
                next_state = 1; // Stay here
                break;
            }
            case "3": {
                try {
                    C.print("Input Path to product csv file:");
                    File path = new File(System.getProperty("user.dir"), C.input());
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
                } catch (Exception e) {
                    C.print("Something went wrong. A better program would tell you what that was.");
                    C.wait_a_sec();
                }
                
                C.wait_a_sec();
                next_state = 1; // Stay here
                break;
            }
            case "4": {
                next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 2 : 0;
                break;
            }
            case "5": {
                break;  // Nothing to do here
            }
            default:  {
                C.print("Invalid Input");
                C.wait_a_sec();
                break;
            }
        }

        C.request_state(next_state);
    }
}