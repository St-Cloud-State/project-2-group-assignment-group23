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

        int client_id = -1;

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
                client_id = Integer.parseInt(C.input("Input Client ID:")); // Will this scope how I want it to?
                for (Client client : Client.master_client_list) {
                    if (client.get_uid() == client_id) {
                        pw = client.get_password();
                        Client_State.set_current_client_id(client);
                    }
                }
                break;
            }
            default: {
                ret = false;
                break;
            }
        }

        if (pw != null) {
            String input = C.input("Input Password:");
            if (input.equals(pw)) {
                ret = true;
            }
        }

        return ret;
    }
}
