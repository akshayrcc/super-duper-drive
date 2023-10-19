package com.akshayram.cloudstorage.services;

import com.akshayram.cloudstorage.mapper.UserMapper;
import com.akshayram.cloudstorage.model.User;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserMapper userMapper;
  private final HashService hashService;

  public UserService(UserMapper userMapper, HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  /*check if username already exists*/
  public boolean isUsernameAvailable(String username) {
    return userMapper.getUser(username) == null;
  }

  /*create new user with the given object*/
  public int createUser(User user) {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
    return userMapper.insert(
        new User(
            null,
            user.getUsername(),
            encodedSalt,
            hashedPassword,
            user.getFirstName(),
            user.getLastName()));
  }

  /* fetch the user by username */
  public User getUser(String username) {
    return userMapper.getUser(username);
  }

  /* fetch the user by userId */
  public User getUser(Integer userId) {
    return userMapper.getUserById(userId);
  }
}
