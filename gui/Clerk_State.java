public class Clerk_State extends State{
    public Clerk_State() {
        super(new int[]{0, 3});
    }

    public void enter_state() {
        Context.get_instance().clear_console();
        Context.get_instance().print("Enter Clerk State, Press enter");
        Context.get_instance().input();
        Context.get_instance().request_state(0);
    }

    public void exit_state() {
        Context.get_instance().print("Exiting Clerk State");
    }
}