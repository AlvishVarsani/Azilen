package com.azilen.training.service;

import com.azilen.training.dto.LoginDTO;
import com.azilen.training.dto.RegisterDTO;
import com.azilen.training.dto.UserUpdateDTO;
import com.azilen.training.entity.User;
import com.azilen.training.entity.UserDetails;
import com.azilen.training.repository.UserDetailsRepository;
import com.azilen.training.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class UserService {
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    public UserService(UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }

    private UserDetails convertDtoToUserDetails(RegisterDTO signupDto) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(signupDto.getEmail());
        userDetails.setFirstName(signupDto.getFirstName());
        userDetails.setLastName(signupDto.getLastName());
        return userDetails;
    }

    private User convertDtoToUser(RegisterDTO registerDTO, UserDetails userDetails) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setUserDetails(userDetails);
        return user;
    }

    public Boolean signUpUser(RegisterDTO registerDTO) throws Exception {

        try {
            if(userRepository.findByUsername(registerDTO.getUsername())!=null){
                throw new IllegalArgumentException("User already exists");
            }
            if(findByEmail(registerDTO.getEmail())!=null){
                throw new IllegalArgumentException("Email already exists");
            }
            if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
                throw new IllegalArgumentException("Passwords and confirm passwords doesn't match");
            }

            UserDetails userDetails = convertDtoToUserDetails(registerDTO);
            UserDetails savedUserDetails = userDetailsRepository.save(userDetails);
            User user = convertDtoToUser(registerDTO, savedUserDetails);
            userRepository.save(user);
            return true;
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Boolean loginUser(LoginDTO loginDto) {
            User user = userRepository.findByUsername(loginDto.getUsername());
           if(user==null){
                return false;
            }
        return user.getPassword().equals(loginDto.getPassword());
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDetails findByEmail(String email){
        return userDetailsRepository.findByEmail(email);
    }

    public boolean updateUserProfile(User user, UserUpdateDTO updateDto) throws Exception {

        UserDetails existingWithEmail = findByEmail(updateDto.getEmail());
        if (existingWithEmail != null ) {
            throw new IllegalArgumentException("Email already exists");
        }

        try {
            UserDetails details = user.getUserDetails();
            details.setFirstName(updateDto.getFirstName());
            details.setLastName(updateDto.getLastName());
            details.setEmail(updateDto.getEmail());
            details.setUpdatedBy(details.getId());
            details.setUpdatedAt(LocalDateTime.now());
            userDetailsRepository.save(details);
            return true;
        } catch (IllegalArgumentException e) {
           throw  new IllegalArgumentException(e.getMessage());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean matchesOldPassword(User user, String oldPassword) {
        return user.getPassword().equals(oldPassword);
    }

    public boolean changePassword(User user, String newPassword) throws Exception {
        try {
            user.setPassword(newPassword);
            user.setUpdatedAt(LocalDateTime.now());
            user.setUpdatedBy(user.getUserDetails().getId());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

