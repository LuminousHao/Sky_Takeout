package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private FlavorMapper flavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.addDish(dish);

        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if(flavors != null && !flavors.isEmpty()){
            flavors.forEach(dishFlavor -> {dishFlavor.setDishId(id);});
            flavorMapper.addFlavor(flavors);
        }


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

    @Override
    public void deleteById(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(Objects.equals(dish.getStatus(), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> setmealId = setmealDishMapper.getById(ids);
        if(setmealId != null && !setmealId.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deletById(ids);
    }

    @Transactional
    @Override
    public void updataById(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        Long id = dishDTO.getId();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if(dishFlavors != null && !dishFlavors.isEmpty()){
            flavorMapper.deleteById(id);
            dishFlavors.forEach(dishFlavor -> {dishFlavor.setDishId(id);});
            flavorMapper.addFlavor(dishFlavors);
        }
        dishMapper.updataById(dish);
    }
}
