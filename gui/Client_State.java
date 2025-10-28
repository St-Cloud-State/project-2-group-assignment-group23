public class Client_State extends State{
    private static int current_client_id;
    public static int get_current_client_id() {return Client_State.current_client_id;}
    public static boolean set_current_client_id(int id) {
        boolean ret = false;

        if (true) { // This needs to query Client.master_client_list to see if the client exists
            Client_State.current_client_id = id;
            ret = true;
        }

        return ret;
    }

    public Client_State() {
        super(new int[]{0});
    }

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

        switch (C.input()) {
            case "1": {
                break;
            }
            case "2": {
                break;
            }
            case "3": {
                break;
            }
            case "4": {
                break;
            }
            case "5": {
                break;
            }
            case "6": {
                break;
            }
            case "7": {
                break;  // Nothing to do here
            }
            default:  {
                C.print("Invalid Input");
                break;
            }
        }

        C.request_state(next_state);
    }
}