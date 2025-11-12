import javax.swing.*;
import java.awt.*;

public class Client_State extends State{
    private static Client current_client;
    public static Client get_current_client_id() {return Client_State.current_client;}
    public static void set_current_client_id(Client client) {Client_State.current_client = client;}

    // Primary Constructor
    public Client_State() {
        super(new int[]{0, 3});
    }

    // Enters the state with a gui
    @Override
    protected void enter_state() {
        if (current_client == null) {
            throw new AssertionError("How did you get here?");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Client Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Select Operation:", SwingConstants.CENTER);
            frame.add(label, BorderLayout.NORTH);

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

            JButton B1 = new JButton("Show Client Details");
            JButton B2 = new JButton("Show Product List");
            JButton B3 = new JButton("Show Client Transactions");
            JButton B4 = new JButton("Add Item to Client Wishlist");
            JButton B5 = new JButton("Display Client Wishlist");
            JButton B6 = new JButton("Place Order");
            JButton B7 = new JButton("Logout");

            Dimension btnSize = new Dimension(400, 32);
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
            B5.addActionListener(e -> {this.B5_action(frame);});
            B6.addActionListener(e -> {this.B6_action(frame);});
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
        Context.get_instance().print(current_client.toString());
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
        String history = "";
        int count = 0;
        for (Invoice i : current_client.get_transaction_history()) {
            history += i.toString() + "\n";
            count++;
        }

        JDialog popup = new JDialog(frame, "Transaction History", false);
        popup.setSize(400, ((count * 20) + 80));
        popup.setLayout(new BorderLayout());
        JTextArea list = new JTextArea(history);
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
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JFormattedTextField product_id = new JFormattedTextField(Context.int_former);
        product_id.setColumns(10);
        panel.add(new JLabel("Product ID:"));
        panel.add(product_id);

        JFormattedTextField quantity = new JFormattedTextField(Context.int_former);
        quantity.setColumns(10);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantity);

        int ret = JOptionPane.showConfirmDialog(frame, 
                                                panel, 
                                                "Enter Product Information", 
                                                JOptionPane.OK_CANCEL_OPTION, 
                                                JOptionPane.PLAIN_MESSAGE
                                                );

        if (ret == JOptionPane.OK_OPTION
         && product_id.getValue() != null
         && quantity.getValue() != null
        ) {
            boolean found_it = false;
            for (Product p : Product.master_product_list) {
                if (p.get_uid() == (Integer)product_id.getValue()) {
                    current_client.add_to_wishlist(p, (Integer)quantity.getValue());
                    Context.get_instance().print("Found Product: " + p);
                    found_it = true;
                    break;
                }
            }
            if (found_it == false) {
                Context.get_instance().print("Couldn't find that product");
            }
        }
    }

    void B5_action(JFrame frame) {
        String products = "";
        int count = 0;
        for (Product p : current_client.get_wishlist().get_product_list()) {
            products += p.toString() + "\n";
            count++;
        }

        JDialog popup = new JDialog(frame, "Wish List", false);
        popup.setSize(400, ((count * 20) + 80));
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

    void B6_action(JFrame frame) {
        JDialog popup = new JDialog(frame, "Process Order", false);

        popup.setSize(400, ((current_client.get_wishlist().get_product_list().size() * 30) + 80));
        popup.setLayout(new BorderLayout());

        JPanel checkbox_panel = new JPanel();
        checkbox_panel.setLayout(new BoxLayout(checkbox_panel, BoxLayout.Y_AXIS));
        java.util.List<JCheckBox> check_boxes = new java.util.ArrayList<>();
        for (Product p : current_client.get_wishlist().get_product_list()) {
            JCheckBox check_box = new JCheckBox(p.toString());
            check_boxes.add(check_box);
            checkbox_panel.add(check_box);
        }

        JScrollPane scroll = new JScrollPane(checkbox_panel);
        popup.add(scroll, BorderLayout.CENTER);

        JPanel button_panel = new JPanel();
        JButton close_button = new JButton("Close");
        close_button.addActionListener(ev -> popup.dispose());

        JButton confirm_button = new JButton("Confirm");

        
        confirm_button.addActionListener(ev -> {
            Wishlist purchase_list = new Wishlist(); // Create a new wishlist to hold our purchases

            for (int i = 0; i < check_boxes.size(); i++) {
                if (check_boxes.get(i).isSelected()) {
                    Product p = current_client.get_wishlist().get_product_list().get(i);
                    purchase_list.add_product(p);
                }
            }
            popup.dispose();
            current_client.process_order(purchase_list);
        });

        button_panel.add(confirm_button);
        button_panel.add(close_button);
        popup.add(button_panel, BorderLayout.SOUTH);

        popup.setLocationRelativeTo(frame);
        popup.setVisible(true);
    }

    void B7_action() {
        Context.get_instance().request_state(0);
    }
}