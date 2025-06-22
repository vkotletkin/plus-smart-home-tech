package ru.practicum.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "spring.collector.kafka")
public class KafkaConfig {

    private Telemetry telemetry;

    public String getHubTopic() {
        return this.getTelemetry().getHub().getTopic();
    }

    public String getSensorTopic() {
        return this.getTelemetry().getSensor().getTopic();
    }

    @Getter
    @Setter
    @ToString
    public static class Telemetry {
        private Bootstrap bootstrap;
        private Sensor sensor;
        private Hub hub;
    }

    @Getter
    @Setter
    @ToString
    public static class Bootstrap {
        private String servers;
    }

    @Getter
    @Setter
    @ToString
    public static class Sensor {
        private String topic;
    }

    @Getter
    @Setter
    @ToString
    public static class Hub {
        private String topic;
    }
}