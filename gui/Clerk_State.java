public class Clerk_State extends State{
    public Clerk_State() {
        super(new int[]{0, 3});
    }

    public void enter_state() {
        Context.get_instance().print("Enter Clerk State");
        Context.get_instance().request_state(3);
    }

    public void exit_state() {
        Context.get_instance().print("Exit Clerk State");
    }
}