package org.digitalLibrary.Adapter;

import org.checkerframework.checker.units.qual.A;
import org.digitalLibrary.Mappers.Input.UserInputmapper;
import org.digitalLibrary.Services.UserService;
import org.digitalLibrary.entities.input.UserInputentity;
import org.digitalLibrary.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserAdapterTest {
    @Mock
    private UserInputmapper userInputmapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserAdapter userAdapter;

    private static UserInputentity userInputentity;
    private static UserModel userModel;
    private static long id;
    private static List<UserModel> userModellist;

    @BeforeAll
    static void setup(){
        id = 1L;
        userInputentity = UserInputentity.builder()
                .userid(1L)
                .firstname("kiran")
                .lastname("kumar")
                .email("kiran2002@gamail.com")
                .dateofbirth(Instant.now())
                .phonenumber(1234567788)
                .build();
        userModel = UserModel.builder()
                .userid(1L)
                .firstname("kiran")
                .lastname("kumar")
                .email("kiran2002@gamail.com")
                .dateofbirth(Instant.now())
                .phonenumber(1234567788)
                .build();

        userModellist = new ArrayList<>();
        userModellist.add(userModel);

    }

    @Test
    @DisplayName("SaveUser should return a UserModel with UserInputEntity has input")
    void testsaveUser(){
        Mockito.when(userInputmapper.maptoModel(userInputentity)).thenReturn(userModel);
        Mockito.when(userService.saveuser(userModel)).thenReturn(userModel);
        UserModel actualoutput = userAdapter.saveuser(userInputentity);
        Assertions.assertEquals(actualoutput, userModel);
    }
    @Test
    @DisplayName("Test FindbyId should return a UserModel ")
    void testfindbyId(){
        Mockito.when(userService.findById(id)).thenReturn(userModel);
        UserModel actualoutput = userAdapter.findById(id);
        Assertions.assertEquals(actualoutput, userModel);
    }

    @Test
    @DisplayName("Update with userinputentity should return usermodel")
    void testupdate(){
        Mockito.when(userInputmapper.maptoModel(userInputentity)).thenReturn(userModel);
        Mockito.when(userService.updateuser(userModel)).thenReturn(userModel);
        UserModel actualoutput  = userAdapter.updateuser(userInputentity);
        Assertions.assertEquals(actualoutput, userModel);
    }

    @Test
    @DisplayName("getallusers should return List<usermodel>")
    void testgetalluser(){
        Mockito.when(userService.getallusers()).thenReturn(userModellist);
        List<UserModel> actualoutput = userAdapter.getallusers();
        Assertions.assertEquals(actualoutput, userModellist);
    }
    @Test
    @DisplayName("deleteuser with id should return usermodel")
    void testdelete(){
        Mockito.when(userService.deleteuserbyId(id)).thenReturn(userModel);
        UserModel actualoutput  = userAdapter.deleteuserbyId(id);
        Assertions.assertEquals(actualoutput, userModel);
    }

}
