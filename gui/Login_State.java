public class Login_State extends State{
    // Primary Constructor
    public Login_State() {
        super(new int[]{0, 1, 2, 3});
    }

    // Enters the state without a gui
    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        int next_state = 0;

        C.clear_console();
        C.print("Select User:");
        C.print("  1. Manager");
        C.print("  2. Sales Clerk");
        C.print("  3. Client");
        C.print("  4. Exit");

        switch (C.input()) {
            case "1": {
                next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 1 : 0;
                break;
            }
            case "2": {
                next_state = Context.security_handle.verify_password(Security.Entity.CLERK) ? 2 : 0;
                break;
            }
            case "3": {
                next_state = Context.security_handle.verify_password(Security.Entity.CLIENT) ? 3 : 0;
                break;
            }
            case "4": {
                C.clear_console();
                C.print("Goodbye!");
                return; // Returning here actually returns us to main (I think, who really knows).
            }
            default:  {
                C.print("Invalid Input");
                break;
            }
        }

        C.request_state(next_state);
    }
}