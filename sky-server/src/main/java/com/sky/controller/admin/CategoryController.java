package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PutMapping()
    public Result upataCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.updataCategory(categoryDTO);
        return Result.success();
    }
    /**
     * 菜品分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult page = categoryService.page(categoryPageQueryDTO);
        return Result.success(page);
    }

    /**
     * 更改菜品状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result status(@PathVariable String status,@RequestParam String id){
        categoryService.status(status,id);
        return  Result.success();
    }
    /**
     * 新增菜品
     * @param categoryDTO
     * @return
     */
    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }
    /**
     * 根据id删除种类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam String id){
        categoryService.delete(id);
        return Result.success();
    }
    /**
     * 根据分类类型查询种类
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam  String type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }






}
