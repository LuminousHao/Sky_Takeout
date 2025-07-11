package com.sky.mapper;


import com.sky.dto.DishDTO;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {


    @Insert("insert into dish(category_id, name,image,price,description,status) values (#{categoryId}," +
            "#{name},#{image},#{price},#{description},#{status})")
    void addDish(DishDTO dishDTO);
    @Insert("insert into dish_flavor(dish_id,id,name,value) values (#{dishId},#{id},#{name},#{value})")
    void addFlavor(DishFlavor dishFlavor);
}
