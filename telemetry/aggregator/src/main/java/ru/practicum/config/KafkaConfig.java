package ru.practicum.config;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "spring.collector.kafka")
public class KafkaConfig {

    Telemetry telemetry;

    public String getSnapshotTopic() {
        return this.getTelemetry().getSnapshot().getTopic();
    }

    public String getSensorTopic() {
        return this.getTelemetry().getSensor().getTopic();
    }

    @Getter
    @Setter
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Telemetry {
        Bootstrap bootstrap;
        Sensor sensor;
        Snapshot snapshot;
    }

    @Getter
    @Setter
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Bootstrap {
        String servers;
    }

    @Getter
    @Setter
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Sensor {
        String topic;
    }

    @Getter
    @Setter
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Snapshot {
        String topic;
    }
}