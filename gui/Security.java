public class Security {
    // Default passwords
    private String manager_password;
    private String clerk_password;


    // Primary Constructor
    public Security(String manager_password, String clerk_password) {
        this.manager_password = manager_password;
        this.clerk_password = clerk_password;
    }

    // Verifies the password of an Entity
    public enum Entity {MANAGER, CLERK, CLIENT}
    public boolean verify_password(Entity ent) {
        boolean ret = false;

        Context C = Context.get_instance();
        C.clear_console();

        int[] client_id = {-1};

        String pw = null;
        switch (ent) {
            case MANAGER: {
                pw = this.manager_password;
                break;
            }
            case CLERK: {
                pw = this.clerk_password;
                break;
            }
            case CLIENT: {
                pw = get_client_password(client_id); // Java wont pass by reference on primitives. The world should have let this language die and just used C++. At least its better than Rust.
                break;
            }
            default: {
                ret = false;
                break;
            }
        }

        if (pw != null) {
            String input = C.input("Input Password:", true);

            if (input.equals(pw)) {
                ret = true;

                if (ent == Entity.CLIENT) {
                    Client_State.set_current_client_id(client_id[0]);
                }

            }
        }

        return ret;
    }

    // Used to fetch client specific passwords
    private String get_client_password(int[] client_id) {
        Context C = Context.get_instance();

        try {
            client_id[0] = Integer.parseInt(C.input("Input Client ID:", true)); // Will this scope how I want it to?

            for (Client client : Client.master_client_list) {
                if (client.get_uid() == client_id[0]) {
                    return client.get_password(); // needs to return C.get_password();
                }
            }
        } catch (NumberFormatException e) {
            C.print("That wasn't a number. How did you mess that up? This shouldn't be that difficult.");
        }

        C.print("Couldn't find that one!");

        C.wait_a_sec();

        return null;
    }
}
