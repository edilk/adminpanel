package com.example.adminpanel.service;

import com.example.adminpanel.dto.UserDTO;
import com.example.adminpanel.entity.User;
import com.example.adminpanel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
