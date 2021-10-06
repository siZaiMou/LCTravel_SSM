package com.travel.web.controller;

import com.travel.domain.Category;
import com.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Category> findAll()
    {
        return categoryService.findAll();
    }
}
