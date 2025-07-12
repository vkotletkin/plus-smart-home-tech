package ru.practicum.service;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.model.Action;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HubRouterClient {

    private static final int SHUTDOWN_TIMEOUT_SECONDS = 5;
    private static final int KEEP_ALIVE_TIME_SECONDS = 30;

    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub stub;
    private final ManagedChannel channel;

    public HubRouterClient(@Value("${grpc.client.hub-router.address}") String grpcAddress) {
        String address = grpcAddress.replace("static://", "");
        this.channel = createChannel(address);
        this.stub = HubRouterControllerGrpc.newBlockingStub(channel);
        log.info("Initialized gRPC client for address: {}", address);
    }

    public void sendAction(Action action) {

        if (!isActionValid(action)) {
            logInvalidAction(action);
            return;
        }

        try {
            DeviceActionRequest request = buildDeviceActionRequest(action);
            stub.handleDeviceAction(request);
            logSuccessfulAction(request, action);
        } catch (Exception e) {
            logActionFailure(action, e);
        }
    }


    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdown().awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                log.info("gRPC channel shutdown successfully");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted during gRPC channel shutdown: {}", e.getMessage(), e);
            }
        }
    }

    private ActionTypeProto mapActionType(ActionTypeAvro type) {
        return switch (type) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
        };
    }

    private ManagedChannel createChannel(String address) {
        return ManagedChannelBuilder.forTarget(address)
                .usePlaintext()
                .keepAliveTime(KEEP_ALIVE_TIME_SECONDS, TimeUnit.SECONDS)
                .keepAliveWithoutCalls(true)
                .build();
    }

    private boolean isActionValid(Action action) {
        return action != null
                && action.getScenario() != null
                && action.getSensor() != null;
    }

    private void logInvalidAction(Action action) {
        log.error("Invalid action: action={}, scenario={}, sensor={}",
                action,
                action != null ? action.getScenario() : null,
                action != null ? action.getSensor() : null);
    }

    private DeviceActionRequest buildDeviceActionRequest(Action action) {
        Instant now = Instant.now();

        return DeviceActionRequest.newBuilder()
                .setHubId(action.getScenario().getHubId())
                .setScenarioName(action.getScenario().getName())
                .setAction(buildDeviceActionProto(action))
                .setTimestamp(buildTimestamp(now))
                .build();
    }

    private DeviceActionProto buildDeviceActionProto(Action action) {
        return DeviceActionProto.newBuilder()
                .setSensorId(action.getSensor().getId())
                .setType(mapActionType(action.getType()))
                .setValue(action.getValue() != null ? action.getValue() : 0)
                .build();
    }

    private Timestamp buildTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private void logSuccessfulAction(DeviceActionRequest request, Action action) {
        log.info("Sent action {} for device {} in scenario {} for hub {}",
                request.getAction().getType(),
                action.getSensor().getId(),
                action.getScenario().getName(),
                action.getScenario().getHubId());
    }

    private void logActionFailure(Action action, Exception e) {
        String sensorId = action != null && action.getSensor() != null
                ? action.getSensor().getId()
                : "unknown";
        log.error("Failed to send action for device {}: {}", sensorId, e.getMessage(), e);
    }
}