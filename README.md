# 🧮 Windows Calculator Automation Framework

## 📘 Overview
The **Windows Calculator Automation Framework** is a **desktop automation project** built to validate and interact with the **Windows 10/11 Calculator app** using **Appium (WinAppDriver)**.

It automates key calculator operations such as addition, subtraction, multiplication, and division, ensuring accuracy and reliability of results in a real Windows environment.

This framework is built with **TestNG**, **Page Object Model (POM)**, and a **Config-driven setup**, making it **scalable**, **maintainable**, and **reusable** for future Windows app testing.

---

## 🧩 Key Highlights

- 🧮 **Basic Arithmetic Tests** – Automates addition, subtraction, multiplication, and division.
- 🔍 **UI Element Validation** – Ensures all calculator buttons and display fields are accessible.
- ⚙️ **Configurable Setup** – Application path and server URL loaded dynamically from `config.properties`.
- 🚀 **Driver Management** – Reusable driver creation via `DriverManager`.
- 🧱 **Page Object Model (POM)** – Each screen encapsulated in dedicated Page classes.
- 🧾 **Structured Reports & Logs** – Integrated logging via Log4j and HTML test reports.

---

## ⚙️ Tech Stack & Framework Features

| Layer | Tool / Framework | Purpose |
|-------|------------------|----------|
| ☕ **Language** | Java | Core automation language |
| 🪟 **Automation Driver** | Appium WinAppDriver | Windows app automation |
| 🧪 **Testing Framework** | TestNG | Test configuration, grouping & parallel execution |
| 🧱 **Design Pattern** | Page Object Model (POM) | Maintainable and modular design |
| 📊 **Reporting** | ChainTest / Log4j2 HTML reports | Enhanced execution reports |
| 🧰 **Build Tool** | Maven | Dependency & lifecycle management |
| 💾 **Configuration** | config.properties | Centralized app settings |

---

## 🧪 Test Coverage

| Test Case | Description |
|------------|-------------|
| ✅ `testAddition()` | Validates addition operation accuracy |
| ✅ `testSubtraction()` | Validates subtraction results |
| ✅ `testMultiplication()` | Ensures correct multiplication calculation |
| ✅ `testDivision()` | Verifies division logic and handling of division by zero |
| ✅ `testClearDisplay()` | Ensures calculator clears previous results properly |

---

## 🧾 Project Structure

**Calculator-Automation**  
