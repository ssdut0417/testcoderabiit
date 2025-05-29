using System;
using System.Threading;
using CSharpCodeIssues.Utils;
using Xunit;

namespace CSharpCodeIssues.Tests
{
    /// <summary>
    /// StringHelper测试类 - 包含各种测试代码问题
    /// </summary>
    public class StringHelperTests
    {
        /// <summary>
        /// 1. 硬编码的测试数据
        /// </summary>
        [Fact]
        public void ProcessText_ShouldTrimAndSubstring()
        {
            // 硬编码的测试数据
            var input = "  hello world  ";
            var result = StringHelper.ProcessText(input);
            
            // 硬编码的期望值
            Assert.Equal("hello", result);
        }
        
        /// <summary>
        /// 2. 没有测试边界情况
        /// </summary>
        [Fact]
        public void ConcatenateWords_BasicTest()
        {
            // 只测试正常情况，没有测试null、空数组等边界情况
            var words = new[] { "hello", "world" };
            var result = StringHelper.ConcatenateWords(words);
            
            Assert.Equal("hello world", result);
        }
        
        /// <summary>
        /// 3. 测试中包含控制台输出
        /// </summary>
        [Fact]
        public void IsValidEmail_WithDebugOutput()
        {
            Console.WriteLine("Testing email validation...");
            
            var email = "test@example.com";
            var isValid = StringHelper.IsValidEmail(email);
            
            Console.WriteLine($"Email: {email}, Valid: {isValid}");
            Assert.True(isValid);
        }
        
        /// <summary>
        /// 4. 依赖于时间的不稳定测试
        /// </summary>
        [Fact]
        public void PerformanceTest_StringConcatenation()
        {
            var start = DateTime.Now;
            
            // 性能测试，但结果可能因环境而异
            var words = new string[1000];
            for (int i = 0; i < 1000; i++)
            {
                words[i] = $"word{i}";
            }
            
            StringHelper.ConcatenateWords(words);
            
            var duration = DateTime.Now - start;
            
            // 依赖执行时间的断言，不稳定
            Assert.True(duration.TotalMilliseconds < 1000, "Should complete within 1 second");
        }
        
        /// <summary>
        /// 5. 复杂的测试方法
        /// </summary>
        [Fact]
        public void ComplexMultipleAssertionsTest()
        {
            // 在一个测试中测试多个不相关的功能
            var password = "Password123";
            var email = "user@domain.com";
            var text = "Hello World";
            var number = "42";
            
            // 多个不相关的断言
            var isValidPassword = StringHelper.IsValidPassword(password);
            var isValidEmail = StringHelper.IsValidEmail(email);
            var processedText = StringHelper.ProcessText(text);
            var parsedNumber = StringHelper.SafeParse(number);
            
            Assert.True(isValidPassword);
            Assert.True(isValidEmail);
            Assert.Equal("Hello", processedText);
            Assert.Equal(42, parsedNumber);
        }
        
        /// <summary>
        /// 6. 测试中创建临时文件但不清理
        /// </summary>
        [Fact]
        public void WriteToFile_CreatesFile()
        {
            var filename = "test_output.txt";
            var content = "Test content";
            
            // 创建文件但不在测试后清理
            StringHelper.WriteToFile(filename, content);
            
            // 简单断言
            Assert.True(System.IO.File.Exists(filename));
            
            // 没有清理临时文件
        }
        
        /// <summary>
        /// 7. 忽略异常的测试
        /// </summary>
        [Fact]
        public void ExtractDomain_IgnoresExceptions()
        {
            try
            {
                // 故意传入无效数据但不验证异常
                var result = StringHelper.ExtractDomain("invalid-email");
                
                // 如果没有异常就认为成功
                Assert.NotNull(result);
            }
            catch
            {
                // 忽略所有异常
            }
        }
        
        /// <summary>
        /// 8. 测试中使用Thread.Sleep
        /// </summary>
        [Fact]
        public void ConcurrencyTest_WithSleep()
        {
            // 使用Sleep来模拟并发，不是好的做法
            var task1 = new System.Threading.Tasks.Task(() =>
            {
                Thread.Sleep(100);
                StringHelper.FormatMessage("Hello {0}", "World");
            });
            
            var task2 = new System.Threading.Tasks.Task(() =>
            {
                Thread.Sleep(50);
                StringHelper.FormatMessage("Hi {0}", "There");
            });
            
            task1.Start();
            task2.Start();
            
            // 等待任务完成
            System.Threading.Tasks.Task.WaitAll(task1, task2);
            
            Assert.True(true); // 没有意义的断言
        }
        
        /// <summary>
        /// 9. 重复的测试逻辑
        /// </summary>
        [Fact]
        public void PasswordValidation_Test1()
        {
            var password = "Test123";
            var result = StringHelper.IsValidPassword(password);
            Assert.True(result);
        }
        
        [Fact]
        public void PasswordValidation_Test2()
        {
            var password = "Demo456";
            var result = StringHelper.IsValidPassword(password);
            Assert.True(result);
        }
        
        /// <summary>
        /// 10. 空的测试方法
        /// </summary>
        [Fact]
        public void FutureTest_ToBeImplemented()
        {
            // TODO: 实现这个测试
        }
        
        /// <summary>
        /// 11. 测试方法名不够描述性
        /// </summary>
        [Fact]
        public void Test1()
        {
            var result = StringHelper.CreateHash("test");
            Assert.NotNull(result);
        }
        
        /// <summary>
        /// 12. 测试中包含业务逻辑
        /// </summary>
        [Fact]
        public void ProcessEmailWithBusinessLogic()
        {
            var email = "user@example.com";
            
            // 在测试中包含业务逻辑处理
            string domain;
            if (email.Contains("@"))
            {
                var parts = email.Split('@');
                domain = parts.Length > 1 ? parts[1] : "";
            }
            else
            {
                domain = "";
            }
            
            var extractedDomain = StringHelper.ExtractDomain(email);
            
            Assert.Equal(domain, extractedDomain);
        }
    }
} 