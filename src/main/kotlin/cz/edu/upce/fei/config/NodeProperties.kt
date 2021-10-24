package cz.edu.upce.fei.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConstructorBinding
@ConfigurationProperties(prefix = "config")
class NodeProperties(
    var serverUrl: String = "",
    var device: Device? = null,
    val sensors: List<Sensor> = mutableListOf()
) {
    data class Device(
        var id: Long,
        var name: String
    )

    data class Sensor(
        var id: Long,
        var name: String,
        var dataType: String
    )
}
