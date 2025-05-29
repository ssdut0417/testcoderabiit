package com.example.demo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 测试类也包含一些问题，用于测试代码分析工具对测试代码的检查能力
 */
public class CodeIssuesDemoTest {
    
    /**
     * 1. 没有断言的测试
     */
    @Test
    public void testWithoutAssertions() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        // 调用了方法但没有验证结果
        demo.inefficientStringConcatenation();
    }
    
    /**
     * 2. 测试中的硬编码值
     */
    @Test
    public void testWithHardcodedValues() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        
        // 硬编码的测试数据
        String result = demo.nullPointerRisk("test");
        assertEquals("TEST", result);
        
        // 硬编码的魔法数字
        assertTrue(result.length() == 4);
    }
    
    /**
     * 3. 测试异常但没有正确验证
     */
    @Test
    public void testExceptionHandling() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        
        try {
            // 期望异常但没有使用expected注解或fail()
            demo.nullPointerRisk(null);
        } catch (NullPointerException e) {
            // 空的catch块
        }
    }
    
    /**
     * 4. 测试方法太复杂
     */
    @Test
    public void complexTestMethod() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        
        // 在一个测试中测试多个不相关的功能
        demo.inefficientStringConcatenation();
        demo.poorExceptionHandling();
        demo.duplicatedCode1();
        demo.duplicatedCode2();
        
        // 复杂的验证逻辑
        if (System.currentTimeMillis() > 0) {
            if (demo != null) {
                assertTrue(true);
            }
        }
    }
    
    /**
     * 5. 没有清理资源的测试
     */
    @Test
    public void testWithoutCleanup() {
        // 创建资源但没有清理
        java.io.File tempFile = new java.io.File("test-temp.txt");
        
        try {
            tempFile.createNewFile();
            // 执行测试逻辑
            assertTrue(tempFile.exists());
            
            // 没有删除临时文件
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }
    
    /**
     * 6. 依赖于外部状态的测试
     */
    @Test
    public void testDependentOnExternalState() {
        // 依赖于系统时间
        long currentTime = System.currentTimeMillis();
        assertTrue("Test runs in reasonable time", currentTime > 1000000000000L);
        
        // 依赖于文件系统
        java.io.File file = new java.io.File("/tmp");
        assertTrue("Temp directory exists", file.exists());
    }
    
    /**
     * 7. 测试中的Sleep
     */
    @Test
    public void testWithSleep() throws InterruptedException {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        
        // 不好的做法：在测试中使用sleep
        Thread.sleep(1000);
        
        assertNotNull(demo);
    }
    
    /**
     * 8. 重复的测试代码
     */
    @Test
    public void duplicatedTestCode1() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        assertNotNull(demo);
        
        String result = demo.inefficientStringConcatenation();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void duplicatedTestCode2() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        assertNotNull(demo);
        
        String result = demo.inefficientStringConcatenation();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
    
    /**
     * 9. 忽略的测试（没有说明原因）
     */
    @Test
    @org.junit.Ignore
    public void ignoredTestWithoutReason() {
        // 被忽略的测试但没有说明原因
        fail("This test should not run");
    }
    
    /**
     * 10. 测试中的System.out.println
     */
    @Test
    public void testWithPrintStatements() {
        CodeIssuesDemo demo = new CodeIssuesDemo();
        
        // 测试中不应该有打印语句
        System.out.println("Starting test...");
        
        String result = demo.inefficientStringConcatenation();
        
        System.out.println("Result: " + result);
        System.out.println("Test completed");
        
        assertNotNull(result);
    }
} 