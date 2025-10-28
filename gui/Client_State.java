public class Client_State extends State{
    public Client_State() {
        super(new int[]{0});
    }

    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        C.clear_console();
        C.print("Entered Client State, Press enter");
        C.input();
        C.request_state(0);
    }
}