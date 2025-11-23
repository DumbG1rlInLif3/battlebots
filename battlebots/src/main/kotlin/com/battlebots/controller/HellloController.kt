package com.battlebots.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HelloController {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "🤖 Olá, piloto! O sistema BattleBots está online!"
    }
}