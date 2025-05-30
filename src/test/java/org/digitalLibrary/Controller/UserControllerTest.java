package org.digitalLibrary.Controller;

import org.digitalLibrary.Adapter.UserAdapter;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.entities.input.UserInputentity;
import org.digitalLibrary.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.Optional;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
        @Mock
        private UserAdapter userAdapter;
        @InjectMocks
        private  UserController userController;

        private static UserInputentity userInputentity;
        private static UserModel userModel;
        private static List<UserModel> usermodellist;
        private static long id;

        @BeforeAll
        static void set(){
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

            usermodellist = new ArrayList<>();
            usermodellist.add(userModel);
        }
        @Test
    @DisplayName("Add user should return REsponseEntity with USerinputEntity as input ")
    void testaddUser(){
            Mockito.when(userAdapter.saveuser(userInputentity)).thenReturn(userModel);
            ResponseEntity<UserModel> response = userController.saveuser(userInputentity);
            Assertions.assertEquals(userModel, response.getBody());
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
    @DisplayName("Findby id returns a ResponseEntity with id as input")
    void testfindbyId(){
            Mockito.when(userAdapter.findById(id)).thenReturn(userModel);
            ResponseEntity<UserModel> response = userController.findbyId(id);
            Assertions.assertEquals(userModel, response.getBody());
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        }
        @Test
    @DisplayName("Update should returns a REsponseEntity with USerinputEntity as input")
    void testupdate(){
            Mockito.when(userAdapter.updateuser(userInputentity)).thenReturn(userModel);
            ResponseEntity<UserModel> response = userController.updateuser(userInputentity);
            Assertions.assertEquals(userModel, response.getBody());
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }
        @Test
    @DisplayName("Deleteuser should returns a REsponseEntity with USerinputEntity as userid")
    void testdelete(){
            Mockito.when(userAdapter.deleteuserbyId(id)).thenReturn(userModel);
            ResponseEntity<UserModel> response = userController.deleteuser(id);
            Assertions.assertEquals(userModel, response.getBody());
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }
    @Test
    @DisplayName("getallusers should returns a List<BookModel>")
    void testgetallusers(){
        Mockito.when(userAdapter.getallusers()).thenReturn(usermodellist);
        List<UserModel> actual = userController.getallusers();
        Assertions.assertEquals(usermodellist, actual);
    }
    @Test
    @DisplayName("FindbyID if id not found should throw Resourcen=NotFoundException")
    void testfindbyIdNotFound(){
            long nonexistingid = 111L;
        Mockito.when(userAdapter.findById(nonexistingid)).thenThrow(new ResourcenotFoundException(UserController.class, "nonexistinid", String.valueOf(nonexistingid)));
        Assertions.assertThrows(ResourcenotFoundException.class ,() -> userController.findbyId(nonexistingid));
    }
        @Test
    @DisplayName("Deletid if id not found should throw Resourcen=NotFoundException")
        void testdeleteidnotFound(){
            long nonexistingid = 111L;
            Mockito.when(userAdapter.deleteuserbyId(nonexistingid)).thenThrow(new ResourcenotFoundException(UserController.class, "nonexistinid", String.valueOf(nonexistingid)));
            Assertions.assertThrows(ResourcenotFoundException.class, () -> userController.deleteuser(nonexistingid));
        }

}
