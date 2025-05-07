package com.example.provide_vaccine_services.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogService {

    // Khởi tạo logger
    private static final Logger LOGGER = LogManager.getLogger(LogService.class);

    // Ghi log mức độ INFO
    public static void LogInfo(String message) {
        LOGGER.info(message);
    }

    // Ghi log mức độ ERROR
    public static void LogError(String message) {
        LOGGER.error(message);
    }

    // Ghi log mức độ WARNING
    public static void LogWarning(String message) {
        LOGGER.warn(message);
    }

    // Ghi log mức độ DEBUG
    public static void LogDebug(String message) {
        LOGGER.debug(message);
    }

    // Ghi log mức độ TRACE
    public static void LogTrace(String message) {
        LOGGER.trace(message);
    }
}