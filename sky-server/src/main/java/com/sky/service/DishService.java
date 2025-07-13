package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);
    //void addFlavor(DishFlavor dishFlavor);
    List<Dish> list(Long categoryId);

    DishVO selectDishById(Long id);

    void status(String status, String id);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void deleteById(List<Long> ids);

    void updataById(DishDTO dishDTO);

    List<DishVO> listWithFlavor(Dish dish);
}
