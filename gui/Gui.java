public class Gui {
    public static void main(String[] args) {
        // Create the states ORDER MATTERS!!!
        State states[] = {
            new Login_State(),      // S0
            new Manager_State(),    // S1
            new Clerk_State(),      // S2
            new Client_State()      // S3
        };

        // Initialize and run the gui
        new Context(states).run();
    }
}




// public class gui {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         boolean keep_running = true;
//         while (keep_running){
//             System.out.print("\n\nEnter Command:\n");
//             System.out.print("      0. Exit\n");
//             System.out.print("      1. List Clients\n");
//             System.out.print("      2. Add Client\n");
//             System.out.print("      3. List Products\n");
//             System.out.print("      4. Add Product\n");
//             System.out.print("      5. Display Client Wishlist\n");
//             System.out.print("      6. Add Product to Client Wishlist\n");
//             System.out.print("      7. Checkout Client\n");
//             System.out.print("      8. Accept Client Payment\n");
//             System.out.print("      9. Print Client Invoices\n");
//             System.out.print("      10. Recieve Product Shipment\n");
//             System.out.print("      11. Display Client Waitlist\n\n");
//             String input = scanner.nextLine();

//             switch (input) {
//                 case "0": { // Exit
//                     keep_running = false;
//                     continue;
//                 }
//                 case "1": { // List Clients
//                     for (Client c : Client.master_client_list) {
//                         System.out.print(c + "\n");
//                     }
//                     continue;
//                 }
//                 case "2": { // Add Client
//                     System.out.print("Input Client Name: ");
//                     String name = scanner.nextLine();
//                     System.out.print("Input Client Address: ");
//                     String address = scanner.nextLine();
//                     Client.master_client_list.add(new Client(name, address));
//                     continue;
//                 }
//                 case "3": { // List Products
//                     for (Product p : Product.master_product_list) {
//                         System.out.print(p + "\n");
//                     }
//                     continue;
//                 }
//                 case "4": { // Add Product
//                     System.out.print("Input Product Name: ");
//                     String name = scanner.nextLine();
//                     System.out.print("Input Product Quantity: ");
//                     String quantity = scanner.nextLine();
//                     System.out.print("Input Product Price: ");
//                     String price = scanner.nextLine();
//                     try {
//                         Product.master_product_list.add(new Product(name, Integer.parseInt(quantity), Double.parseDouble(price)));
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                     }
//                     continue;
//                 }
//                 case "5": { // Display Client Wishlist
//                     System.out.print("Input Client ID: ");
//                     int client_id;
//                     try {
//                         client_id = Integer.parseInt(scanner.nextLine());
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }
//                     boolean found = false;
//                     for (Client c : Client.master_client_list) {
//                         if (c.get_uid() == client_id) {
//                             System.out.print(c.get_wishlist());
//                             found = true;
//                             break;
//                         }
//                     }
//                     if (found == false) {
//                         System.out.print("Client ID not found!");
//                     }
//                     continue;
//                 }
//                 case "6": { // Add Product to Client Wishlist
//                     System.out.print("Input Client ID: ");
//                     Client client = null;
//                     try {
//                         int client_id = Integer.parseInt(scanner.nextLine());
//                         for (Client c : Client.master_client_list) {
//                             if (c.get_uid() == client_id) {
//                                 System.out.print("Found Client: " + c + "\n");
//                                 client = c;
//                                 break;
//                             }
//                         }
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }
//                     if (client == null) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }

//                     System.out.print("Input Product ID: ");
//                     Product product = null;
//                     try {
//                         int product_id = Integer.parseInt(scanner.nextLine());
//                         for (Product p : Product.master_product_list) {
//                             if (p.get_uid() == product_id) {
//                                 System.out.print("Found Product: " + p + "\n");
//                                 product = p;
//                                 break;
//                             }
//                         }
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }
//                     if (product == null) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }

//                     System.out.print("Input Quantity: ");
//                     int quantity = 0;
//                     try {
//                         quantity = Integer.parseInt(scanner.nextLine());
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }

//                     client.add_to_wishlist(product, quantity);
//                     continue;
//                 }
//                 case "7": { // Checkout Client
//                     System.out.print("Input Client ID: ");
//                     boolean found = false;
//                     try {
//                         int client_id = Integer.parseInt(scanner.nextLine());
//                         for (Client c : Client.master_client_list) {
//                             if (c.get_uid() == client_id) {
//                                 found = true;
//                                 System.out.print("Found Client: " + c + "\n");
//                                 c.process_order(scanner);
//                                 break;
//                             }
//                         }
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                     }

//                     if (found == true) {
//                         continue;
//                     } else {
//                         System.out.print("Client Not Found!");
//                         continue;
//                     }
//                 }
//                 case "8": { // Accept Client Payment
//                     System.out.print("Input Client ID: ");
//                     boolean found = false;
//                     try {
//                         int client_id = Integer.parseInt(scanner.nextLine());
//                         System.out.print("Input Amount Paid: ");
//                         double payment = Double.parseDouble(scanner.nextLine());

//                         for (Client c : Client.master_client_list) {
//                             if (c.get_uid() == client_id) {
//                                 found = true;
//                                 System.out.print("Found Client: " + c + "\n");
//                                 c.accept_payment(payment);
//                                 break;
//                             }
//                         }
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                     }

//                     if (found == true) {
//                         continue;
//                     } else {
//                         System.out.print("Client Not Found!");
//                         continue;
//                     }
//                 }
//                 case "9": { // Print Client Invoices
//                     System.out.print("Input Client ID: ");
//                     int client_id;
//                     try {
//                         client_id = Integer.parseInt(scanner.nextLine());
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }
//                     boolean found = false;
//                     for (Client c : Client.master_client_list) {
//                         if (c.get_uid() == client_id) {
//                             System.out.print(c.get_transaction_history());
//                             found = true;
//                             break;
//                         }
//                     }
//                     if (found == false) {
//                         System.out.print("Client ID not found!");
//                     }
//                     continue;
//                 }
//                 case "10": { // Recieve Product Shipment
//                     System.out.print("Input Product ID: ");
//                     boolean found = false;
//                     try {
//                         int product_id = Integer.parseInt(scanner.nextLine());
//                         for (Product p : Product.master_product_list) {
//                             if (p.get_uid() == product_id) {
//                                 System.out.print("Input Product Quantity: ");
//                                 p.set_qty(Integer.parseInt(scanner.nextLine()));
//                                 Client.check_client_waitlists(p);
//                                 found = true;
//                                 break;
//                             }
//                         }
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }
//                     if (found == false) {
//                         System.out.print("Product ID Not Found!");
//                     }
//                     continue;
//                 }
//                 case "11": { // Display Client Waitlist
//                     System.out.print("Input Client ID: ");
//                     int client_id;
//                     try {
//                         client_id = Integer.parseInt(scanner.nextLine());
//                     } catch (NumberFormatException e) {
//                         System.out.print("Invalid Input");
//                         continue;
//                     }
//                     boolean found = false;
//                     for (Client c : Client.master_client_list) {
//                         if (c.get_uid() == client_id) {
//                             c.display_waitlist();
//                             found = true;
//                             break;
//                         }
//                     }
//                     if (found == false) {
//                         System.out.print("Client ID not found!");
//                     }
//                     continue;
//                 }
//                 default:
//                     System.out.print("Invalid Input!");
//                     continue;
//             }
//         }

//         System.out.print("Goodbye!\n");
//         scanner.close();
//     }
// }