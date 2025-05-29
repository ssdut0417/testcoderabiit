using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Diagnostics;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;

namespace CSharpCodeIssues.Security
{
    /// <summary>
    /// 安全漏洞演示类 - 包含常见的安全问题
    /// </summary>
    public class SecurityVulnerabilities
    {
        // 1. 硬编码的敏感信息
        private const string DatabasePassword = "admin123";
        private const string EncryptionKey = "mySecretKey12345";
        private const string ApiSecret = "sk-1234567890abcdef";
        
        /// <summary>
        /// 2. SQL注入漏洞
        /// </summary>
        public User GetUserByName(string username)
        {
            var connectionString = "Server=localhost;Database=TestDB;User Id=admin;Password=" + DatabasePassword;
            
            using var connection = new SqlConnection(connectionString);
            connection.Open();
            
            // 直接拼接SQL，存在SQL注入风险
            var sql = $"SELECT * FROM Users WHERE Username = '{username}'";
            using var command = new SqlCommand(sql, connection);
            
            using var reader = command.ExecuteReader();
            if (reader.Read())
            {
                return new User
                {
                    Username = reader.GetString(0),
                    Email = reader.GetString(1)
                };
            }
            
            return null;
        }
        
        /// <summary>
        /// 3. 命令注入漏洞
        /// </summary>
        public void ExecuteCommand(string userInput)
        {
            try
            {
                // 直接执行用户输入，存在命令注入风险
                var processInfo = new ProcessStartInfo
                {
                    FileName = "ping",
                    Arguments = userInput,
                    UseShellExecute = false,
                    RedirectStandardOutput = true
                };
                
                using var process = Process.Start(processInfo);
                var output = process?.StandardOutput.ReadToEnd();
                Console.WriteLine(output);
            }
            catch (Exception)
            {
                // 在异常处理中泄露敏感信息
                Console.WriteLine($"Command execution failed. API Key: {ApiSecret}");
            }
        }
        
        /// <summary>
        /// 4. 路径遍历漏洞
        /// </summary>
        public string ReadFile(string filename)
        {
            try
            {
                // 没有验证文件路径，可能导致路径遍历攻击
                var filePath = Path.Combine("/app/data", filename);
                return File.ReadAllText(filePath);
            }
            catch (Exception ex)
            {
                // 可能泄露系统路径信息
                Console.WriteLine($"Failed to read file: {ex.Message}");
                return null;
            }
        }
        
        /// <summary>
        /// 5. 弱密码验证
        /// </summary>
        public bool IsValidPassword(string password)
        {
            // 非常弱的密码验证规则
            return password != null && password.Length >= 4;
        }
        
        /// <summary>
        /// 6. 不安全的随机数生成
        /// </summary>
        public string GenerateToken()
        {
            // 使用Random类不适合生成安全令牌
            var random = new Random();
            var token = new StringBuilder();
            
            for (int i = 0; i < 16; i++)
            {
                var randomChar = (char)(random.Next(26) + 97); // a-z
                token.Append(randomChar);
            }
            
            return token.ToString();
        }
        
        /// <summary>
        /// 7. 不安全的加密实现
        /// </summary>
        public string EncryptData(string data)
        {
            try
            {
                // 使用已废弃的MD5算法
                using var md5 = MD5.Create();
                var hash = md5.ComputeHash(Encoding.UTF8.GetBytes(data + EncryptionKey));
                
                return Convert.ToBase64String(hash);
            }
            catch (Exception)
            {
                // 忽略异常，返回原始数据
                return data;
            }
        }
        
        /// <summary>
        /// 8. 不当的输入验证
        /// </summary>
        public void ProcessUserData(string email, int age, string comment)
        {
            // 没有验证邮箱格式
            Console.WriteLine($"Processing email: {email}");
            
            // 没有验证年龄范围
            if (age > 0)
            {
                Console.WriteLine($"Age: {age}");
            }
            
            // 直接输出用户输入，可能导致XSS（如果在web环境中）
            Console.WriteLine($"<div>User comment: {comment}</div>");
        }
        
        /// <summary>
        /// 9. 不安全的反序列化
        /// </summary>
        public object DeserializeData(string jsonData)
        {
            try
            {
                // 使用Newtonsoft.Json反序列化未验证的数据可能有安全风险
                return Newtonsoft.Json.JsonConvert.DeserializeObject(jsonData);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Deserialization failed: {ex.Message}");
                return null;
            }
        }
        
        /// <summary>
        /// 10. 信息泄露
        /// </summary>
        public void HandleError(string operation)
        {
            try
            {
                if (operation == "fail")
                {
                    throw new InvalidOperationException($"Database connection failed with password: {DatabasePassword}");
                }
            }
            catch (Exception ex)
            {
                // 完整的异常信息可能暴露内部结构
                Console.WriteLine($"Full exception details: {ex}");
                
                // 记录敏感信息到日志
                File.AppendAllText("error.log", $"Error: {ex.Message}, Connection: {DatabasePassword}\n");
            }
        }
        
        /// <summary>
        /// 11. 不安全的正则表达式（ReDoS攻击风险）
        /// </summary>
        public bool ValidateInput(string input)
        {
            // 可能导致ReDoS攻击的正则表达式
            var pattern = @"^(a+)+$";
            var regex = new Regex(pattern);
            
            return regex.IsMatch(input);
        }
        
        /// <summary>
        /// 12. 硬编码的URL和配置
        /// </summary>
        public void CallExternalAPI()
        {
            // 硬编码的API端点
            var apiUrl = "https://api.internal.company.com/secret-endpoint";
            
            // 硬编码的认证信息
            var authHeader = "Bearer " + ApiSecret;
            
            Console.WriteLine($"Calling API: {apiUrl} with auth: {authHeader}");
        }
    }
    
    /// <summary>
    /// 用户实体类
    /// </summary>
    public class User
    {
        public string? Username { get; set; }
        public string? Email { get; set; }
        
        // 敏感信息没有适当的保护
        public string? Password { get; set; }
        public string? SocialSecurityNumber { get; set; }
    }
} 