package com.example.demo.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * StringUtils测试类 - 包含测试代码问题
 */
public class StringUtilsTest {
    
    /**
     * 1. 测试中的硬编码值
     */
    @Test
    public void testConcatenateStrings() {
        String[] input = {"hello", "world", "test"};
        String result = StringUtils.concatenateStrings(input);
        
        // 硬编码的期望值
        assertEquals("hello,world,test,", result);
        
        // 魔法数字
        assertTrue(result.length() == 18);
    }
    
    /**
     * 2. 没有测试边界情况
     */
    @Test
    public void testProcessString() {
        // 只测试正常情况，没有测试null或空字符串
        String result = StringUtils.processString("hello world");
        assertEquals("hello worl", result);
    }
    
    /**
     * 3. 测试中的打印语句
     */
    @Test
    public void testGenerateHash() {
        System.out.println("Testing hash generation...");
        
        String hash = StringUtils.generateHash("test");
        assertNotNull(hash);
        
        System.out.println("Generated hash: " + hash);
    }
    
    /**
     * 4. 测试异常但没有正确验证
     */
    @Test
    public void testParseInteger() {
        // 测试异常情况但没有验证具体异常类型
        int result = StringUtils.parseInteger("invalid");
        assertEquals(-1, result);
        
        // 没有测试null输入的情况
    }
    
    /**
     * 5. 不稳定的测试（依赖时间）
     */
    @Test
    public void testPerformance() {
        long startTime = System.currentTimeMillis();
        
        String[] largeArray = new String[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = "item" + i;
        }
        
        StringUtils.concatenateStrings(largeArray);
        
        long endTime = System.currentTimeMillis();
        
        // 依赖于执行时间的断言，不稳定
        assertTrue("Should complete in reasonable time", 
                   (endTime - startTime) < 1000);
    }
    
    /**
     * 6. 测试方法过于复杂
     */
    @Test
    public void complexTest() {
        // 在一个测试中测试多个功能
        String hash1 = StringUtils.generateHash("test1");
        String hash2 = StringUtils.generateHash("test2");
        
        String concat = StringUtils.concatenateStrings(new String[]{"a", "b"});
        
        int parsed = StringUtils.parseInteger("123");
        
        String processed = StringUtils.processString("  hello  ");
        
        // 复杂的验证逻辑
        if (hash1 != null && hash2 != null) {
            if (concat.length() > 0) {
                if (parsed > 0) {
                    assertTrue(processed.startsWith("hello"));
                }
            }
        }
    }
    
    /**
     * 7. 空的测试方法
     */
    @Test
    public void testEmailValidation() {
        // 测试方法体为空
        // TODO: 实现邮箱验证测试
    }
    
    /**
     * 8. 重复的测试逻辑
     */
    @Test
    public void testStringProcessing1() {
        String result = StringUtils.processString("test string");
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void testStringProcessing2() {
        String result = StringUtils.processString("another test");
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
    
    /**
     * 9. 测试中创建临时文件但不清理
     */
    @Test
    public void testFileOperations() {
        String content = "test content";
        String filename = "test-output.txt";
        
        // 写入文件但不清理
        StringUtils.writeToFile(content, filename);
        
        // 简单断言
        assertTrue("File operation completed", true);
    }
    
    /**
     * 10. 使用Thread.sleep的测试
     */
    @Test
    public void testConcurrency() throws InterruptedException {
        // 不好的做法：在测试中使用sleep
        Thread.sleep(100);
        
        String result = StringUtils.formatMessage("Test", "arg1", "arg2");
        assertNotNull(result);
    }
} 