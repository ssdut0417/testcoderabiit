package com.example.demo.utils;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;

/**
 * 数据处理工具类 - 包含各种代码问题
 */
public class DataProcessor {
    
    // 1. 硬编码的配置信息
    private static final String API_ENDPOINT = "https://api.example.com/data";
    private static final String DB_PASSWORD = "password123";
    
    // 2. 可变的静态集合（线程安全问题）
    public static List<String> processingQueue = new ArrayList<>();
    
    // 3. 未关闭的资源
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    
    /**
     * 4. 不安全的网络请求
     */
    public static String fetchDataFromAPI(String userId) {
        try {
            // 直接拼接URL，可能导致注入攻击
            String url = API_ENDPOINT + "?userId=" + userId;
            
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            
            // 没有设置超时，可能导致无限等待
            connection.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            // 没有关闭资源
            return response.toString();
            
        } catch (Exception e) {
            // 泄露敏感信息
            System.err.println("API call failed with password: " + DB_PASSWORD);
            return null;
        }
    }
    
    /**
     * 5. 低效的数据处理
     */
    public static List<Integer> processNumbers(int[] numbers) {
        List<Integer> result = new ArrayList<>();
        
        // 不必要的装箱操作
        for (int i = 0; i < numbers.length; i++) {
            Integer boxedNumber = new Integer(numbers[i]); // 废弃的构造方法
            
            // 低效的重复计算
            if (isPrime(boxedNumber) && isPrime(boxedNumber)) {
                result.add(boxedNumber);
            }
        }
        
        // 低效的排序
        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = 0; j < result.size() - i - 1; j++) {
                if (result.get(j) > result.get(j + 1)) {
                    Integer temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }
        
        return result;
    }
    
    /**
     * 6. 低效的质数判断
     */
    private static boolean isPrime(int number) {
        if (number < 2) return false;
        
        // 低效的算法，应该只检查到sqrt(number)
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 7. 内存泄漏风险
     */
    private static Map<String, byte[]> cache = new HashMap<>();
    
    public static void cacheData(String key, byte[] data) {
        // 无限制地向缓存添加数据，可能导致内存耗尽
        cache.put(key, data);
        
        // 没有清理机制或大小限制
    }
    
    /**
     * 8. 竞态条件
     */
    private static int counter = 0;
    
    public static void incrementCounter() {
        // 非原子操作，多线程环境下不安全
        counter++;
        
        // 添加到共享集合也不安全
        processingQueue.add("Item " + counter);
    }
    
    /**
     * 9. 阻塞操作没有超时
     */
    public static String processWithDelay(String input) {
        try {
            // 提交任务但没有设置超时
            Future<String> future = executor.submit(() -> {
                try {
                    Thread.sleep(10000); // 模拟长时间处理
                    return input.toUpperCase();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            });
            
            // 可能无限等待
            return future.get();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 10. 不当的序列化
     */
    public static byte[] serializeObject(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            
            // 没有验证对象类型就序列化
            oos.writeObject(obj);
            
            // 没有关闭流
            return bos.toByteArray();
            
        } catch (Exception e) {
            // 忽略异常
            return null;
        }
    }
    
    /**
     * 11. 文件操作安全问题
     */
    public static String readConfigFile(String filename) {
        try {
            // 没有验证文件路径，可能导致路径遍历
            File file = new File("/config/" + filename);
            
            Scanner scanner = new Scanner(file);
            StringBuilder content = new StringBuilder();
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // 可能包含敏感信息但没有过滤
                content.append(line).append("\n");
            }
            
            // 没有关闭Scanner
            return content.toString();
            
        } catch (Exception e) {
            // 可能泄露系统路径信息
            System.err.println("Failed to read file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 12. 数组边界问题
     */
    public static int[] processArray(int[] input, int startIndex, int endIndex) {
        // 没有验证边界
        int[] result = new int[endIndex - startIndex];
        
        for (int i = startIndex; i < endIndex; i++) {
            // 可能数组越界
            result[i - startIndex] = input[i] * 2;
        }
        
        return result;
    }
    
    /**
     * 13. 死锁风险
     */
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    
    public static void method1() {
        synchronized (lock1) {
            try {
                Thread.sleep(100);
                synchronized (lock2) {
                    System.out.println("Method 1 executed");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void method2() {
        synchronized (lock2) {
            try {
                Thread.sleep(100);
                synchronized (lock1) {
                    System.out.println("Method 2 executed");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
} 