package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;


@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private Properties pageHelperProperties;

    @Override
    public void addDish(DishDTO dishDTO) {
        dishMapper.addDish(dishDTO);
        dishMapper.addFlavor(dishDTO.getFlavors());

    }


    @Override
    public List<Dish> list(String categoryId) {
        List<Dish> list = dishMapper.list(categoryId);
        return list;
    }

    @Override
    public DishVO selectDishById(String id) {
        List<DishFlavor> dishFlavors = dishMapper.selectDishFlavorById(id);
        DishVO dishVOS = dishMapper.selectDishById(id);
        dishVOS.setFlavors(dishFlavors);
        return dishVOS;
    }

    @Override
    public void status(String status, String id) {
        dishMapper.status(status,id);
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.page(dishPageQueryDTO);

        long total = page.getTotal();
        List<Dish> result = page.getResult();
        return new PageResult(total,result);

    }
}
