public class Manager_State extends State{
    public Manager_State() {
        super(new int[]{0, 2, 3});
    }

    public void enter_state() {
        Context.get_instance().clear_console();
        Context.get_instance().print("Entered Manager State, Press enter");
        Context.get_instance().input();
        Context.get_instance().request_state(0);
    }

    public void exit_state() {
        Context.get_instance().print("Exiting Manager State");
    }
}