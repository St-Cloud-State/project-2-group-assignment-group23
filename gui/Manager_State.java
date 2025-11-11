import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
        String req = "Select Operation:\n"
                   + "  1. Add Product\n"
                   + "  2. Display Product Waitlist\n"
                   + "  3. Recieve Shipment\n"
                   + "  4. Become Clerk\n"
                   + "  5. Logout";

        switch (C.input(req, true)) {
            case "1": {
                try {
                    String name = C.input("Input Product Name:", true);
                    int quantity = Integer.parseInt(C.input("Input Product Quantity:", true));
                    Double price = Double.parseDouble(C.input("Input Product Price: ", true));
                    Product.master_product_list.add(new Product(name, quantity, price));
                } catch (NumberFormatException e) {
                    C.print("Invalid Value: " + e);
                    C.wait_a_sec();
                }
                next_state = 1; // Stay here
                break;
            }
            case "2": {
                try {
                    int uid = Integer.parseInt(C.input("Input Product UID:", true));

                    boolean found_it = false;
                    for (Product p : Product.master_product_list) {
                        if (p.get_uid() == uid) {
                            found_it = true;

                            Map<Client, Integer> waitlist = p.get_waitlist();
                            for (Map.Entry<Client, Integer> entry : waitlist.entrySet()) {
                                C.print("Client: " + entry.getKey() + " Waiting on qty: " + entry.getValue());
                            }

                            C.input("Press Enter to Continue:", true);

                            break;
                        }
                    }

                    if (found_it == false) {
                        C.print("Didn't find that product.");
                        C.wait_a_sec();
                    }
                } catch (NumberFormatException e) {
                    C.print("Invalid Value: " + e);
                    C.wait_a_sec();
                }

                next_state = 1; // Stay here
                break;
            }
            case "3": {
                try {
                    File path = new File(System.getProperty("user.dir"), C.input("Input Path to product csv file:", true));
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