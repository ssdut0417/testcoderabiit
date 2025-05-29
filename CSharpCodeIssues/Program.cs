using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using CSharpCodeIssues.Security;
using CSharpCodeIssues.Performance;
using CSharpCodeIssues.Utils;

namespace CSharpCodeIssues
{
    /// <summary>
    /// 主程序类 - 包含各种C#代码问题用于测试代码分析工具
    /// </summary>
    class Program
    {
        // 1. 硬编码的敏感信息
        private const string DatabaseConnectionString = "Server=localhost;Database=TestDB;User Id=admin;Password=password123;";
        private const string ApiKey = "sk-1234567890abcdef";
        
        // 2. 未使用的字段
        private static readonly string UnusedField = "never used";
        
        // 3. 可变的静态集合（线程安全问题）
        public static List<string> SharedList = new List<string>();
        
        static void Main(string[] args)
        {
            Console.WriteLine("Starting C# Code Issues Demo...");
            
            // 测试各种代码问题
            TestNullPointerIssues();
            TestResourceLeaks();
            TestSecurityVulnerabilities();
            TestPerformanceIssues();
            TestConcurrencyProblems();
            
            Console.WriteLine("Demo completed.");
        }
        
        /// <summary>
        /// 4. 空引用异常风险
        /// </summary>
        private static void TestNullPointerIssues()
        {
            string nullString = null;
            
            // 直接使用可能为null的变量，没有检查
            try
            {
                int length = nullString.Length; // 可能抛出NullReferenceException
                Console.WriteLine($"Length: {length}");
            }
            catch (Exception ex)
            {
                // 捕获了太宽泛的异常类型
                Console.WriteLine($"Error: {ex.Message}");
            }
        }
        
        /// <summary>
        /// 5. 资源泄漏问题
        /// </summary>
        private static void TestResourceLeaks()
        {
            // 没有使用using语句，可能导致资源泄漏
            var file = new System.IO.FileStream("test.txt", System.IO.FileMode.Create);
            var writer = new System.IO.StreamWriter(file);
            
            writer.WriteLine("Test content");
            
            // 没有正确释放资源
            // file.Dispose(); 
            // writer.Dispose();
        }
        
        /// <summary>
        /// 6. 安全漏洞测试
        /// </summary>
        private static void TestSecurityVulnerabilities()
        {
            var securityDemo = new SecurityVulnerabilities();
            
            // 测试SQL注入
            securityDemo.GetUserByName("'; DROP TABLE Users; --");
            
            // 测试命令注入
            securityDemo.ExecuteCommand("127.0.0.1; rm -rf /");
            
            // 测试路径遍历
            securityDemo.ReadFile("../../../etc/passwd");
        }
        
        /// <summary>
        /// 7. 性能问题测试
        /// </summary>
        private static void TestPerformanceIssues()
        {
            var performanceDemo = new PerformanceIssues();
            
            // 低效的字符串拼接
            performanceDemo.InEfficientStringConcatenation();
            
            // 不必要的对象创建
            performanceDemo.UnnecessaryObjectCreation();
            
            // 低效的集合操作
            performanceDemo.InEfficientCollectionOperations();
        }
        
        /// <summary>
        /// 8. 并发问题测试
        /// </summary>
        private static void TestConcurrencyProblems()
        {
            // 多个任务同时修改共享资源
            var tasks = new Task[10];
            
            for (int i = 0; i < 10; i++)
            {
                int taskId = i;
                tasks[i] = Task.Run(() =>
                {
                    // 竞态条件
                    SharedList.Add($"Item {taskId}");
                    
                    // 非线程安全的操作
                    _counter++;
                });
            }
            
            Task.WaitAll(tasks);
        }
        
        // 9. 非线程安全的静态变量
        private static int _counter = 0;
        
        /// <summary>
        /// 10. 未处理的异步操作
        /// </summary>
        public static void UnhandledAsyncOperation()
        {
            // 没有等待异步操作完成
            DoAsyncWork(); // 应该使用await或Wait()
        }
        
        private static async Task DoAsyncWork()
        {
            await Task.Delay(1000);
            Console.WriteLine("Async work completed");
        }
        
        /// <summary>
        /// 11. 内存泄漏风险
        /// </summary>
        public static void MemoryLeakRisk()
        {
            var largeList = new List<byte[]>();
            
            // 创建大量对象但没有适当清理
            for (int i = 0; i < 10000; i++)
            {
                largeList.Add(new byte[1024 * 1024]); // 每次添加1MB
            }
            
            // 没有清理largeList
        }
        
        /// <summary>
        /// 12. 错误的异常处理
        /// </summary>
        public static void PoorExceptionHandling()
        {
            try
            {
                int divisor = 0;
                int result = 10 / divisor; // 运行时除零错误
            }
            catch
            {
                // 空的catch块，吞掉所有异常
            }
            
            try
            {
                throw new InvalidOperationException("Test exception");
            }
            catch (Exception)
            {
                // 在异常处理中暴露敏感信息
                Console.WriteLine($"Database error with connection: {DatabaseConnectionString}");
                throw; // 重新抛出异常但可能暴露堆栈信息
            }
        }
    }
} 