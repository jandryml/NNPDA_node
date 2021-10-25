package cz.edu.upce.fei.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class InitService(
    @Autowired val dataSendingService: DataSendingService
) {
    @PostConstruct
    fun init() {
        dataSendingService.login()
    }
}