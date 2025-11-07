public class Gui {
    // Program Entry Point
    public static void main(String[] args) {
        // Create the states ORDER MATTERS!!!
        State states[] = {
            new Login_State(false), // S0
            new Manager_State(),    // S1
            new Clerk_State(),      // S2
            new Client_State()      // S3
        };

        // Initialize and run the gui
        new Context(states).run();
    }
}