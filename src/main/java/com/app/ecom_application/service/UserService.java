package com.app.ecom_application.service;

import com.app.ecom_application.dto.AddressDTO;
import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.model.Address;
import com.app.ecom_application.repository.UserRepository;
import com.app.ecom_application.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAdress() != null){
            Address address = new Address();
            address.setCity(userRequest.getAdress().getCity());
            address.setStreet(userRequest.getAdress().getStreet());
            address.setCountry(userRequest.getAdress().getCountry());
            address.setState(userRequest.getAdress().getState());
            address.setZipcode(userRequest.getAdress().getZipcode());
            user.setAddress(address);
        }
    }

    public Optional<UserResponse> fetchUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::mapToUserResponse);
        }

    public Boolean UpdateUser(Long id, UserRequest updatedUser){
        return userRepository.findById(id)
                .map(existingUser ->{
                    updateUserFromRequest(existingUser, updatedUser);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getStreet());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAdress(addressDTO);
        }

        return response;
    }
}


