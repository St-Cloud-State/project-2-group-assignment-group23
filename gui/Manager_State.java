import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
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

            Dimension btnSize = new Dimension(400, 32);
            for (JButton b : new JButton[]{B1, B2, B3, B4, B5}) {
                b.setAlignmentX(Component.CENTER_ALIGNMENT);
                b.setPreferredSize(btnSize);
                b.setMaximumSize(btnSize);
                b.setMinimumSize(btnSize);
                b.setMargin(new Insets(6, 12, 6, 12));
                center.add(b);
                center.add(Box.createVerticalStrut(8));
            }

            B1.addActionListener(e -> {this.B1_action(frame);});
            B2.addActionListener(e -> {this.B2_action();});
            B3.addActionListener(e -> {this.B3_action(frame);});
            B4.addActionListener(e -> {
                frame.dispose();
                this.B4_action();
            });
            B5.addActionListener(e -> {
                frame.dispose();
                this.B5_action();
            });

            center.remove(center.getComponentCount() - 1);
            frame.add(center, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    void B1_action(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField name = new JTextField(10);
        panel.add(new JLabel("Name:"));
        panel.add(name);

        JFormattedTextField quantity = new JFormattedTextField(Context.int_former);
        quantity.setColumns(10);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantity);

        JFormattedTextField price = new JFormattedTextField(Context.double_former);
        price.setColumns(10);
        panel.add(new JLabel("Price:"));
        panel.add(price);

        int ret = JOptionPane.showConfirmDialog(frame, 
                                                panel, 
                                                "Enter Product Information", 
                                                JOptionPane.OK_CANCEL_OPTION, 
                                                JOptionPane.PLAIN_MESSAGE
                                                );

        if (ret == JOptionPane.OK_OPTION
         && name.getText().isEmpty() != true
         && quantity.getValue() != null
         && price.getValue() != null
        ) {
            Product.master_product_list.add(new Product(
                name.getText(),
                (Integer)quantity.getValue(), 
                (Double)price.getValue()
            ));
        }
    }

    void B2_action() {
        Context C = Context.get_instance();
        Integer uid = C.input("Input Product UID:", Integer.class);

        if (uid != null) {
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
        }
    }

    void B3_action(JFrame frame) {
        JFileChooser file = new JFileChooser();
        int ret = file.showOpenDialog(frame);

        try {
            if (ret == JFileChooser.APPROVE_OPTION) {
                File path = file.getSelectedFile();
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
            }
        }
        // A better program would handle these.
        catch(FileNotFoundException e) {}
        catch(IOException e) {}
    }

    void B4_action() {
        int next_state = Context.security_handle.verify_password(Security.Entity.MANAGER) ? 2 : 1;
        Context.get_instance().request_state(next_state);
    }

    void B5_action() {
        Context.get_instance().request_state(0);
    }

}