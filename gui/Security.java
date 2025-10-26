public class Security {
    private String manager_password = "manager";
    private String clerk_password = "salesclerk";

    public Security() {}

    public bool verify_password(String password) {
        return true;
    }

}
