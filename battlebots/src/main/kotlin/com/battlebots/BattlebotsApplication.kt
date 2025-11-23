package com.battlebots

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BattlebotsApplication

fun main(args: Array<String>) {
    runApplication<BattlebotsApplication>(*args)
}

