import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Context {
    // Handles keeping track of what state transitions are valid and what ones are not.
    private List<List<State>> transition_matrix;


    // Keeps track of the state we're in.
    private State current_state;


    // Provides an easy means for states to get the context manager
    private static Context instance;
    public static Context get_instance() {return Context.instance;}


    // Primary Constructor.
    public Context(State states[]) {
        if (Context.instance != null) {
            throw new IllegalStateException("An instance of Context already exists!");
        }
        Context.instance = this;

        // First prep the transition matrix
        this.transition_matrix = new ArrayList<List<State>>();

        // Then add the states to the transition matrix
        int i = 0;
        for (State state : states) {
            // First make a row for it
            List<State> row = new ArrayList<State>();

            // Now we'll add enough cells in the row to handle all possible states
            while (row.size() < states.length) {
                row.add(null); // Init them to null
            }

            // Now we get our valid transitions from  the state and mark all those as valid
            for (int num : state.get_valid_transitions()) {
                row.set(num, states[num]);
            }

            // Now we're gonna let the state know what index number it is
            state.set_index(i++);

            // Finally we add the row to the transition_matrix
            transition_matrix.add(row);
        }

        // Finally, set the current state to S0
        this.current_state = states[0];
    }


    // Handles transitioning to/from states
    public void request_state(int next_state_index) {
        // First check that we're allowed to transition.
        if (next_state_index < 0
         || next_state_index >= transition_matrix.size()
         || transition_matrix.get(current_state.get_index()).get(next_state_index) == null
        ) {
            throw new IllegalStateException("Invalid State Transition! From: S" + this.current_state.get_index() + " To: S" + next_state_index);
        }


        // Then call the exit function in case theres anything to do.
        this.current_state.exit_state();


        // Finally set ourselves to the new state and call it's enter function.
        this.current_state = transition_matrix.get(current_state.get_index()).get(next_state_index);
        this.current_state.enter_state();
    }


    // Starts the state machine
    public void run() {
        this.current_state.enter_state();
    }


    // Security handler
    public static final Security security_handle = new Security("manager", "salesclerk");


    // Centralize IO operations for easy life
    public void print(String str) {System.out.print(str + "\n");}
    private static Scanner scanner = new Scanner(System.in);
    public String input() {return scanner.nextLine();}
    public void clear_console() {System.out.print("\033[H\033[2J"); System.out.flush();}
}
