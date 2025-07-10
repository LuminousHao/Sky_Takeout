package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * From category where type = #{type}")
    List<Category> list(String type);

    @Delete("delete from category where id = #{id}")
    void delete(String id);

    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "VALUE (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addCategory(Category category);

    @Update("update category set status = #{status} where id = #{id}")
    void status(String status, String id);

    Page<Category> page(CategoryPageQueryDTO page);

    @Update("update category set type = #{type},name = #{name},sort = #{sort} where id = #{id}")
    void updataCateggory(CategoryDTO categoryDTO);
}
