package org.digitalLibrary.Mappers.output;

import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserOutputMapper {
    public UserModel maptoModel(UserOutputentity userOutputentity){
        return UserModel.builder()
                .userid(userOutputentity.getUserid())
                .firstname(userOutputentity.getFirstname())
                .lastname(userOutputentity.getLastname())
                .email(userOutputentity.getEmail())
                .dateofbirth(userOutputentity.getDateofbirth())
                .phonenumber(userOutputentity.getPhonenumber())
                .password(userOutputentity.getPassword())
                .role(userOutputentity.getRole())
                .build();
    }

    public UserOutputentity mapfromModel(UserModel userModel){
        return UserOutputentity.builder()
                .userid(userModel.getUserid())
                .firstname(userModel.getFirstname())
                .lastname(userModel.getLastname())
                .email(userModel.getEmail())
                .dateofbirth(userModel.getDateofbirth())
                .phonenumber(userModel.getPhonenumber())
                .password(userModel.getPassword())
                .role(userModel.getRole())
                .build();
    }
}
