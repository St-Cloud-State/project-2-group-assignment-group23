public abstract class State {
    // Used to set what are/aren't valid states to transition to
    protected final int[] valid_transitions;
    public int[] get_valid_transitions() {return this.valid_transitions;}


    // Primary Constructor
    public State(int[] valid_transitions) {
        this.valid_transitions = valid_transitions;
    }


    // Used to keep track of what state number we are, makes transitioning easier on the Context manager
    protected int index;
    public int get_index() {return this.index;}
    public void set_index(int number) {this.index = number;}


    // Methods called by the context manager to transition to/from states
    public abstract void enter_state();
    public abstract void exit_state();
}