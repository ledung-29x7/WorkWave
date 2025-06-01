package com.Aptech.releaseservice.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRequired {
    String value(); // Mã quyền, ví dụ "EPIC_CREATE", "SPRINT_DELETE"...
}
