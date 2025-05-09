package com.example.provide_vaccine_services.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class LogService {

    // Khởi tạo logger
    private static final Logger LOGGER = LogManager.getLogger(LogService.class);

    // Hàm ghi log với thông tin tên tài khoản và địa chỉ IP
    public static void LogInfo(String message, String userName, String userIp) {
        LOGGER.info("User: {} | IP: {} - {}", userName, userIp, message);
    }

    public static void LogError(String message, String userName, String userIp) {
        LOGGER.error("User: {} | IP: {} - {}", userName, userIp, message);
    }

    public static void LogWarning(String message, String userName, String userIp) {
        LOGGER.warn("User: {} | IP: {} - {}", userName, userIp, message);
    }

    public static void LogDebug(String message, String userName, String userIp) {
        LOGGER.debug("User: {} | IP: {} - {}", userName, userIp, message);
    }

    public static void LogTrace(String message, String userName, String userIp) {
        LOGGER.trace("User: {} | IP: {} - {}", userName, userIp, message);
    }
}
