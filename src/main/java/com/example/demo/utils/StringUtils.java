package com.example.demo.utils;

import java.util.*;
import java.io.*;
import java.security.MessageDigest;

/**
 * 字符串工具类 - 包含各种代码问题用于测试
 */
public class StringUtils {
    
    // 1. 硬编码的密钥（安全问题）
    private static final String SECRET_KEY = "mySecretKey123";
    
    // 2. 未使用的静态变量
    private static final String UNUSED_CONSTANT = "never used";
    
    /**
     * 3. 不安全的字符串比较（可能导致时序攻击）
     */
    public static boolean comparePasswords(String password1, String password2) {
        // 直接使用equals比较密码是不安全的
        return password1.equals(password2);
    }
    
    /**
     * 4. 低效的字符串操作
     */
    public static String concatenateStrings(String[] strings) {
        String result = "";
        
        // 在循环中使用字符串拼接，性能很差
        for (int i = 0; i < strings.length; i++) {
            result = result + strings[i] + ",";
        }
        
        return result;
    }
    
    /**
     * 5. 不当的空值处理
     */
    public static String processString(String input) {
        // 没有检查null，可能抛出NullPointerException
        String trimmed = input.trim();
        
        // 可能的StringIndexOutOfBoundsException
        if (trimmed.length() > 0) {
            return trimmed.substring(0, 10); // 没有检查长度是否足够
        }
        
        return "";
    }
    
    /**
     * 6. 不安全的MD5哈希（已废弃的算法）
     */
    public static String generateHash(String input) {
        try {
            // MD5已经不安全，应该使用SHA-256或更强的算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            
            // 低效的十六进制转换
            String result = "";
            for (byte b : hash) {
                result += String.format("%02x", b);
            }
            
            return result;
            
        } catch (Exception e) {
            // 吞掉异常，不好的做法
            return null;
        }
    }
    
    /**
     * 7. 资源泄漏风险
     */
    public static void writeToFile(String content, String filename) {
        try {
            // 没有使用try-with-resources，可能导致资源泄漏
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            
            // 没有在finally中关闭资源
            writer.close();
            
        } catch (IOException e) {
            // 空的异常处理
        }
    }
    
    /**
     * 8. 正则表达式性能问题
     */
    public static boolean isValidEmail(String email) {
        // 每次调用都重新编译正则表达式
        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        return email.matches(emailRegex);
    }
    
    /**
     * 9. 线程不安全的静态变量
     */
    private static StringBuilder sharedBuffer = new StringBuilder();
    
    public static String formatMessage(String template, String... args) {
        // 多个线程同时访问可能出问题
        sharedBuffer.setLength(0);
        
        for (String arg : args) {
            sharedBuffer.append(arg).append(" ");
        }
        
        return template + ": " + sharedBuffer.toString();
    }
    
    /**
     * 10. 潜在的无限循环
     */
    public static String removeSpaces(String input) {
        // 潜在的无限循环，如果input包含特殊空白字符
        while (input.contains(" ")) {
            input = input.replace(" ", "");
            // 如果replace没有实际替换任何内容，可能会无限循环
        }
        
        return input;
    }
    
    /**
     * 11. 不当的异常处理
     */
    public static int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            // 捕获了太宽泛的异常类型
            // 返回-1作为错误标识不是好的做法
            return -1;
        }
    }
    
    /**
     * 12. SQL注入风险
     */
    public static String buildQuery(String tableName, String condition) {
        // 直接拼接SQL，存在注入风险
        return "SELECT * FROM " + tableName + " WHERE " + condition;
    }
} 