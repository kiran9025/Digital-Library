package org.digitalLibrary.Repository;

import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.Mappers.output.UserOutputMapper;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.model.UserModel;
import org.digitalLibrary.repository.UserRepository;
import org.digitalLibrary.repository.jpa.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserReposioryTest {
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private UserOutputMapper userOutputMapper;
    @InjectMocks
    private UserRepository userRepository;

    private static UserOutputentity userOutputentity;
    private static UserModel userModel;
    private static long id;
    private static List<UserOutputentity> userOutputentityList;
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
                .build();
        id = 1L;
        userOutputentity = UserOutputentity.builder()
                .userid(1L)
                .firstname("kiran")
                .lastname("kumar")
                .email("kiran2002@gamail.com")
                .dateofbirth(Instant.now())
                .phonenumber(1234567788)
                .build();
        userOutputentityList = new ArrayList<>();
        userOutputentityList.add(userOutputentity);

        userModelList = new ArrayList<>();
        userModelList.add(userModel);
    }
        @Test
    @DisplayName("Save User should return useroutputEntity")
        void testsaveuser(){
            Mockito.when(userOutputMapper.mapfromModel(userModel)).thenReturn(userOutputentity);
            Mockito.when(this.userJpaRepository.save(userOutputentity)).thenReturn(userOutputentity);
            Mockito.when(userOutputMapper.maptoModel(userOutputentity)).thenReturn(userModel);
            UserModel actualoutput = userRepository.save(userModel);
            Assertions.assertEquals(actualoutput, userModel);
        }
        @Test
    @DisplayName("FinduserbyID should return Optional<UserOutputEntity> if id is found")
    void testfindbyId(){
        Mockito.when(userJpaRepository.findById(id)).thenReturn(Optional.of(userOutputentity));
        Mockito.when(userOutputMapper.maptoModel(userOutputentity)).thenReturn(userModel);
        UserModel actualoutput = userRepository.findbyId(id);
        Assertions.assertEquals(actualoutput, userModel);
    }
    @Test
    @DisplayName("Update user should return UserOutputEntity ")
    void testupdateUSer(){
        Mockito.when(userOutputMapper.mapfromModel(userModel)).thenReturn(userOutputentity);
        Mockito.when(this.userJpaRepository.save(userOutputentity)).thenReturn(userOutputentity);
        Mockito.when(userOutputMapper.maptoModel(userOutputentity)).thenReturn(userModel);
        UserModel actualoutput = userRepository.udateUser(userModel);
        Assertions.assertEquals(actualoutput, userModel);
    }

    @Test
    @DisplayName("")
    void testdeleteUser(){
        Mockito.when(userJpaRepository.findById(id)).thenReturn(Optional.of(userOutputentity));
        Mockito.when(userOutputMapper.maptoModel(userOutputentity)).thenReturn(userModel);
        UserModel actualoutput  = userRepository.deleteUserById(id);
        Assertions.assertEquals(actualoutput, userModel);
        Mockito.verify(this.userJpaRepository).deleteById(id);
    }
    @Test
    @DisplayName("")
    void testgetallusers(){
        Mockito.when(userJpaRepository.findAll()).thenReturn(userOutputentityList);
        Mockito.when(userOutputMapper.maptoModel(userOutputentity)).thenReturn(userModel);
        List<UserModel> actual = userRepository.getallusers();
        Assertions.assertEquals(actual, userModelList);

    }
    @Test
    @DisplayName("FindUserById should throw ResourceNotFoundException when id is not found")
    void testFindByIdThrowsExceptionWhenIdNotFound() {
        Mockito.when(userJpaRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourcenotFoundException.class, () -> {
            userRepository.findbyId(id);
        });
    }

    @Test
    @DisplayName("DeleteUserById should throw ResourceNotFoundException when id is not found")
    void testDeleteUserByIdThrowsExceptionWhenIdNotFound() {
        Mockito.when(userJpaRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourcenotFoundException.class, () -> {
            userRepository.deleteUserById(id);
        });
    }


}

