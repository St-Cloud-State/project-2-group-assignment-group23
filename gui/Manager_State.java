import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

public class Manager_State extends State{
    // Primary Constructor
    public Manager_State() {
        super(new int[]{0, 1, 2, 3});
    }

    @Override
    protected void enter_state() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Manager Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Select Operation:", SwingConstants.CENTER);
            frame.add(label, BorderLayout.NORTH);

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

            JButton B1 = new JButton("Add Product");
            JButton B2 = new JButton("Display Product Waitlist");
            JButton B3 = new JButton("Recieve Shipment");
            JButton B4 = new JButton("Become Clerk");
            JButton B5 = new JButton("Logout");

            Dimension btnSize = new Dimension(140, 32);
            for (JButton b : new JButton[]{B1, B2, B3, B4, B5}) {
                b.setAlignmentX(Component.CENTER_ALIGNMENT);
                b.setPreferredSize(btnSize);
                b.setMaximumSize(btnSize);
                b.setMinimumSize(btnSize);
                b.setMargin(new Insets(6, 12, 6, 12));
                center.add(b);
                center.add(Box.createVerticalStrut(8));
            }

            B1.addActionListener(e -> {
                this.B1_action();
            });
            B2.addActionListener(e -> {
                this.B2_action();
            });
            B3.addActionListener(e -> {
                this.B3_action();
            });
            B4.addActionListener(e -> {
                this.B4_action();
            });
            B5.addActionListener(e -> {
                this.B5_action();
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
        try {
            Product.master_product_list.add(new Product(
                C.input("Input Product Name:"),
                Integer.parseInt(C.input("Input Product Quantity:")),
                Double.parseDouble(C.input("Input Product Price: "))
            ));
        } catch (NumberFormatException e) {
            C.print("Invalid Value: " + e);
            C.wait_a_sec();
        }
    }

    void B2_action() {
        Context C = Context.get_instance();
        try {
            int uid = Integer.parseInt(C.input("Input Product UID:"));

            boolean found_it = false;
            for (Product p : Product.master_product_list) {
                if (p.get_uid() == uid) {
                    found_it = true;

                    Map<Client, Integer> waitlist = p.get_waitlist();
                    for (Map.Entry<Client, Integer> entry : waitlist.entrySet()) {
                        C.print("Client: " + entry.getKey() + " Waiting on qty: " + entry.getValue());
                    }
                    break;
                }
            }

            if (found_it == false) {
                C.print("Didn't find that product.");
            }
        } catch (NumberFormatException e) {
            C.print("Invalid Value: " + e);
        }
    }

    void B3_action() {
        Context C = Context.get_instance();
        try {
            File path = new File(System.getProperty("user.dir"), C.input("Input Path to product csv file:"));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String in = br.readLine();
            br.close();
            String[] arr = in.split(",");
            for (int i = 0; i < arr.length/3; i++) {
                String name = arr[i++];
                int qty = Integer.parseInt(arr[i++]);
                double price = Double.parseDouble(arr[i]);  // Dont iterate here, loop will do it.
                Product.add_to_mpl(name, qty, price);
            }
        } catch (Exception e) {
            C.print("Something went wrong. A better program would tell you what that was.");
        }
    }

    void B4_action() {
        int next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 2 : 0;
        Context.get_instance().request_state(next_state);
    }

    void B5_action() {
        Context.get_instance().request_state(0);
    }

}