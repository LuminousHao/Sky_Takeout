package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public void addDish(DishDTO dishDTO) {
        dishMapper.addDish(dishDTO);

    }

    @Override
    public void addFlavor(DishFlavor dishFlavor) {

    }
}
