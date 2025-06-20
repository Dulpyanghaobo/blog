## Repo 结构
- src/main/java: 业务
- src/main/resources: 配置
- src/test/java: 单测

## 构建 & 测试命令
|类型|命令|说明|
|---|---|---|
|build|`./gradlew build`|打包|
|test|`./gradlew test`|单测|
|integration|`./gradlew integrationTest`|集成测|
|format|`./gradlew spotlessCheck`|格式|

## 目录约束与命名规范
- 包名前缀 `com.hab.blog`
- 测试与业务结构一致

## 依赖与工具安装
- `MAVEN_TOKEN` 用于私库
- Sonar 地址 http://localhost:9000, key blog
- Jacoco 报告 build/reports/jacoco/test/jacocoTestReport.xml

## 验收标准
- Quality Gate 通过, 0 Bug/Vuln
- 覆盖率 ≥ 60%
- PR 标题 `[Component] Summary`

## 常见任务示例
- 修 Bug：说明问题并补测
- 写测试：按包建类, 跑 `./gradlew test`
