package com.Aptech.userservice.enums;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;

public class PermissionCode {
    // Permission Management
    public static final String PERMISSION_CREATE = "PERMISSION_CREATE";
    public static final String PERMISSION_EDIT = "PERMISSION_EDIT";
    public static final String PERMISSION_DELETE = "PERMISSION_DELETE";

    // Project
    public static final String PROJECT_CREATE = "PROJECT_CREATE";
    public static final String PROJECT_EDIT = "PROJECT_EDIT";
    public static final String PROJECT_DELETE = "PROJECT_DELETE";
    public static final String PROJECT_VIEW_MEMBER = "PROJECT_VIEW_MEMBER";
    public static final String PROJECT_ASSIGN_MEMBER = "PROJECT_ASSIGN_MEMBER";
    public static final String PROJECT_UPDATE_ROLE = "PROJECT_UPDATE_ROLE";
    public static final String PROJECT_REMOVE_MEMBER = "PROJECT_REMOVE_MEMBER";

    // Role
    public static final String ROLE_CREATE = "ROLE_CREATE";
    public static final String ROLE_EDIT = "ROLE_EDIT";
    public static final String ROLE_DELETE = "ROLE_DELETE";
    public static final String ROLE_VIEW_PERMISSION = "ROLE_VIEW_PERMISSION";
    public static final String ROLE_ASSIGN_PERMISSION = "ROLE_ASSIGN_PERMISSION";
    public static final String ROLE_REMOVE_PERMISSION = "ROLE_REMOVE_PERMISSION";

    // User
    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_VIEW = "USER_VIEW";
    public static final String USER_EDIT = "USER_EDIT";
    public static final String USER_DELETE = "USER_DELETE";
    public static final String USER_SEARCH = "USER_SEARCH";
    public static final String USER_VIEW_ALL = "USER_VIEW_ALL";
}
