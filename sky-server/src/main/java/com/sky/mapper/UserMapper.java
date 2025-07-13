package com.sky.mapper;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid = #{openid}")
    User getUserById(String openid);

//    @Insert("insert into user values (openid,name,phone,sex,id_number,avatar,create_time)")
    void addUser(User user);
}
