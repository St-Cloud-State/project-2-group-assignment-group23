public class Client_State extends State{
    private static int current_client_id;
    // Fetches the client id currently in use.
    public static int get_current_client_id() {return Client_State.current_client_id;}
    // Sets the client id currently in use.
    public static boolean set_current_client_id(int id) {
        boolean ret = false;

        for (Client client : Client.master_client_list) {
            if (client.get_uid() ==  id) {
                Client_State.current_client_id = id;
                ret = true;
                break;
            }
        }

        return ret;
    }

    // Primary Constructor
    public Client_State() {
        super(new int[]{0, 3});
    }

    // Enters the state without a gui
    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        int next_state = 0;

        C.clear_console();
        C.print("Select Operation:");
        C.print("  1. Show Client Details");
        C.print("  2. Show Product List");
        C.print("  3. Show Client Transactions");
        C.print("  4. Add Item to Client Wishlist");
        C.print("  5. Display Client Wishlist");
        C.print("  6. Place Order");
        C.print("  7. Logout");

        // First we want to parse this client's info from the master client list.
        Client me = null;
        for (Client client : Client.master_client_list) {
            if (client.get_uid() == current_client_id) {
                me = client;
            }
        }

        // Shouldn't be possible to be here without a valid client id, but theres probably some wierd edge case I don't know about.
        if (me != null) {
            switch (C.input()) {
                case "1": {
                    C.print(me.toString());
                    C.print("Press Enter to Continue");
                    C.input();
                    next_state = 3; // Stay here
                    break;
                }
                case "2": {
                    for (Product p : Product.master_product_list) {
                        C.print(p.toString());
                    }
                    C.print("Press Enter to Continue");
                    C.input();
                    next_state = 3; // Stay here
                    break;
                }
                case "3": {
                    for (Invoice i : me.get_transaction_history()) {
                        C.print(i.toString());
                    }
                    C.print("Press Enter to Continue");
                    C.input();
                    next_state = 3; // Stay here
                    break;
                }
                case "4": {
                    try {
                        C.print("Input Product ID: ");
                        int product_id = Integer.parseInt(C.input());
                        Product product = null;
                        for (Product p : Product.master_product_list) {
                            if (p.get_uid() == product_id) {
                                C.print("Found Product: " + p + "\n");
                                product = p;
                                break;
                            }
                        }

                        if (product == null) {
                            C.print("Product Not Found");
                            C.wait_a_sec();
                        } else {
                            C.print("Input Quantity: ");
                            int quantity = Integer.parseInt(C.input());
                            me.add_to_wishlist(product, quantity);
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("Invalid Input: " + e);
                        C.wait_a_sec();
                    }
                    next_state = 3; // Stay here
                    break;
                }
                case "5": {
                    for (Product p : me.get_wishlist().get_product_list()) {
                        C.print(p.toString());
                    }
                    C.print("Press Enter to Continue");
                    C.input();
                    next_state = 3; // Stay here
                    break;
                }
                case "6": {
                    me.process_order();
                    next_state = 3; // Stay here
                    break;
                }
                case "7": {
                    break;  // Nothing to do here
                }
                default:  {
                    C.print("Invalid Input");
                    C.wait_a_sec();
                    break;
                }
            }
        }

        C.request_state(next_state);
    }
}