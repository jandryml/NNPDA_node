package cz.edu.upce.fei

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NnpdaNodeApplication

fun main(args: Array<String>) {
    runApplication<NnpdaNodeApplication>(*args)
}
