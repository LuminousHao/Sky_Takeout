package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dishDTO);

    List<Dish> list(Dish dish);

    @Select("select * from dish where id = #{id}")
    Dish selectDishById(Long id);


    @Update("update dish set status = #{status} where id = #{id}")
    void status(String status, String id);

    Page<Dish> page(DishPageQueryDTO dishPageQueryDTO);

    void deletById(List<Long> ids);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    void updataById(Dish dish);
}
