package org.digitalLibrary.Controller;

import jakarta.validation.Valid;
import org.digitalLibrary.Adapter.UserAdapter;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.entities.input.UserInputentity;
import org.digitalLibrary.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserAdapter userAdapter;

    @Autowired
    public UserController(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    @PostMapping("/adduser")
    public ResponseEntity<UserModel> saveuser(@Valid @RequestBody UserInputentity userInputentity){
        return new ResponseEntity<UserModel>(this.userAdapter.saveuser(userInputentity), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findbyId(@PathVariable long id){
        try {
            UserModel userModel = this.userAdapter.findById(id);
            return new ResponseEntity<>(userModel, HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
           throw new ResourcenotFoundException(UserController.class, "id", String.valueOf(id));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<UserModel> updateuser(@Valid @RequestBody UserInputentity userInputentity){
        return new ResponseEntity<>(this.userAdapter.updateuser(userInputentity),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserModel>deleteuser(@PathVariable long id){
        try{
            UserModel user = this.userAdapter.deleteuserbyId(id);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
            throw new ResourcenotFoundException(UserController.class, "id", String.valueOf(id));
        }
    }
    @GetMapping("getallusers")
    public List<UserModel> getallusers(){
        return this.userAdapter.getallusers();
    }

}




