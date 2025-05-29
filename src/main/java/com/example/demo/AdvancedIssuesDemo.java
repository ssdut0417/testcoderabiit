package com.example.demo;

import java.util.*;
import java.util.concurrent.*;
import java.lang.reflect.*;

/**
 * 包含更高级和微妙代码问题的演示类
 */
public class AdvancedIssuesDemo {
    
    /**
     * 1. 单例模式的线程安全问题
     */
    public static class UnsafeSingleton {
        private static UnsafeSingleton instance;
        
        public static UnsafeSingleton getInstance() {
            // 双重检查锁定的错误实现
            if (instance == null) {
                synchronized (UnsafeSingleton.class) {
                    if (instance == null) {
                        instance = new UnsafeSingleton();
                    }
                }
            }
            return instance;
        }
    }
    
    /**
     * 2. 不正确的equals和hashCode实现
     */
    public static class BadEqualsHashCode {
        private String name;
        private int value;
        
        public BadEqualsHashCode(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        // 只重写了equals但没有重写hashCode
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof BadEqualsHashCode) {
                BadEqualsHashCode other = (BadEqualsHashCode) obj;
                return Objects.equals(name, other.name) && value == other.value;
            }
            return false;
        }
        
        // 缺少hashCode重写会导致在HashMap等集合中出现问题
    }
    
    /**
     * 3. 不当的比较器实现
     */
    public static class BadComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            // 违反比较器的传递性和对称性
            if (s1.length() > s2.length()) {
                return 1;
            } else if (s1.length() < s2.length()) {
                return -1;
            } else {
                // 对于相等长度的字符串，使用随机比较（非常糟糕）
                return (int) (Math.random() * 3) - 1;
            }
        }
    }
    
    /**
     * 4. 线程池使用问题
     */
    public void problematicThreadPoolUsage() {
        // 创建无界线程池，可能导致资源耗尽
        ExecutorService executor = Executors.newCachedThreadPool();
        
        for (int i = 0; i < 10000; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(10000); // 长时间运行的任务
                    System.out.println("Task " + taskId + " completed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 没有正确关闭线程池
        // executor.shutdown();
    }
    
    /**
     * 5. 不当的锁使用
     */
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void potentialDeadlock1() {
        synchronized (lock1) {
            System.out.println("Thread acquired lock1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            synchronized (lock2) {
                System.out.println("Thread acquired lock2");
            }
        }
    }
    
    public void potentialDeadlock2() {
        // 与上面方法不同的锁获取顺序，可能导致死锁
        synchronized (lock2) {
            System.out.println("Thread acquired lock2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            synchronized (lock1) {
                System.out.println("Thread acquired lock1");
            }
        }
    }
    
    /**
     * 6. 错误的volatile使用
     */
    private volatile boolean flag = false;
    private int counter = 0; // 非volatile，但在多线程中修改
    
    public void volatileMisuse() {
        // volatile不能保证复合操作的原子性
        counter++; // 不是原子操作
        flag = true;
    }
    
    /**
     * 7. 不当的反射使用
     */
    public void dangerousReflection(String className, String methodName) {
        try {
            // 直接根据用户输入加载类和调用方法，极其危险
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.newInstance(); // 已废弃的方法
            
            Method method = clazz.getMethod(methodName);
            method.invoke(instance);
            
        } catch (Exception e) {
            e.printStackTrace(); // 可能暴露系统信息
        }
    }
    
    /**
     * 8. 内部类的内存泄漏
     */
    public class InnerClassMemoryLeak {
        private byte[] largeData = new byte[1024 * 1024]; // 1MB
        
        public Runnable createRunnable() {
            // 非静态内部类持有外部类引用，可能导致内存泄漏
            return new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running task");
                }
            };
        }
    }
    
    /**
     * 9. 不正确的克隆实现
     */
    public static class ShallowCloneIssue implements Cloneable {
        private List<String> items = new ArrayList<>();
        
        public ShallowCloneIssue(List<String> items) {
            this.items = items;
        }
        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            // 浅拷贝，修改克隆对象会影响原对象
            return super.clone();
        }
        
        public List<String> getItems() {
            return items;
        }
    }
    
    /**
     * 10. 不当的finalize使用
     */
    public static class BadFinalize {
        @Override
        protected void finalize() throws Throwable {
            try {
                // finalize中进行重要清理工作是不可靠的
                System.out.println("Finalizing...");
                
                // 更糟糕的是在finalize中抛出异常
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Finalize failed");
                }
                
            } finally {
                super.finalize();
            }
        }
    }
    
    /**
     * 11. 集合的并发修改
     */
    public void concurrentModificationIssue() {
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        
        // 在迭代过程中修改集合
        for (String item : list) {
            if (item.equals("item2")) {
                list.remove(item); // 抛出ConcurrentModificationException
            }
        }
    }
    
    /**
     * 12. 不当的静态变量使用
     */
    private static List<Object> staticList = new ArrayList<>();
    
    public void staticVariableMisuse(Object data) {
        // 无限制地向静态集合添加数据
        staticList.add(data);
        
        // 静态集合永远不会被清理，导致内存泄漏
    }
    
    /**
     * 13. 错误的数组使用
     */
    public void arrayMisuse() {
        int[] array = new int[10];
        
        // 可能的数组越界
        for (int i = 0; i <= array.length; i++) { // 注意这里是 <=
            array[i] = i; // 最后一次迭代会越界
        }
    }
    
    /**
     * 14. 不当的日期处理
     */
    public void dateHandlingIssues() {
        // 使用已废弃的Date构造函数
        Date date = new Date(2023, 12, 25); // 错误：月份从0开始，年份需要减1900
        
        // SimpleDateFormat不是线程安全的
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        
        // 在多线程环境中共享SimpleDateFormat实例是危险的
        String dateString = formatter.format(date);
        System.out.println(dateString);
    }
    
    /**
     * 15. 错误的序列化实现
     */
    public static class BadSerialization implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        private String password; // 敏感信息不应该被序列化
        private transient String sessionId; // transient字段在反序列化后为null
        
        public BadSerialization(String password, String sessionId) {
            this.password = password;
            this.sessionId = sessionId;
        }
        
        // 没有实现readObject和writeObject来处理敏感数据
        
        public String getSessionId() {
            // 可能返回null（如果对象是反序列化而来）
            return sessionId.toUpperCase(); // 可能抛出NullPointerException
        }
    }
} 