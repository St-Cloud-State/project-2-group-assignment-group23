import javax.swing.*;
import java.awt.*;

public class Client_State extends State{
    private static int current_client_id;
    // Fetches the client id currently in use.
    public static int get_current_client_id() {return Client_State.current_client_id;}
    // Sets the client id currently in use.
    public static boolean set_current_client_id(int id) {
        boolean ret = false;

        for (Client client : Client.master_client_list) {
            if (client.get_uid() ==  id) {
                Client_State.current_client_id = id;
                ret = true;
                break;
            }
        }

        return ret;
    }

    // Primary Constructor
    public Client_State() {
        super(new int[]{0, 3});
    }

    // Enters the state with a gui
    @Override
    protected void enter_state() {
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
            B3.addActionListener(e -> {this.B3_action();});
            B4.addActionListener(e -> {this.B4_action();});
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
        // Context C = Context.get_instance();
        // C.print(me.toString());
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

    void B3_action() {
        // Context C = Context.get_instance();
        // for (Invoice i : me.get_transaction_history()) {
        //     C.print(i.toString());
        // }
    }

    void B4_action() {
        // try {
        //     Context C = Context.get_instance();
        //     int product_id = Integer.parseInt(C.input("Input Product ID: "));
        //     Product product = null;
        //     for (Product p : Product.master_product_list) {
        //         if (p.get_uid() == product_id) {
        //             C.print("Found Product: " + p + "\n");
        //             product = p;
        //             break;
        //         }
        //     }

        //     if (product == null) {
        //         C.print("Product Not Found");
        //     } else {
        //         int quantity = Integer.parseInt(C.input("Input Quantity: "));
        //         me.add_to_wishlist(product, quantity);
        //     }
        // } catch (NumberFormatException e) {
        //     System.out.print("Invalid Input: " + e);
        // }
    }

    void B5_action() {
        // Context C = Context.get_instance();
        // for (Product p : me.get_wishlist().get_product_list()) {
        //     C.print(p.toString());
        // }
    }

    void B6_action() {
        // me.process_order();
    }

    void B7_action() {
        Context.get_instance().request_state(0);
    }
}