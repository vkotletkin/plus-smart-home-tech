package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AggregatorSnapshotState {

    // создаем мапу для хранения снэпшотов
    Map<String, SensorsSnapshotAvro> sensorsSnapshotAvroMap = new HashMap<>();

    Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {

        // либо получаем существующий снэпшот, либо создаем новый
        SensorsSnapshotAvro currentSnapshotAvro = sensorsSnapshotAvroMap.getOrDefault(event.getHubId(),
                SensorsSnapshotAvro.newBuilder()
                        .setHubId(event.getHubId())
                        .setTimestamp(event.getTimestamp())
                        .setSensorsState(new HashMap<>())
                        .build());

        // получаем в снэпшоте из мапы с сенсорами по идентификатору SensorEventAvro. Если нет - то null
        SensorStateAvro oldSensorState = currentSnapshotAvro.getSensorsState().get(event.getId());

        // проверяем на изменение, нужно ли апдейтить
        if (oldSensorState != null
                && (oldSensorState.getTimestamp().isBefore(event.getTimestamp())
                || event.getPayload().equals(oldSensorState.getData()))) {
            return Optional.empty();
        }

        // т.к. ветка сверху не выполнилась, а значит данные обновились, то обновляем состояние снэпшота
        SensorStateAvro newSensorState = SensorStateAvro.newBuilder()
                .setTimestamp(event.getTimestamp())
                .setData(event.getPayload())
                .build();

        // сохраняем новое состояние в снэпшот
        currentSnapshotAvro.getSensorsState().put(event.getId(), newSensorState);
        // обновляем временную метку у снэпшота таймстемпом из события
        currentSnapshotAvro.setTimestamp(event.getTimestamp());

        // кладем в хранилище снэпшотов
        sensorsSnapshotAvroMap.put(event.getHubId(), currentSnapshotAvro);

        return Optional.of(currentSnapshotAvro);
    }
}