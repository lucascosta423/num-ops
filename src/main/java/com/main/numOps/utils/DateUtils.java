package com.main.numOps.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateUtils {

    public static LocalDateTime nowWithoutNanos() {
        return LocalDateTime.now().withNano(0);
    }
}
