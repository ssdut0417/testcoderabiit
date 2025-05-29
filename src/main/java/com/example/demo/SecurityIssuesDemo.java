package com.example.demo;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 * 这个类包含常见的安全漏洞，用于测试安全代码分析
 */
public class SecurityIssuesDemo {
    
    // 1. 硬编码的敏感信息
    private static final String DATABASE_PASSWORD = "admin123";
    private static final String API_KEY = "sk-1234567890abcdef";
    
    /**
     * 2. SQL注入漏洞
     */
    public User getUserByName(String username) throws SQLException {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/testdb", 
            "admin", 
            DATABASE_PASSWORD
        );
        
        // 直接拼接SQL，存在SQL注入风险
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()) {
            return new User(rs.getString("username"), rs.getString("email"));
        }
        
        return null;
    }
    
    /**
     * 3. 命令注入漏洞
     */
    public void executeCommand(String userInput) {
        try {
            // 直接执行用户输入，存在命令注入风险
            Process process = Runtime.getRuntime().exec("ping " + userInput);
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 4. 路径遍历漏洞
     */
    public String readFile(String filename) {
        try {
            // 没有验证文件路径，可能导致路径遍历攻击
            File file = new File("/app/data/" + filename);
            
            Scanner scanner = new Scanner(file);
            StringBuilder content = new StringBuilder();
            
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            
            scanner.close();
            return content.toString();
            
        } catch (FileNotFoundException e) {
            return "File not found";
        }
    }
    
    /**
     * 5. 不安全的随机数生成
     */
    public String generateToken() {
        // 使用Math.random()不适合生成安全令牌
        StringBuilder token = new StringBuilder();
        
        for (int i = 0; i < 16; i++) {
            int randomChar = (int) (Math.random() * 26) + 97; // a-z
            token.append((char) randomChar);
        }
        
        return token.toString();
    }
    
    /**
     * 6. 不当的输入验证
     */
    public void processUserData(String email, int age) {
        // 没有验证邮箱格式
        System.out.println("Processing email: " + email);
        
        // 没有验证年龄范围
        if (age > 0) {
            System.out.println("Age: " + age);
        }
        
        // 直接输出用户输入，可能导致XSS（如果在web环境中）
        System.out.println("<div>User input: " + email + "</div>");
    }
    
    /**
     * 7. 弱密码验证
     */
    public boolean isValidPassword(String password) {
        // 非常弱的密码验证规则
        return password != null && password.length() >= 4;
    }
    
    /**
     * 8. 信息泄露
     */
    public void handleException(String operation) {
        try {
            // 模拟可能失败的操作
            if (operation.equals("fail")) {
                throw new RuntimeException("Database connection failed: " + DATABASE_PASSWORD);
            }
        } catch (Exception e) {
            // 在异常消息中暴露敏感信息
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace(); // 可能暴露内部结构信息
        }
    }
    
    /**
     * 9. 不安全的对象反序列化
     */
    public Object deserializeObject(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            
            // 没有验证就直接反序列化，可能导致代码执行
            return ois.readObject();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 10. 不安全的临时文件创建
     */
    public File createTempFile(String content) {
        try {
            // 创建的临时文件可能有不安全的权限
            File tempFile = File.createTempFile("temp", ".txt");
            
            FileWriter writer = new FileWriter(tempFile);
            writer.write(content);
            writer.close();
            
            // 没有设置适当的文件权限
            return tempFile;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 简单的User类用于演示
    public static class User {
        private String username;
        private String email;
        
        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
        
        // getters and setters...
        public String getUsername() { return username; }
        public String getEmail() { return email; }
    }
} 