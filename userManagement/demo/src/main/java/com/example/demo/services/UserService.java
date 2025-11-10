package com.example.demo.services;


import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.dtos.builders.UserBuilder;
import com.example.demo.entities.User;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //findAll
    public List<UserDetailsDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDetailsDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getUser_id());
        return user.getUser_id();
    }

    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }

        userRepository.deleteById(id);
        LOGGER.info("User with id {} has been deleted successfully", id);
    }

    public UserDetailsDTO updateUser(UUID id, UserDetailsDTO userDetailsDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            LOGGER.error("User with id {} not found", id);
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }

        User user = userOptional.get();

        // Actualizam câmpurile
        if (userDetailsDTO.getFirstName() != null) user.setFirstName(userDetailsDTO.getFirstName());
        if (userDetailsDTO.getLastName() != null) user.setLastName(userDetailsDTO.getLastName());
        if (userDetailsDTO.getEmail() != null) user.setEmail(userDetailsDTO.getEmail());
        if (userDetailsDTO.getPhoneNumber() != null) user.setPhoneNumber(userDetailsDTO.getPhoneNumber());
        if (userDetailsDTO.getAddress() != null) user.setAddress(userDetailsDTO.getAddress());
        if (userDetailsDTO.getBirthDate() != null) user.setBirthDate(userDetailsDTO.getBirthDate());


        User updatedUser = userRepository.save(user);

        LOGGER.info("User with id {} was updated successfully", id);

        return UserBuilder.toUserDetailsDTO(updatedUser);
    }

}
