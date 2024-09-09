package com.npt.anis.ANIS.fcm.controller;

import com.npt.anis.ANIS.fcm.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redis")
public class RedisController {
    private final RedisService redisService;

//    @PostMapping("/save")
//    public String saveData() {
//        redisService.saveData("myKey", "myValue", 1000L);
//        return "Data saved";
//    }
//
//    @GetMapping("/get")
//    public String getData() {
//        return (String) redisService.getData("myKey");
//    }
//
//    @DeleteMapping("/del")
//    public String delData() {
//        if (redisService.deleteData("myKey")) {
//            return "Data deleted";
//        }
//        return "Data not found";
//    }
}
