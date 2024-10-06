以下是一份项目配置文档,包含如何运行该项目的步骤:

## 项目配置文档

### 1. 安装Gradle

1. 下载Gradle
    - 访问Gradle官网下载页面: https://gradle.org/releases/
    - 下载最新版本的二进制包(如gradle-8.10.2-bin.zip)

2. 解压安装
    - 将下载的zip文件解压到指定目录(如D:\gradle-8.10.2)

3. 配置环境变量
    - 新建系统变量GRADLE_HOME,值为Gradle解压目录(如D:\gradle-8.10.2)
    - 编辑系统变量Path,在末尾添加 %GRADLE_HOME%\bin

4. 验证安装
    - 打开命令提示符,输入 `gradle -v`
    - 如显示Gradle版本信息则安装成功

### 2. 安装JDK

1. 下载JDK
    - 访问Oracle官网下载页面
    - 下载适合系统的JDK安装包(如jdk-17_windows-x64_bin.exe)

2. 安装JDK
    - 双击运行安装包,按提示完成安装
    - 记住JDK的安装路径(如E:\Program Files\Java\jdk-17)

3. 配置环境变量
    - 新建系统变量JAVA_HOME,值为JDK安装路径
    - 编辑系统变量Path,添加 %JAVA_HOME%\bin

4. 验证安装
    - 打开命令提示符,输入 `java -version`
    - 如显示Java版本信息则安装成功

### 3. 安装MySQL

1. 下载MySQL
    - 访问MySQL官网下载页面
    - 下载MySQL Community Server安装包

2. 安装MySQL
    - 运行安装包,选择"Custom"自定义安装
    - 选择安装路径,设置root密码
    - 完成安装并启动MySQL服务

3. 配置环境变量
    - 编辑系统变量Path,添加MySQL的bin目录路径

4. 验证安装
    - 打开命令提示符,输入 `mysql -u root -p`
    - 输入密码后能成功登录则安装成功

5. 创建对应的数据库和表
   - 在进入mysql运行sql_scrpit/sql文件
   
### 4. 配置application.properties
spring.datasource.username=数据库的账户
spring.datasource.password=数据库的密码

### 运行项目

1. 克隆项目代码到本地

2. 打开命令提示符,进入项目根目录

3. 执行 `gradle build` 编译项目

4. 执行 `gradle bootRun` 运行项目

5. 访问 http://localhost:8081 查看项目是否成功运行
