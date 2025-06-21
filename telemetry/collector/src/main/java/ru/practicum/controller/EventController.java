package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.sensor.SensorEvent;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    @PostMapping("/sensors")
    public void addSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {

    }

    @PostMapping("/hubs")
    public void addHubEvent() {

    }
}
