package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dishDTO);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> list(String categoryId);

    @Select("select * from dish where id = #{id}")
    DishVO selectDishById(String id);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> selectDishFlavorById(String id);

    @Update("update dish set status = #{status} where id = #{id}")
    void status(String status, String id);

    Page<Dish> page(DishPageQueryDTO dishPageQueryDTO);

    void deletById(List<Long> ids);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    void updataById(Dish dish);
}
