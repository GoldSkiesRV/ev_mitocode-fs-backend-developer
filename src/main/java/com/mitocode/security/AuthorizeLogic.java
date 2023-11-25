package com.mitocode.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizeLogic {

    public boolean hasAccess(String path){
        boolean rpta = false;

        String methodRole = switch (path){
            case "findAll" -> "ADMIN";
            case "findById" -> "USER,DBA";
            default -> "ROOT";
        };

        String methodRoles[] = methodRole.split(",");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username is: " + auth.getName());

        for(GrantedAuthority gra : auth.getAuthorities()){
            String rolUser = gra.getAuthority();
            log.info("Role: " + rolUser);

            for(String rolMet: methodRoles){
                if(rolUser.equalsIgnoreCase(rolMet)){
                    rpta = true;
                    break;
                }
            }
        }

        return rpta;
    }
}
