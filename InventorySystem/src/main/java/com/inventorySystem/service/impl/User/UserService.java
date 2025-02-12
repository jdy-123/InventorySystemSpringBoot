package com.inventorySystem.service.impl.User;

import com.inventorySystem.dto.UserDto;
import com.inventorySystem.Model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByUsername(String username);

    List<User> findAllUsers();

    void deleteUserById(Long id);
}
