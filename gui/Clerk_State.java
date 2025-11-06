public class Clerk_State extends State{
    // Primary Constructor
    public Clerk_State() {
        super(new int[]{0, 2, 3});
    }

    // Enters the state without a gui
    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        int next_state = 0;

        C.clear_console();
        C.print("Select Operation:");
        C.print("  1. Add Client");
        C.print("  2. Show Product List");
        C.print("  3. Show Client List");
        C.print("  4. Show Clients With Outstanding Balance");
        C.print("  5. Record Client Payment");
        C.print("  6. Become Client");
        C.print("  7. Logout");

        switch (C.input()) {
            case "1": {
                C.print("Input Client Name: ");
                String name = C.input();
                C.print("Input Client Address: ");
                String address = C.input();
                C.print("Input Client Password: ");
                String password = C.input();
                Client.master_client_list.add(new Client(name, address, password));
                next_state = 2; // Stay here
                break;
            }
            case "2": {
                for (Product p : Product.master_product_list) {
                    C.print(p.toString());
                }
                C.print("Press Enter to Continue");
                C.input();
                next_state = 2; // Stay here
                break;
            }
            case "3": {
                for (Client client : Client.master_client_list) {
                    C.print(client.toString());
                }
                C.print("Press Enter to Continue");
                C.input();
                next_state = 2; // Stay here
                break;
            }
            case "4": {
                for (Client client : Client.master_client_list) {
                    if (client.get_balance() < 0) {
                        C.print(client.toString());
                    }
                }
                C.print("Press Enter to Continue");
                C.input();
                next_state = 2; // Stay here
                break;
            }
            case "5": {
                try {
                    C.print("Input Client ID:");
                    int client_id = Integer.parseInt(C.input());
                    System.out.print("Input Amount Paid:");
                    double payment = Double.parseDouble(C.input());

                    boolean found = false;
                    for (Client client : Client.master_client_list) {
                        if (client.get_uid() == client_id) {
                            found = true;
                            C.print("Found Client: " + client);
                            client.accept_payment(payment);
                            break;
                        }
                    }

                    if (found != true) {
                        C.print("Client Not Found!");
                    }
                } catch (NumberFormatException e) {
                    C.print("Invalid Value: " + e);
                    C.wait_a_sec();
                }
                C.wait_a_sec(); // So they can read the responses
                next_state = 2; // Stay here
                break;
            }
            case "6": {
                next_state = Context.security_handle.verify_password(Security.Entity.CLERK) ? 3 : 0;
                if (next_state == 3) {
                    try {
                        C.print("Input Client ID:");

                        int client_id = Integer.parseInt(C.input());

                        if (!Client_State.set_current_client_id(client_id)) {
                            next_state = 2; // Stay here
                            C.print("Couldn't Find that one!");
                            C.wait_a_sec();
                        }
                    } catch (NumberFormatException e) {
                        C.print("Invalid Input: " + e);
                        C.wait_a_sec();
                    }
                }
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

        C.request_state(next_state);
    }
}