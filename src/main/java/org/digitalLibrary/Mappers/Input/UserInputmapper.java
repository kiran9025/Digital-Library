package org.digitalLibrary.Mappers.Input;

import org.digitalLibrary.entities.input.UserInputentity;
import org.digitalLibrary.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserInputmapper {

    public UserModel maptoModel(UserInputentity userInputentity) {
        return UserModel.builder()
                .userid(userInputentity.getUserid())
                .firstname(userInputentity.getFirstname())
                .lastname(userInputentity.getLastname())
                .email(userInputentity.getEmail())
                .dateofbirth(userInputentity.getDateofbirth())
                .phonenumber(userInputentity.getPhonenumber())
                .password(userInputentity.getPassword())
                .role(userInputentity.getRole())
                .build();
    }


}
