package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {


    @Insert("insert into dish(category_id, name,image,price,description,status) values (#{categoryId}," +
            "#{name},#{image},#{price},#{description},#{status})")
    void addDish(DishDTO dishDTO);

    void addFlavor(List<DishFlavor> dishFlavor);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> list(String categoryId);

    @Select("select * from dish where id = #{id}")
    DishVO selectDishById(String id);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> selectDishFlavorById(String id);

    @Update("update dish set status = #{status} where id = #{id}")
    void status(String status, String id);

    Page<Dish> page(DishPageQueryDTO dishPageQueryDTO);
}
