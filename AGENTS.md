## Project Overview
- 该项目是一个个人博客系统，采用 **Spring Boot 3.1.5** 与 **Gradle 8.4** 构建，JDK 版本为 **17**。
- `src/main/java` 含 78 个 Java 文件，共 2341 行；`src/test` 下有 9 个测试文件，共 249 行。
- 顶级包及职责：
  - `com.hab.blog.api`：REST API、配置与业务接口实现。
  - `com.hab.blog.feature`：预留的功能扩展模块。
  - `com.hab.blog.BlogApplication`：应用入口。

## Build & Test Pipeline
- 常用 Gradle 任务：`clean`、`build`、`test`、`integrationTest`、`sonarqube`、`spotlessCheck`。
- GitHub Actions 在 `push`/`pull_request` 到 `master` 分支时触发，阶段包含：Checkout → JDK 设置 → Gradle 缓存 → `./gradlew build` 与 `./gradlew test` → （推送主分支时）构建并推送 Docker 镜像。
- 项目包含 `Dockerfile`，基础镜像 `openjdk:20-slim`，单阶段构建。镜像名称及大小在环境中未生成，暂为 **N/A**。

## Quality & Security Agents
- **Build Agent**：执行 `./gradlew build`，利用 Gradle 缓存，产物位于 `build/libs/`，超时 10 分钟。
- **Test Agent**：运行 `./gradlew test`/`integrationTest`，生成覆盖率报告 `build/customJacocoReportDir/test/jacocoTestReport.xml`，超时 10 分钟。
- **Sonar Agent**：`./gradlew sonarqube` 上传静态分析，需提前配置 `SONAR_HOST` 与 `SONAR_TOKEN` 环境变量；如未配置则 TODO。
- **Container Agent**：根据 `Dockerfile` 构建并推送镜像，基础镜像为 `openjdk:20-slim`，当前为单层复制 JAR 的模式，超时 15 分钟。

## Developer Workflow Cheatsheet
- 本地常用命令：
  ```bash
  ./gradlew build       # 编译打包
  ./gradlew test        # 单元测试
  ./gradlew integrationTest  # 集成测试
  ./gradlew sonarqube   # Sonar 扫描（需配置）
  docker compose up -d sonarqube  # 本地启动 Sonar 服务
  ```
- 典型分支策略：从 `master` 派生 feature 分支，提交 PR 后由 GitHub Actions 自动校验。
- 端到端测试可在本地或 CodeX 环境执行 `./gradlew integrationTest`，结合需要的外部服务（如数据库容器）。

## Open TODOs / Next Steps
- [ ] 配置 SonarQube 服务地址及 Token，完善静态代码扫描。
- [ ] 考虑将业务拆分为独立模块，逐步实现文档中提到的 DDD/微服务方案。
- [ ] 优化 Docker 镜像体积与分层，采用多阶段构建。
- [ ] 补充真实的单元测试与集成测试覆盖。
