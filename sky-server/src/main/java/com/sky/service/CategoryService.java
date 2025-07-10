package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    List<Category> list(String type);

    void delete(String id);

    void addCategory(CategoryDTO categoryDTO);

    void status(String type, String id);

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void updataCategory(CategoryDTO categoryDTO);
}
