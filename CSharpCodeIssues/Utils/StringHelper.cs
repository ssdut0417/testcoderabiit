using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace CSharpCodeIssues.Utils
{
    /// <summary>
    /// 字符串处理工具类 - 包含各种代码质量问题
    /// </summary>
    public static class StringHelper
    {
        // 1. 硬编码的配置值
        private const string DefaultEncoding = "UTF-8";
        private const string SecretSalt = "mySecretSalt123";
        
        // 2. 可变的静态字段
        public static StringBuilder SharedBuilder = new StringBuilder();
        
        /// <summary>
        /// 3. 不安全的字符串比较
        /// </summary>
        public static bool CompareSecrets(string secret1, string secret2)
        {
            // 直接比较，容易受到时序攻击
            return secret1 == secret2;
        }
        
        /// <summary>
        /// 4. 潜在的空引用异常
        /// </summary>
        public static string ProcessText(string input)
        {
            // 没有检查null值
            var trimmed = input.Trim();
            
            // 没有验证长度就进行子字符串操作
            if (trimmed.Length > 0)
            {
                return trimmed.Substring(0, 5); // 可能越界
            }
            
            return string.Empty;
        }
        
        /// <summary>
        /// 5. 低效的字符串操作
        /// </summary>
        public static string ConcatenateWords(params string[] words)
        {
            string result = "";
            
            // 在循环中使用字符串拼接
            foreach (var word in words)
            {
                result = result + word + " ";
            }
            
            return result.TrimEnd();
        }
        
        /// <summary>
        /// 6. 正则表达式编译问题
        /// </summary>
        public static bool IsValidEmail(string email)
        {
            // 每次调用都重新编译正则表达式
            string pattern = @"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$";
            return Regex.IsMatch(email, pattern);
        }
        
        /// <summary>
        /// 7. 不当的异常处理
        /// </summary>
        public static int SafeParse(string number)
        {
            try
            {
                return int.Parse(number);
            }
            catch (Exception)
            {
                // 捕获所有异常类型，并返回魔法数字
                return -9999;
            }
        }
        
        /// <summary>
        /// 8. 线程安全问题
        /// </summary>
        public static string FormatMessage(string template, params object[] args)
        {
            // 使用共享的StringBuilder，多线程不安全
            SharedBuilder.Clear();
            SharedBuilder.AppendFormat(template, args);
            return SharedBuilder.ToString();
        }
        
        /// <summary>
        /// 9. 内存浪费
        /// </summary>
        public static string[] SplitText(string text, char separator)
        {
            var parts = new List<string>();
            var currentPart = "";
            
            // 低效的字符级别处理，应该使用内置Split方法
            foreach (char c in text)
            {
                if (c == separator)
                {
                    parts.Add(currentPart);
                    currentPart = ""; // 创建新字符串对象
                }
                else
                {
                    currentPart += c; // 每次都创建新字符串
                }
            }
            
            if (!string.IsNullOrEmpty(currentPart))
            {
                parts.Add(currentPart);
            }
            
            return parts.ToArray();
        }
        
        /// <summary>
        /// 10. 硬编码的业务逻辑
        /// </summary>
        public static bool IsValidPassword(string password)
        {
            // 硬编码的密码规则
            if (password == null || password.Length < 8)
                return false;
                
            // 简单的检查，没有考虑复杂度
            bool hasUpper = password.Any(char.IsUpper);
            bool hasLower = password.Any(char.IsLower);
            bool hasDigit = password.Any(char.IsDigit);
            
            return hasUpper && hasLower && hasDigit;
        }
        
        /// <summary>
        /// 11. 不安全的哈希实现
        /// </summary>
        public static string CreateHash(string input)
        {
            // 使用简单的哈希，容易碰撞
            var hash = input.GetHashCode();
            
            // 添加固定的盐值，但盐值是硬编码的
            var saltedHash = (input + SecretSalt).GetHashCode();
            
            return $"{hash}_{saltedHash}";
        }
        
        /// <summary>
        /// 12. 文化敏感的字符串操作
        /// </summary>
        public static string NormalizeText(string text)
        {
            // 使用当前文化进行大小写转换，可能在不同环境下产生不同结果
            var normalized = text.ToLower();
            
            // 使用默认的字符串比较，没有指定文化
            if (normalized.Contains("test"))
            {
                return normalized.Replace("test", "demo");
            }
            
            return normalized;
        }
        
        /// <summary>
        /// 13. 潜在的无限循环
        /// </summary>
        public static string RemoveSubstring(string source, string toRemove)
        {
            // 如果toRemove是空字符串或null，可能导致无限循环
            while (source.Contains(toRemove))
            {
                source = source.Replace(toRemove, "");
            }
            
            return source;
        }
        
        /// <summary>
        /// 14. 数据验证不足
        /// </summary>
        public static string ExtractDomain(string email)
        {
            // 没有验证邮箱格式就提取域名
            int atIndex = email.LastIndexOf('@');
            
            // 没有检查索引是否有效
            return email.Substring(atIndex + 1);
        }
        
        /// <summary>
        /// 15. 编码问题
        /// </summary>
        public static byte[] EncodeString(string text)
        {
            // 使用默认编码，可能在不同平台上不一致
            return Encoding.Default.GetBytes(text);
        }
        
        /// <summary>
        /// 16. 资源管理问题
        /// </summary>
        public static void WriteToFile(string filename, string content)
        {
            // 没有使用using语句管理资源
            var writer = new System.IO.StreamWriter(filename);
            writer.Write(content);
            
            // 忘记关闭文件流
            // writer.Close();
        }
    }
}