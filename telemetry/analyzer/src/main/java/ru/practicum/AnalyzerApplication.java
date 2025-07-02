package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.practicum.service.HubEventProcessor;
import ru.practicum.service.SnapshotProcessor;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AnalyzerApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AnalyzerApplication.class, args);

        final HubEventProcessor hubEventProcessor = context.getBean(HubEventProcessor.class);
        SnapshotProcessor snapshotProcessor = context.getBean(SnapshotProcessor.class);

        Thread hubEventsThread = new Thread(hubEventProcessor);
        hubEventsThread.setName("HubEventHandlerThread");
        hubEventsThread.start();

        Thread snapshotsThread = new Thread(snapshotProcessor);
        snapshotsThread.setName("SnapshotProcessorThread");
        snapshotsThread.start();
    }
}