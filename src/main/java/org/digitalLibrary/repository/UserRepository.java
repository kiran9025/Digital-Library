package org.digitalLibrary.repository;

import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.Mappers.output.UserOutputMapper;
import org.digitalLibrary.entities.output.UserOutputentity;
import org.digitalLibrary.model.UserModel;
import org.digitalLibrary.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepository {
    @Autowired
    private final UserOutputMapper userOutputMapper;
    private final UserJpaRepository userJpaRepository;

    public UserRepository(UserOutputMapper userOutputMapper, UserJpaRepository userJpaRepository) {
        this.userOutputMapper = userOutputMapper;
        this.userJpaRepository = userJpaRepository;
    }

    public UserModel save(UserModel userModel){
        UserOutputentity userOutputentity = this.userOutputMapper.mapfromModel(userModel);
        UserOutputentity savedentity = this.userJpaRepository.save(userOutputentity);
        return this.userOutputMapper.maptoModel(savedentity);

    }
    public UserModel findbyId(long id){
        Optional<UserOutputentity> optional = this.userJpaRepository.findById(id);
        return optional
                .map(this.userOutputMapper::maptoModel)
                .orElseThrow(() -> new ResourcenotFoundException(UserOutputentity.class,"id",String.valueOf(id)));

    }
    public UserModel udateUser(UserModel userModel){
        return this.save(userModel);
    }
    public UserModel deleteUserById(long id){
        Optional<UserOutputentity> optional = this.userJpaRepository.findById(id);
        if(optional.isPresent()){
            UserOutputentity delete = optional.get();
            this.userJpaRepository.deleteById(id);
            return this.userOutputMapper.maptoModel(delete);
        }else{
            throw new ResourcenotFoundException(UserOutputentity.class,"id",String.valueOf(id));
        }

    }

    public List<UserModel> getallusers(){
        List<UserOutputentity> list = this.userJpaRepository.findAll();
        return list.stream()
                .map(outputentity  -> this.userOutputMapper.maptoModel(outputentity))
                .collect(Collectors.toList());
    }

    public UserModel findbyEmail(String email){
        Optional<UserOutputentity> optional = this.userJpaRepository.findByEmail(email);
        return optional
                .map(userOutputMapper::maptoModel)
                .orElseThrow(() -> new ResourcenotFoundException(UserRepository.class, "email", email));
    }
}
