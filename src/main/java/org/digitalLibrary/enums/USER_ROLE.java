package org.digitalLibrary.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum USER_ROLE {
    USER("USER"),
    ADMIN("ADMIN");

     private final String role;

     USER_ROLE(String role){
         this.role= role;
     }

     @JsonValue
    public String getRole(){
         return this.role;
     }
    @JsonCreator
    public static USER_ROLE fromString(String role){
        for(USER_ROLE user : USER_ROLE.values()){
            if(user.getRole().equalsIgnoreCase(role)){
                return user;
            }
        }
        throw new IllegalArgumentException("Unkonw status " + role);
    }

}
