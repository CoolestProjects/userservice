package security;

/**
 * Created by noelking on 12/26/14.
 */
public enum UserRoles {

    ADMIN("ROLE_CP_ADMIN"),
    CHAMPION("ROLE_CHAMPION"),
    USER("ROLE_USER");

    private String name;
    UserRoles(final String name) {
        this.name = name;
    }

    public String getRoleName() {
        return name;
    }
}
