public class Manager_State extends State{
    public Manager_State() {
        super(new int[]{0, 2, 3});
    }

    public void enter_state() {
        Context.get_instance().print("Enter Manager State");
        Context.get_instance().request_state(2);
    }

    public void exit_state() {
        Context.get_instance().print("Exit Manager State");
    }
}