public class Manager_State extends State{
    public Manager_State() {
        super(new int[]{0, 2, 3});
    }

    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        C.clear_console();
        C.print("Entered Manager State, Press enter");
        C.input();
        C.request_state(0);
    }
}