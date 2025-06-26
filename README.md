# Shapeville 几何学习辅助应用

![Shapeville Logo](docs/logo.png)

## 简介

Shapeville 是一款面向小学生设计的互动式几何学习辅助软件，通过图形识别、角度判断、面积计算等任务，帮助学生巩固几何基础知识，提高空间思维与计算能力。

## 主要功能

* **图形识别**：拍照或绘图后自动识别多种几何图形（如三角形、正方形、圆等）。
* **角度判断**：测量并验证图形中的角度，判断是否满足特定条件。
* **面积计算**：支持对基本图形和复合图形进行准确的面积估算。
* **两大学习阶段**：

  * **KS1**：基础图形识别与简单运算。
  * **KS2**：复杂图形组合与进阶题目。
* **挑战模块 (Bonus)**：两个附加任务，提升解题难度与自主思考能力。
* **学习进度**：实时记录任务完成情况与得分，输出成绩报告。

## 系统截图

<kbd>主界面</kbd> ![主界面](docs/screenshots/main.png) <kbd>KS1 任务</kbd> ![KS1](docs/screenshots/ks1.png) <kbd>KS2 任务</kbd> ![KS2](docs/screenshots/ks2.png) <kbd>挑战模块</kbd> ![Bonus](docs/screenshots/bonus.png)

## 环境要求

* **操作系统**：Windows 10/11 或 macOS 10.15 及以上
* **Java 版本**：JDK 8 及以上
* **内存**：至少 2 GB 可用内存
* **磁盘空间**：至少 100 MB

## 安装与运行

1. 克隆项目仓库：

   ```bash
   git clone https://github.com/your-org/Shapeville.git
   ```
2. 进入项目目录：

   ```bash
   cd Shapeville
   ```
3. 编译并运行：

   ```bash
   javac -d bin src/com/shapeville/*.java
   java -cp bin com.shapeville.ShapevilleApp
   ```

## 项目结构

```
Shapeville/
├── docs/                  # 文档与截图
├── src/                   # 源代码
│   └── com/shapeville/    # 应用主包
│       ├── ShapevilleApp.java
│       ├── controller/    # 控制逻辑
│       ├── model/         # 数据模型
│       └── view/          # 界面布局
├── bin/                   # 编译后 class 文件
├── README.md              # 项目说明
└── LICENSE                # 开源协议
```

## 贡献者

欢迎提交 Issue 与 Pull Request，一起完善 Shapeville：

* **主要开发**：张三 ([zhangsan@example.com](mailto:zhangsan@example.com))
* **UI 设计**：李四 ([lisi@example.com](mailto:lisi@example.com))

## 联系方式

如有问题，请发邮件至 [support@shapeville.org](mailto:support@shapeville.org)，或在 GitHub 上提交 Issue。

---

© 2025 Shapeville 团队 | [License](LICENSE)
