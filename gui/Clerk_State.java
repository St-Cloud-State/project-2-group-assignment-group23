import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.text.NumberFormat;

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
            JButton B4 = new JButton("Show Clients With Outstanding Balance");
            JButton B5 = new JButton("Record Client Payment");
            JButton B6 = new JButton("Become Client");
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

            B1.addActionListener(e -> {this.B1_action(frame);});
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

    void B1_action(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField name = new JTextField(10);
        panel.add(new JLabel("Client Name: "));
        panel.add(name);

        JTextField address = new JTextField(10);
        panel.add(new JLabel("Client Address: "));
        panel.add(address);

        JTextField password = new JTextField(10);
        panel.add(new JLabel("Client Password: "));
        panel.add(password);

        int ret = JOptionPane.showConfirmDialog(frame, 
                                                panel, 
                                                "Enter Client Information", 
                                                JOptionPane.OK_CANCEL_OPTION, 
                                                JOptionPane.PLAIN_MESSAGE
                                                );

        if (ret == JOptionPane.OK_OPTION
            && name.getText().isEmpty() != true
            && address.getText().isEmpty() != true
            && password.getText().isEmpty() != true
        ) {
            Client.master_client_list.add(new Client(
                name.getText(),
                address.getText(), 
                password.getText()
            ));
        }
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

    void B5_action(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        NumberFormat int_form = NumberFormat.getIntegerInstance();
        NumberFormatter int_former = new NumberFormatter(int_form);
        int_former.setValueClass(Integer.class);
        int_former.setAllowsInvalid(false);
        JFormattedTextField uid = new JFormattedTextField(int_former);
        uid.setColumns(10);
        panel.add(new JLabel("UID:"));
        panel.add(uid);

        NumberFormat double_form = NumberFormat.getNumberInstance();
        double_form.setGroupingUsed(false);
        double_form.setMaximumFractionDigits(2);
        NumberFormatter double_former = new NumberFormatter(double_form);
        double_former.setValueClass(Double.class);
        JFormattedTextField payment = new JFormattedTextField(double_former);
        payment.setColumns(10);
        panel.add(new JLabel("Payment:"));
        panel.add(payment);

        int ret = JOptionPane.showConfirmDialog(frame, 
                                                panel, 
                                                "Enter Client Information", 
                                                JOptionPane.OK_CANCEL_OPTION, 
                                                JOptionPane.PLAIN_MESSAGE
                                                );

        if (ret == JOptionPane.OK_OPTION
         && uid.getValue() != null
         && payment.getValue() != null
         ) {
            boolean found = false;
            for (Client client : Client.master_client_list) {
                if (client.get_uid() == (Integer)uid.getValue()) {
                    found = true;
                    client.accept_payment((Double)payment.getValue());
                    JOptionPane.showMessageDialog(frame, client.toString());
                    break;
                }
            }

            if (found != true) {
                JOptionPane.showMessageDialog(frame, "Client Not Found!");
            }
        }
    }

    void B6_action(JFrame frame) {
        Context C = Context.get_instance();
        int next_state = Context.security_handle.verify_password(Security.Entity.CLERK) ? 3 : 0;
        if (next_state == 3) {
            int client_id = Integer.parseInt(C.input("Input Client ID:"));
            boolean found = false;
            for (Client client : Client.master_client_list) {
                if (client.get_uid() == client_id) {
                    Client_State.set_current_client_id(client);
                    found = true;
                }
            }

            if (found) {
                frame.dispose();
                C.request_state(next_state);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Client ID");
            }
        }
    }

    void B7_action() {
        Context.get_instance().request_state(0);
    }
}