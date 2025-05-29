package com.example.demo;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 这个类包含各种性能问题，用于测试性能分析能力
 */
public class PerformanceIssuesDemo {
    
    /**
     * 1. 低效的集合操作
     */
    public boolean containsItem(List<String> list, String item) {
        // 使用ArrayList的contains方法，时间复杂度O(n)
        // 如果频繁查找，应该使用HashSet
        return list.contains(item);
    }
    
    /**
     * 2. 不必要的对象创建
     */
    public String processStrings(String[] inputs) {
        String result = "";
        
        // 在循环中创建大量String对象
        for (String input : inputs) {
            result = result + input.toUpperCase() + ",";
        }
        
        return result;
    }
    
    /**
     * 3. 低效的正则表达式使用
     */
    public boolean validateEmail(String email) {
        // 每次调用都重新编译正则表达式
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * 4. 低效的数据结构选择
     */
    public void inefficientDataStructure() {
        // 使用Vector而不是ArrayList（Vector是同步的，性能较差）
        Vector<Integer> numbers = new Vector<>();
        
        for (int i = 0; i < 10000; i++) {
            numbers.add(i);
        }
        
        // 频繁的随机访问，但使用了LinkedList
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            linkedList.add("item" + i);
        }
        
        // 低效的访问模式
        for (int i = 0; i < linkedList.size(); i++) {
            String item = linkedList.get(i); // O(n)操作
            System.out.println(item);
        }
    }
    
    /**
     * 5. 内存浪费
     */
    public void memoryWaste() {
        // 创建大数组但只使用很少的元素
        int[] largeArray = new int[1000000];
        largeArray[0] = 1;
        largeArray[1] = 2;
        
        // 创建不必要的包装对象
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new Integer(i)); // 应该使用Integer.valueOf()或自动装箱
        }
    }
    
    /**
     * 6. 低效的循环
     */
    public void inefficientLoop() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        
        // 在循环条件中调用方法
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        
        // 嵌套循环中的重复计算
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                double result = Math.sqrt(i * j); // 重复的数学计算
                System.out.println(result);
            }
        }
    }
    
    /**
     * 7. 低效的字符串处理
     */
    public String processLargeText(String text) {
        // 多次调用替换方法，每次都创建新的String对象
        String result = text;
        result = result.replace("old1", "new1");
        result = result.replace("old2", "new2");
        result = result.replace("old3", "new3");
        result = result.replace("old4", "new4");
        
        return result;
    }
    
    /**
     * 8. 不必要的同步
     */
    public synchronized void unnecessarySynchronization(int value) {
        // 只是简单的局部变量操作，不需要同步
        int localVar = value * 2;
        System.out.println(localVar);
    }
    
    /**
     * 9. 低效的异常使用
     */
    public boolean isNumber(String str) {
        try {
            // 使用异常来控制流程，性能很差
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 10. 低效的I/O操作
     */
    public void inefficientIO() {
        try {
            // 没有使用缓冲，频繁的系统调用
            java.io.FileWriter writer = new java.io.FileWriter("output.txt");
            
            for (int i = 0; i < 10000; i++) {
                writer.write("Line " + i + "\n"); // 每次写入都是系统调用
            }
            
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 11. 内存泄漏风险
     */
    private static final Map<String, Object> cache = new HashMap<>();
    
    public void potentialMemoryLeak(String key, Object value) {
        // 无限制地向静态Map添加数据，可能导致内存泄漏
        cache.put(key, value);
        // 没有清理机制
    }
    
    /**
     * 12. 低效的排序
     */
    public void inefficientSorting() {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        
        // 添加大量数据
        for (int i = 0; i < 100000; i++) {
            numbers.add(random.nextInt());
        }
        
        // 使用低效的冒泡排序而不是Collections.sort()
        bubbleSort(numbers);
    }
    
    private void bubbleSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    // 交换元素
                    Integer temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
    
    /**
     * 13. 不必要的计算
     */
    public void unnecessaryComputations() {
        List<Double> results = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++) {
            // 在循环中重复计算常量
            double pi = Math.PI;
            double e = Math.E;
            double result = Math.sin(pi) + Math.cos(e);
            results.add(result);
        }
    }
} 