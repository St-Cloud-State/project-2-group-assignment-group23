public class Login_State extends State{
    public Login_State() {
        super(new int[]{1, 2, 3});
    }

    public void enter_state() {
        Context.get_instance().print("Enter Login State");
        Context.get_instance().request_state(1);
    }

    public void exit_state() {
        Context.get_instance().print("Exit Login State");
    }
}