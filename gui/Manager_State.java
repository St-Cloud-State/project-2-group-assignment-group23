public class Manager_State extends State{
    public Manager_State() {
        super(new int[]{0, 2, 3});
    }

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