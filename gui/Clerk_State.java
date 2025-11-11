import javax.swing.*;
import java.awt.*;

public class Clerk_State extends State{
    // Primary Constructor
    public Clerk_State() {
        super(new int[]{0, 2, 3});
    }

    // Enters the state with a gui
    @Override
    protected void enter_state() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Clerk Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Select Operation:", SwingConstants.CENTER);
            frame.add(label, BorderLayout.NORTH);

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

            JButton B1 = new JButton("Add Client");
            JButton B2 = new JButton("Show Product List");
            JButton B3 = new JButton("Show Client List");
            JButton B4 = new JButton("Show Clients With); Outstanding Balance");
            JButton B5 = new JButton("Record Client Payment");
            JButton B6 = new JButton("Become Client");
            JButton B7 = new JButton("Logout");

            Dimension btnSize = new Dimension(200, 32);
            for (JButton b : new JButton[]{B1, B2, B3, B4, B5, B6, B7}) {
                b.setAlignmentX(Component.CENTER_ALIGNMENT);
                b.setPreferredSize(btnSize);
                b.setMaximumSize(btnSize);
                b.setMinimumSize(btnSize);
                b.setMargin(new Insets(6, 12, 6, 12));
                center.add(b);
                center.add(Box.createVerticalStrut(8));
            }

            B1.addActionListener(e -> {this.B1_action();});
            B2.addActionListener(e -> {this.B2_action(frame);});
            B3.addActionListener(e -> {this.B3_action(frame);});
            B4.addActionListener(e -> {this.B4_action(frame);});
            B5.addActionListener(e -> {this.B5_action();});
            B6.addActionListener(e -> {this.B6_action();});
            B7.addActionListener(e -> {
                frame.dispose();
                this.B7_action();
            });

            center.remove(center.getComponentCount() - 1);
            frame.add(center, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    void B1_action() {
        Context C = Context.get_instance();
        Client.master_client_list.add(new Client(
            C.input("Input Client Name:"),
            C.input("Input Client Address:"), 
            C.input("Input Client Password:")
        ));
    }

    void B2_action(JFrame frame) {
        String products = "";
        for (Product p : Product.master_product_list) {
            products += p.toString() + "\n";
        }

        JDialog popup = new JDialog(frame, "Products List", false);
        popup.setSize(400, ((Product.master_product_list.size() * 20) + 80));
        popup.setLayout(new BorderLayout());
        JTextArea list = new JTextArea(products);
        JScrollPane scroll = new JScrollPane(list);
        popup.add(scroll, BorderLayout.CENTER);
        list.setEditable(false);
        JButton close_button = new JButton("Close");
        close_button.addActionListener(ev -> popup.dispose());
        popup.add(close_button, BorderLayout.SOUTH);
        popup.setLocationRelativeTo(frame);
        popup.setVisible(true);
    }

    void B3_action(JFrame frame) {
        String clients = "";
        for (Client client : Client.master_client_list) {
            clients += client.toString();
        }

        JDialog popup = new JDialog(frame, "Client List", false);
        popup.setSize(400, ((Client.master_client_list.size() * 20) + 80));
        popup.setLayout(new BorderLayout());
        JTextArea list = new JTextArea(clients);
        JScrollPane scroll = new JScrollPane(list);
        popup.add(scroll, BorderLayout.CENTER);
        list.setEditable(false);
        JButton close_button = new JButton("Close");
        close_button.addActionListener(ev -> popup.dispose());
        popup.add(close_button, BorderLayout.SOUTH);
        popup.setLocationRelativeTo(frame);
        popup.setVisible(true);
    }

    void B4_action(JFrame frame) {
        String clients = "";
        int count = 0;
        for (Client client : Client.master_client_list) {
            if (client.get_balance() < 0) {
                clients += client.toString();
                count++;
            }
        }

        JDialog popup = new JDialog(frame, "Client List", false);
        popup.setSize(400, ((count * 20) + 80));
        popup.setLayout(new BorderLayout());
        JTextArea list = new JTextArea(clients);
        JScrollPane scroll = new JScrollPane(list);
        popup.add(scroll, BorderLayout.CENTER);
        list.setEditable(false);
        JButton close_button = new JButton("Close");
        close_button.addActionListener(ev -> popup.dispose());
        popup.add(close_button, BorderLayout.SOUTH);
        popup.setLocationRelativeTo(frame);
        popup.setVisible(true);
    }

    void B5_action() {
        Context C = Context.get_instance();
        try {
            int client_id = Integer.parseInt(C.input("Input Client ID:"));
            double payment = Double.parseDouble(C.input("Input Amount Paid:"));

            boolean found = false;
            for (Client client : Client.master_client_list) {
                if (client.get_uid() == client_id) {
                    found = true;
                    C.print("Found Client: " + client);
                    client.accept_payment(payment);
                    break;
                }
            }

            if (found != true) {
                C.print("Client Not Found!");
            }
        } catch (NumberFormatException e) {
            C.print("Invalid Value: " + e);
        }
    }

    void B6_action() {
        Context C = Context.get_instance();
        int next_state = Context.security_handle.verify_password(Security.Entity.CLERK) ? 3 : 0;
        if (next_state == 3) {
            try {
                int client_id = Integer.parseInt(C.input("Input Client ID:"));

                if (!Client_State.set_current_client_id(client_id)) {
                    next_state = 2; // Stay here
                    C.print("Couldn't Find that one!");
                } else {
                    C.request_state(next_state);
                }
            } catch (NumberFormatException e) {
                C.print("Invalid Input: " + e);
            }
        }
    }

    void B7_action() {
        Context.get_instance().request_state(0);
    }
}