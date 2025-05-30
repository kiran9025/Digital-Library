package org.digitalLibrary.Service;

import org.digitalLibrary.Services.UserService;
import org.digitalLibrary.enums.USER_ROLE;
import org.digitalLibrary.model.UserModel;
import org.digitalLibrary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserService userService;
    private static UserModel userModel;
    private static long id;
    private static List<UserModel> userModelList;
    @BeforeAll
    static void setup(){
        userModel = UserModel.builder()
                .userid(1L)
                .firstname("kiran")
                .lastname("kumar")
                .email("kiran2002@gamail.com")
                .dateofbirth(Instant.now())
                .phonenumber(1234567788)
                .password("encodedpassword")
                .role(USER_ROLE.USER)
                .build();
        id = 1L;
        userModelList = new ArrayList<>();
        userModelList.add(userModel);
    }
    @Test
    @DisplayName("Save user should return a usermodel wit input usermodel ")
    void testsaveuser(){
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        UserModel actualoutput = userRepository.save(userModel);
        Assertions.assertEquals(actualoutput,userModel);
    }
    @Test
    @DisplayName("FindById should return the usermodel for input id")
    void testfindById() {
        Mockito.when(userRepository.findbyId(1L)).thenReturn(userModel);
        UserModel actualoutput = userRepository.findbyId(1L);
        Assertions.assertEquals(actualoutput, userModel);
    }

    @Test
    @DisplayName("UpdateUser should return updated usermodel")
    void testupdateuser() {
        Mockito.when(userRepository.udateUser(userModel)).thenReturn(userModel);
        UserModel actualoutput = userRepository.udateUser(userModel);
        Assertions.assertEquals(actualoutput, userModel);
    }

    @Test
    @DisplayName("DeleteUserById should return deleted usermodel")
    void testdeleteuserbyId() {
        Mockito.when(userRepository.deleteUserById(1L)).thenReturn(userModel);
        UserModel actualoutput = userRepository.deleteUserById(1L);
        Assertions.assertEquals(actualoutput, userModel);
    }

    @Test
    @DisplayName("GetAllUsers should return list of usermodels")
    void testgetallusers() {
        Mockito.when(userRepository.getallusers()).thenReturn(userModelList);
        List<UserModel> actualoutput = userRepository.getallusers();
        Assertions.assertEquals(actualoutput, userModelList);
    }


}
