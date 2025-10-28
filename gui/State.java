public abstract class State {
    // This will be used to swap from console to gui for the second part of the project.
    private boolean headless;


    // Used to set what are/aren't valid states to transition to.
    protected final int[] valid_transitions;
    public int[] get_valid_transitions() {return this.valid_transitions;}


    // Primary Constructor
    public State(int[] valid_transitions) {
        this.valid_transitions = valid_transitions;
        this.headless = true;
    }


    // Used to keep track of what state number we are, makes transitioning easier on the Context manager.
    protected int index;
    public int get_index() {return this.index;}
    public void set_index(int number) {this.index = number;}


    // Methods called by the context manager to transition into a state.
    public final void enter_state() {
        if (this.headless) {
            this.enter_no_gui();
        } else {
            this.enter_gui();
        }
    }
    protected void enter_no_gui() {}
    protected void enter_gui() {}


    // Methods called by the context manager to transition out of a state.
    public final void exit_state() {
        if (this.headless) {
            this.exit_no_gui();
        } else {
            this.exit_gui();
        }
    }
    protected void exit_no_gui() {}
    protected void exit_gui() {}
}