package br.com.zup.credicardzero

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class ProposalApplication

fun main(args: Array<String>) {
    runApplication<ProposalApplication>(*args)
}
