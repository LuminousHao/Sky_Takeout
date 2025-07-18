package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list != null && !list.isEmpty()){
            ShoppingCart cart = list.get(0);
//            Long dishId = cart.getDishId();
//            Long setmealId = cart.getSetmealId();
            cart.setNumber(cart.getNumber() + 1);
//            if(dishId != null){
//                Dish dish = dishMapper.getById(dishId);
//                cart.setAmount(cart.getAmount().add(dish.getPrice()));
//            }else {
//                Setmeal setmeal = setmealMapper.getById(setmealId);
//                cart.setAmount(cart.getAmount().add(setmeal.getPrice()));
//            }
            shoppingCartMapper.updateNumberById(cart);

        }else {
            Long setmealId = shoppingCartDTO.getSetmealId();
            Long dishId = shoppingCart.getDishId();
            if(dishId != null){
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setName(dish.getName());
                shoppingCart.setAmount(dish.getPrice());

            }
            if(setmealId != null){
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shoppingCartMapper.add(shoppingCart);
        }
    }

    @Override
    public void clean() {
        Long currentId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(currentId);
    }

    @Override
    public List<ShoppingCart> list() {
        Long currentId = BaseContext.getCurrentId();
        ShoppingCart cart = ShoppingCart.builder()
                .userId(currentId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(cart);
        return list;
    }

    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //设置查询条件，查询当前登录用户的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list != null && list.size() > 0){
            shoppingCart = list.get(0);

            Integer number = shoppingCart.getNumber();
            if(number == 1){
                //当前商品在购物车中的份数为1，直接删除当前记录
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }else {
                //当前商品在购物车中的份数不为1，修改份数即可
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }
        }
    }




}
