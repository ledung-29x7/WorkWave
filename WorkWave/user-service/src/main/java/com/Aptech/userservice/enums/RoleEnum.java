package com.Aptech.userservice.enums;

public enum RoleEnum {
    ADMIN(1, "Admin"),
    PRODUCT_OWNER(2, "Product Owner"),
    SCRUM_MASTER(3, "Scrum Master"),
    DEVELOPER(4, "Developer"),
    TESTER(5, "Tester"),
    VIEWER(6, "Viewer"),
    SUPER_ADMIN(7, "Super Admin"),
    GENERAL_USER(8, "General User");

    private final int roleId;
    private final String roleName;

    RoleEnum(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public static RoleEnum fromId(int id) {
        for (RoleEnum r : values()) {
            if (r.roleId == id) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid RoleId: " + id);
    }

    public static RoleEnum fromName(String name) {
        for (RoleEnum r : values()) {
            if (r.roleName.equalsIgnoreCase(name)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid RoleName: " + name);
    }
}
