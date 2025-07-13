package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    /**
     * 修改菜品信息
     */
    @PutMapping
    public Result updataById(@RequestBody DishDTO dishDTO){
        dishService.updataById(dishDTO);
        return Result.success();
    }

    /**
     * 删除菜品
     */
    @DeleteMapping
    public Result deleteById(@RequestParam List<Long> ids){
        dishService.deleteById(ids);
        return Result.success();

    }

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        PageResult page = dishService.page(dishPageQueryDTO);
        return Result.success(page);
    }
    /**
     * 修改菜品状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result status(@PathVariable String status,@RequestParam String id){
        dishService.status(status,id);
        return Result.success("修改成功");
    }

    /**
     * 根据Id查询菜品
     */
    @GetMapping("/{id}")
    public Result selectDishById(@PathVariable Long id){
        DishVO dishVOS = dishService.selectDishById(id);
        return Result.success(dishVOS);
    }
    /**
     * 根据种类查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }

}
