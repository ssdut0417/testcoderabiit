using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;

namespace CSharpCodeIssues.Performance
{
    /// <summary>
    /// 性能问题演示类 - 包含各种性能相关的代码问题
    /// </summary>
    public class PerformanceIssues
    {
        // 1. 静态缓存，可能导致内存泄漏
        private static readonly Dictionary<string, object> StaticCache = new Dictionary<string, object>();
        
        /// <summary>
        /// 2. 低效的字符串拼接
        /// </summary>
        public void InEfficientStringConcatenation()
        {
            string result = "";
            
            // 在循环中使用字符串拼接，效率很低
            for (int i = 0; i < 1000; i++)
            {
                result += $"Number: {i} ";
            }
            
            Console.WriteLine($"Result length: {result.Length}");
        }
        
        /// <summary>
        /// 3. 不必要的对象创建
        /// </summary>
        public void UnnecessaryObjectCreation()
        {
            var list = new List<string>();
            
            for (int i = 0; i < 10000; i++)
            {
                // 每次迭代都创建新的DateTime对象
                var now = new DateTime();
                var timeString = now.ToString("yyyy-MM-dd");
                
                // 不必要的字符串对象创建
                var message = new string($"Item {i}: {timeString}".ToCharArray());
                list.Add(message);
            }
        }
        
        /// <summary>
        /// 4. 低效的集合操作
        /// </summary>
        public void InEfficientCollectionOperations()
        {
            var list = new List<int>();
            
            // 频繁的插入操作在列表开头
            for (int i = 0; i < 1000; i++)
            {
                list.Insert(0, i); // O(n)操作，应该使用Queue或者反向操作
            }
            
            // 使用LINQ进行复杂的嵌套查询
            var result = list.Where(x => x > 100)
                           .Select(x => x * 2)
                           .Where(x => x < 1000)
                           .Select(x => x.ToString())
                           .Where(x => x.Length > 2)
                           .ToList();
        }
        
        /// <summary>
        /// 5. 低效的正则表达式使用
        /// </summary>
        public bool ValidateEmailFormat(string email)
        {
            // 每次调用都重新编译正则表达式
            var emailPattern = @"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$";
            return Regex.IsMatch(email, emailPattern);
        }
        
        /// <summary>
        /// 6. 不当的异常使用来控制流程
        /// </summary>
        public bool IsValidInteger(string input)
        {
            try
            {
                // 使用异常来控制程序流程，性能很差
                int.Parse(input);
                return true;
            }
            catch (FormatException)
            {
                return false;
            }
        }
        
        /// <summary>
        /// 7. 内存泄漏风险 - 无限制的缓存
        /// </summary>
        public void AddToCache(string key, object value)
        {
            // 无限制地向静态缓存添加数据
            StaticCache[key] = value;
            // 没有清理机制或大小限制
        }
        
        /// <summary>
        /// 8. 低效的排序算法
        /// </summary>
        public void BubbleSortExample(int[] array)
        {
            // 使用冒泡排序而不是内置的高效排序
            int n = array.Length;
            for (int i = 0; i < n - 1; i++)
            {
                for (int j = 0; j < n - i - 1; j++)
                {
                    if (array[j] > array[j + 1])
                    {
                        // 交换元素
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
        
        /// <summary>
        /// 9. 不必要的装箱/拆箱操作
        /// </summary>
        public void BoxingUnboxingIssues()
        {
            var list = new ArrayList(); // 使用非泛型集合
            
            for (int i = 0; i < 1000; i++)
            {
                list.Add(i); // 装箱操作
            }
            
            int sum = 0;
            foreach (object item in list)
            {
                sum += (int)item; // 拆箱操作
            }
        }
        
        /// <summary>
        /// 10. 低效的文件I/O操作
        /// </summary>
        public void InEfficientFileIO()
        {
            var fileName = "large_file.txt";
            
            // 没有使用缓冲，频繁的系统调用
            using var writer = new System.IO.StreamWriter(fileName);
            for (int i = 0; i < 10000; i++)
            {
                writer.WriteLine($"Line {i}"); // 每行都是单独的写操作
                writer.Flush(); // 不必要的刷新操作
            }
        }
        
        /// <summary>
        /// 11. 不必要的同步操作
        /// </summary>
        private readonly object _lockObject = new object();
        
        public void UnnecessarySynchronization(int value)
        {
            // 对简单的局部变量操作使用锁
            lock (_lockObject)
            {
                int localVar = value * 2;
                Console.WriteLine(localVar);
            }
        }
        
        /// <summary>
        /// 12. 低效的查找算法
        /// </summary>
        public bool ContainsValue(List<int> list, int target)
        {
            // 线性搜索，应该使用HashSet或排序后二分查找
            foreach (var item in list)
            {
                if (item == target)
                    return true;
            }
            return false;
        }
        
        /// <summary>
        /// 13. 重复计算
        /// </summary>
        public void RedundantCalculations()
        {
            var results = new List<double>();
            
            for (int i = 0; i < 1000; i++)
            {
                // 在循环中重复计算相同的值
                double pi = Math.PI;
                double e = Math.E;
                double constant = Math.Sin(pi) + Math.Cos(e);
                
                results.Add(i * constant);
            }
        }
        
        /// <summary>
        /// 14. 不合适的数据结构选择
        /// </summary>
        public void PoorDataStructureChoice()
        {
            // 使用List进行频繁的Contains操作
            var list = new List<string>();
            
            for (int i = 0; i < 1000; i++)
            {
                var item = $"item{i}";
                if (!list.Contains(item)) // O(n)操作
                {
                    list.Add(item);
                }
            }
        }
        
        /// <summary>
        /// 15. 异步操作的阻塞调用
        /// </summary>
        public void BlockingAsyncCalls()
        {
            // 阻塞异步操作，可能导致死锁
            var task = Task.Run(async () =>
            {
                await Task.Delay(1000);
                return "Completed";
            });
            
            // 使用.Result阻塞调用
            var result = task.Result;
            Console.WriteLine(result);
        }
        
        /// <summary>
        /// 16. 内存分配过多
        /// </summary>
        public void ExcessiveMemoryAllocation()
        {
            for (int i = 0; i < 10000; i++)
            {
                // 频繁创建大对象
                var largeArray = new byte[1024 * 1024]; // 1MB
                
                // 立即丢弃，给GC造成压力
                largeArray = null;
            }
        }
    }
} 