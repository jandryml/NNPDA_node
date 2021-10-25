package cz.edu.upce.fei.service

import cz.edu.upce.fei.config.NodeProperties
import cz.edu.upce.fei.model.SensorData
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class DataGeneratorService(
    private val nodeProperties: NodeProperties,
    private val dataSendingService: DataSendingService
) {
    val dateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS")

    @Scheduled(cron = "0 */15 * * * *")
    fun sendData() {
        nodeProperties.sensors.forEach {
            val data = SensorData(it.dataType, generateRandomValue().toString(), it.id, dateFormat.format(Date()))
            println("Sending data for : ${it.id} - ${it.name} : $data")
            dataSendingService.postData(
                data,
                "/api/sensor-data"
            )
        }
    }

    fun generateRandomValue() = (Math.random() * 100).toLong()
}