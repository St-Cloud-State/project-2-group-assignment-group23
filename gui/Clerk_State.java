public class Clerk_State extends State{
    public Clerk_State() {
        super(new int[]{0, 3});
    }

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