public class Client_State extends State{
    public Client_State() {
        super(new int[]{0});
    }

    public void enter_state() {
        Context.get_instance().clear_console();
        Context.get_instance().print("Enter Client State, Press enter");
        Context.get_instance().input();
        Context.get_instance().request_state(0);
    }

    public void exit_state() {
        Context.get_instance().print("Exiting Client State");
    }
}