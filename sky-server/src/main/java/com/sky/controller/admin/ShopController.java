package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 修改营业状态
     */
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable String status){
        shopService.setStatus(status);
        ValueOperations hashOperations = redisTemplate.opsForValue();
        hashOperations.set("SHOP_STATUS",status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<String> getStatus(){
        String  shopStatus = (String) redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(shopStatus);
    }



}
