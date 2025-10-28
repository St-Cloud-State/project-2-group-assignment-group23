public class Clerk_State extends State{
    public Clerk_State() {
        super(new int[]{0, 3});
    }

    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        C.clear_console();
        C.print("Entered Clerk State, Press enter");
        C.input();
        C.request_state(0);
    }
}