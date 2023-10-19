package com.akshayram.cloudstorage.mapper;

import com.akshayram.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/*Database mapper to perform crud ops on the users*/

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM USERS WHERE username = #{username}")
  User getUser(String username);

  @Select("SELECT * FROM USERS WHERE userid = #{userId}")
  User getUserById(Integer userId);

  @Insert(
      "INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
  @Options(useGeneratedKeys = true, keyProperty = "userId")
  int insert(User user);
}
