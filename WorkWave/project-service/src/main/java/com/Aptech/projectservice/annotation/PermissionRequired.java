package com.Aptech.projectservice.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRequired {
    String value(); // Mã quyền, ví dụ "EPIC_CREATE", "SPRINT_DELETE"...
}
