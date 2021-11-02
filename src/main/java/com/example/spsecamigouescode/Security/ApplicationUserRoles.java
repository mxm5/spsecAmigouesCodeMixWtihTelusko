package com.example.spsecamigouescode.Security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.spsecamigouescode.Security.ApplicationUserPermissions.*;

public enum ApplicationUserRoles {
    STUDENT(
            Sets.newHashSet()
    ),
    ADMINTRAINEE(
            Sets.newHashSet(COURSE_READ, STUDENT_READ)

    ),
    ADMIN(
            Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)
    );

    private final Set<ApplicationUserPermissions> permissions;

    ApplicationUserRoles(Set<ApplicationUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermissions> getPermissions() {
        return permissions;
    }

    Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream().map(
                applicationUserPermissions -> new SimpleGrantedAuthority(applicationUserPermissions.getPermission())

        ).collect(Collectors.toSet());
//        and the role_name must be a simple granted authority to the all authorities
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
