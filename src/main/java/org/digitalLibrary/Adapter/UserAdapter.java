package org.digitalLibrary.Adapter;

import org.digitalLibrary.Mappers.Input.UserInputmapper;
import org.digitalLibrary.Services.UserService;
import org.digitalLibrary.entities.input.UserInputentity;
import org.digitalLibrary.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class UserAdapter {
    @Autowired
    private final UserInputmapper userInputmapper;
    private final UserService userService;

    public UserAdapter(UserInputmapper userInputmapper, UserService userService) {
        this.userInputmapper = userInputmapper;
        this.userService = userService;
    }
    public UserModel saveuser(UserInputentity userInputentity){
        return this.userService.saveuser(this.userInputmapper.maptoModel(userInputentity));
    }
    public UserModel findById(long id){
        return this.userService.findById(id);
    }
    public UserModel updateuser(UserInputentity userInputentity){
        return this.userService.updateuser(this.userInputmapper.maptoModel(userInputentity));
    }
    public UserModel deleteuserbyId(long id){
        return this.userService.deleteuserbyId(id);
    }
    public List<UserModel> getallusers(){
        return this.userService.getallusers();
    }

}
