package org.digitalLibrary.Services;

import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.UserPrincipal;
import org.digitalLibrary.model.UserModel;
import org.digitalLibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserModel saveuser(UserModel userModel){
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        return this.userRepository.save(userModel);
    }

    public UserModel findById(long id){
        return this.userRepository.findbyId(id);
    }
    public UserModel updateuser(UserModel userModel){
        return this.userRepository.udateUser(userModel);
    }
    public UserModel deleteuserbyId(long id){
     return this.userRepository.deleteUserById(id);
    }

    public List<UserModel> getallusers(){
        return this.userRepository.getallusers();
    }
    public UserModel findbyEmail(String email){
        return this.userRepository.findbyEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            UserModel userModel = this.userRepository.findbyEmail(username);
            return new UserPrincipal(userModel);
        }catch (ResourcenotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
