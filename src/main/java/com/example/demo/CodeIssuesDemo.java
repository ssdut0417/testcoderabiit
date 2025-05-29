package com.example.demo;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个类故意包含了多种代码问题，用于测试代码分析工具的能力
 */
public class CodeIssuesDemo {
    
    // 1. 未使用的字段
    private String unusedField = "I'm never used";
    
    // 2. 可能的线程安全问题 - 使用ArrayList而不是线程安全的集合
    private static List<String> sharedList = new ArrayList<>();
    
    // 3. 硬编码的魔法数字
    private static final int MAGIC_NUMBER = 42;
    
    public static void main(String[] args) {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        
        // 测试各种代码问题
        demo.nullPointerRisk(null);
        demo.resourceLeakIssue();
        demo.inefficientStringConcatenation();
        demo.poorExceptionHandling();
        demo.duplicatedCode1();
        demo.duplicatedCode2();
        demo.deadCode();
    }
    
    /**
     * 1. Null Pointer异常风险
     */
    public String nullPointerRisk(String input) {
        // 直接使用可能为null的参数，没有检查
        return input.toUpperCase(); // 可能抛出NullPointerException
    }
    
    /**
     * 2. 资源泄漏问题
     */
    public void resourceLeakIssue() {
        try {
            FileInputStream fis = new FileInputStream("test.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            
            String line = reader.readLine();
            System.out.println(line);
            
            // 没有在finally块中关闭资源，可能导致资源泄漏
            
        } catch (IOException e) {
            e.printStackTrace(); // 不好的异常处理
        }
    }
    
    /**
     * 3. 低效的字符串连接
     */
    public String inefficientStringConcatenation() {
        String result = "";
        
        // 在循环中使用字符串连接，效率低下
        for (int i = 0; i < 1000; i++) {
            result += "Number: " + i + " ";
        }
        
        return result;
    }
    
    /**
     * 4. 不当的异常处理
     */
    public void poorExceptionHandling() {
        try {
            int result = 10 / 0; // 故意的除零错误
        } catch (Exception e) {
            // 捕获了太宽泛的异常类型
            // 没有适当的处理，只是打印
            System.out.println("Something went wrong");
        }
    }
    
    /**
     * 5. 代码重复问题 - 第一部分
     */
    public void duplicatedCode1() {
        // 未使用的局部变量
        int unusedVariable = 100;
        
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        
        for (String item : list) {
            if (item != null) {
                System.out.println("Processing: " + item.toUpperCase());
            }
        }
    }
    
    /**
     * 6. 代码重复问题 - 第二部分（与上面几乎相同）
     */
    public void duplicatedCode2() {
        List<String> list = new ArrayList<>();
        list.add("data1");
        list.add("data2");
        list.add("data3");
        
        for (String item : list) {
            if (item != null) {
                System.out.println("Processing: " + item.toUpperCase());
            }
        }
    }
    
    /**
     * 7. 永远不会被执行的死代码
     */
    public void deadCode() {
        boolean flag = true;
        
        if (flag) {
            System.out.println("This will be executed");
            return;
        }
        
        // 下面的代码永远不会被执行
        System.out.println("This is dead code");
        int deadVariable = 42;
    }
    
    /**
     * 8. 复杂度过高的方法
     */
    public void complexMethod(int type, String data, boolean flag) {
        if (type == 1) {
            if (flag) {
                if (data != null) {
                    if (data.length() > 0) {
                        if (data.contains("test")) {
                            if (data.startsWith("pre")) {
                                if (data.endsWith("post")) {
                                    // 嵌套太深
                                    System.out.println("Very complex condition");
                                }
                            }
                        }
                    }
                }
            }
        } else if (type == 2) {
            // 更多复杂的逻辑...
        }
    }
    
    /**
     * 9. 线程安全问题
     */
    public static void threadSafetyIssue() {
        // 多个线程可能同时修改这个共享的ArrayList
        sharedList.add("New item");
        
        // 更好的做法是使用ConcurrentHashMap或synchronized
        Map<String, String> betterMap = new ConcurrentHashMap<>();
        betterMap.put("key", "value");
    }
    
    /**
     * 10. 内存泄漏风险
     */
    public void memoryLeakRisk() {
        // 创建大量对象但没有适当的清理
        List<byte[]> largeList = new ArrayList<>();
        
        for (int i = 0; i < 10000; i++) {
            largeList.add(new byte[1024 * 1024]); // 每次添加1MB
        }
        
        // 没有清理largeList，可能导致内存问题
    }
} 