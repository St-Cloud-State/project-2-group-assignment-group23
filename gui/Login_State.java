
import javax.swing.*;
import java.awt.*;

public class Login_State extends State {
    // Primary Constructor
    public Login_State(boolean headless) {
        super(new int[]{0, 1, 2, 3});
        this.headless = headless;
    }

    // Enters the state without a gui
    @Override
    protected void enter_no_gui() {
        Context C = Context.get_instance();

        int next_state = 0;

        C.clear_console();
        String req = "Select User:\n"
                   + "  1. Manager\n"
                   + "  2. Sales Clerk\n"
                   + "  3. Client\n"
                   + "  4. Exit";

        switch (C.input(req, true)) {
            case "1": {
                next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 1 : 0;
                break;
            }
            case "2": {
                next_state = Context.security_handle.verify_password(Security.Entity.CLERK) ? 2 : 0;
                break;
            }
            case "3": {
                next_state = Context.security_handle.verify_password(Security.Entity.CLIENT) ? 3 : 0;
                break;
            }
            case "4": {
                C.clear_console();
                C.print("Goodbye!");
                return; // Returning here actually returns us to main (I think, who really knows).
            }
            default:  {
                C.print("Invalid Input");
                break;
            }
        }

        C.request_state(next_state);
    }

    // Enters the state with a gui
    @Override
    protected void enter_gui() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Select User:", SwingConstants.CENTER);
            frame.add(label, BorderLayout.NORTH);

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

            JButton manager = new JButton("Manager");
            JButton sales_clerk = new JButton("Sales Clerk");
            JButton client = new JButton("Client");
            JButton exit = new JButton("Exit");

            Dimension btnSize = new Dimension(140, 32);
            for (JButton b : new JButton[]{manager, sales_clerk, client, exit}) {
                b.setAlignmentX(Component.CENTER_ALIGNMENT);
                b.setPreferredSize(btnSize);
                b.setMaximumSize(btnSize);
                b.setMinimumSize(btnSize);
                b.setMargin(new Insets(6, 12, 6, 12));
                center.add(b);
                center.add(Box.createVerticalStrut(8));
            }

            Context C = Context.get_instance();
            manager.addActionListener(e -> {
                int next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 1 : 0;
                frame.dispose();
                C.request_state(next_state);
            });
            sales_clerk.addActionListener(e -> {
                int next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 2 : 0;
                frame.dispose();
                C.request_state(next_state);
            });
            client.addActionListener(e -> {
                int next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 3 : 0;
                frame.dispose();
                C.request_state(next_state);
            });
            exit.addActionListener(e -> {frame.dispose();});

            center.remove(center.getComponentCount() - 1);
            frame.add(center, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}