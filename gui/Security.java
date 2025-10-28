public class Security {
    private String manager_password;
    private String clerk_password;

    public enum Entity {MANAGER, CLERK, CLIENT}

    public Security(String manager_password, String clerk_password) {
        this.manager_password = manager_password;
        this.clerk_password = clerk_password;
    }

    public boolean verify_password(Entity ent) {
        boolean ret = false;

        Context C = Context.get_instance();
        C.clear_console();

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
                C.print("Input Client ID:");
                pw = get_client_password(C.input());
                break;
            }
            default: {
                ret = false;
                break;
            }
        }

        if (pw != null) {
            C.print("Input Password:");
            String input = C.input();

            if (input.equals(pw)) {
                ret = true;
            }
        }

        return ret;
    }

    private String get_client_password(String client) {
        return "fake_password";
    }
}
