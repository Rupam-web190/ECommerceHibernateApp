<div align="center">

# 🛒 E-Commerce Hibernate ORM Application

### 🚀 A Java 17 + Hibernate ORM based E-Commerce Management System

<p>
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Hibernate-6.4.4-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate"/>
  <img src="https://img.shields.io/badge/MySQL-8.4-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
</p>

<p>
  <img src="https://img.shields.io/badge/JUnit-5.11.0-25A162?style=flat-square&logo=junit5&logoColor=white" alt="JUnit"/>
  <img src="https://img.shields.io/badge/JPA-Jakarta%203.1-59666C?style=flat-square" alt="Jakarta Persistence"/>
  <img src="https://img.shields.io/badge/BCrypt-Password%20Hashing-4B5563?style=flat-square" alt="BCrypt"/>
  <img src="https://img.shields.io/badge/Status-Completed-success?style=flat-square" alt="Status"/>
</p>

**A clean ORM-based implementation demonstrating entity relationships, CRUD operations,  
transaction management, persistence, and multi-item order processing.**

</div>

---

## ✨ Project Highlights

> 🎯 **Purpose:** Demonstrate a complete e-commerce domain model using Hibernate ORM and relational database mapping.

| 🔥 Area | ✅ Implementation |
|---|---|
| 🧩 ORM | Hibernate ORM 6.4.4.Final |
| ☕ Java | Java 17 |
| 🗄️ Database | MySQL |
| 🔗 Relationships | `@OneToMany` + `@ManyToOne` |
| 🛠️ CRUD | Create, Read, Update, Delete |
| 📦 Orders | Orders with multiple OrderDetails |
| 🔐 Passwords | BCrypt hashing |
| 🧪 Testing | JUnit 5 |
| 📦 Build | Maven |
| 🧾 Schema | SQL schema included |

---

## 🧠 What This Project Demonstrates

This project models a small e-commerce system where:

- 👤 A **User** can place multiple **Orders**
- 🗂️ A **Category** can contain multiple **Products**
- 🛒 An **Order** can contain multiple **OrderDetails**
- 📦 A **Product** can appear in multiple **OrderDetails**
- 💾 Hibernate handles object-relational persistence
- 🔄 DAO classes provide CRUD operations
- 🔐 User passwords are stored using BCrypt hashing
- 🔎 Orders can be fetched together with their associated user and product details

---

## 🏗️ Entity Relationship Model

```text
                    ┌──────────────────┐
                    │     CATEGORY     │
                    │──────────────────│
                    │ id               │
                    │ name             │
                    │ description      │
                    └────────┬─────────┘
                             │
                           1 │
                             │
                           N │
                    ┌────────▼─────────┐
                    │     PRODUCT      │
                    │──────────────────│
                    │ id               │
                    │ name             │
                    │ price            │
                    │ stockQuantity    │
                    │ category_id      │
                    └────────┬─────────┘
                             │
                           1 │
                             │
                           N │
┌──────────────────┐  ┌──────▼───────────┐
│      USERS       │  │  ORDER_DETAILS   │
│──────────────────│  │──────────────────│
│ id               │  │ id               │
│ username         │  │ quantity         │
│ password         │  │ unitPrice        │
│ email            │  │ order_id         │
│ role             │  │ product_id       │
└────────┬─────────┘  └──────▲───────────┘
         │                   │
       1 │                   │ N
         │                   │
       N │             ┌─────┴───────────┐
┌────────▼─────────┐   │      ORDERS     │
│     ORDERS       │   │─────────────────│
│──────────────────│   │ id              │
│ id               │   │ orderDate       │
│ orderDate        │   │ totalAmount     │
│ totalAmount      │   │ user_id         │
│ user_id          │   └─────────────────┘
└──────────────────┘

Relationships:
• Category 1 ──────── N Product
• Users    1 ──────── N Orders
• Orders   1 ──────── N OrderDetails
• Product  1 ──────── N OrderDetails
```

---

## 🧩 Entity Mapping

### 📂 Category

```java
@OneToMany(
    mappedBy = "category",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<Product> products;
```

### 📦 Product

```java
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "category_id", nullable = false)
private Category category;
```

### 👤 Users

```java
@OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<Orders> orders;
```

### 🧾 Orders

```java
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "user_id", nullable = false)
private Users user;

@OneToMany(
    mappedBy = "order",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<OrderDetails> orderDetails;
```

### 🛍️ OrderDetails

```java
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "order_id", nullable = false)
private Orders order;

@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "product_id", nullable = false)
private Product product;
```

---

## 🗂️ Project Structure

```text
ECommerceHibernateApp/
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📦 com.rupam.ecommerce/
│   │   │       │
│   │   │       ├── 📁 dao/
│   │   │       │   ├── CategoryDAO.java
│   │   │       │   ├── ProductDAO.java
│   │   │       │   ├── UserDAO.java
│   │   │       │   └── OrderDAO.java
│   │   │       │
│   │   │       ├── 📁 entity/
│   │   │       │   ├── Category.java
│   │   │       │   ├── Product.java
│   │   │       │   ├── Users.java
│   │   │       │   ├── Orders.java
│   │   │       │   ├── OrderDetails.java
│   │   │       │   └── Role.java
│   │   │       │
│   │   │       └── 📁 util/
│   │   │           └── HibernateUtil.java
│   │   │
│   │   └── 📁 resources/
│   │       ├── hibernate.cfg.xml
│   │       └── schema.sql
│   │
│   └── 📁 test/
│       └── 📁 java/
│           └── 📦 com.rupam.ecommerce.dao/
│               ├── CategoryDAOTest.java
│               ├── ProductDAOTest.java
│               ├── UserDAOTest.java
│               └── OrderDAOTest.java
│
├── 📄 pom.xml
├── 📄 README.md
└── 📄 .gitignore
```

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| ☕ **Java 17** | Application development |
| 🧩 **Hibernate ORM 6.4.4.Final** | ORM and persistence |
| 🔗 **Jakarta Persistence 3.1** | Entity annotations |
| 🐬 **MySQL 8.4** | Relational database |
| 🏗️ **Maven** | Dependency and build management |
| 🧪 **JUnit 5.11.0** | DAO testing |
| 🔐 **jBCrypt 0.4** | Password hashing |
| 🧰 **Eclipse / IntelliJ / VS Code** | Development environment |

---

## 📦 Core Features

### 🛠️ CRUD Operations

DAO classes provide CRUD functionality for:

- 📂 Categories
- 📦 Products
- 👤 Users
- 🧾 Orders

### 🔗 Relationship Management

The project demonstrates:

```text
Category
   │
   └─── 1 : N ───► Product

Users
   │
   └─── 1 : N ───► Orders
                       │
                       └─── 1 : N ───► OrderDetails
                                           │
                                           └─── N : 1 ───► Product
```

### 🛒 Multi-Product Orders

An order can contain multiple order details.

Example:

```text
Order #101
│
├── Product A → Quantity: 2
├── Product B → Quantity: 1
└── Product C → Quantity: 3
```

### 🔍 Fetch Order With Associated Data

`OrderDAO` includes functionality to retrieve an order together with:

- 👤 User
- 🧾 Order details
- 📦 Products associated with those order details

This uses Hibernate `JOIN FETCH` to load the required related data.

---

## 🔐 Password Security

User passwords are not stored as plain text when using the DAO's password-aware save method.

BCrypt is used:

```java
String hashedPassword =
        BCrypt.hashpw(plainPassword, BCrypt.gensalt());
```

The resulting hash is stored in the `users.password` column.

> ⚠️ This project demonstrates password hashing for the assignment. It is not intended to be a complete production authentication system.

---

## 🗄️ Database Setup

### 1️⃣ Create the Database

Open MySQL and run:

```sql
CREATE DATABASE IF NOT EXISTS ecommerce_hibernate;
USE ecommerce_hibernate;
```

### 2️⃣ Configure MySQL Credentials

Open:

```text
src/main/resources/hibernate.cfg.xml
```

Set your local MySQL credentials:

```xml
<property name="connection.username">root</property>
<property name="connection.password">YOUR_MYSQL_PASSWORD</property>
```

⚠️ **Never commit your real database password to GitHub.**

### 3️⃣ Database Schema

A reproducible schema is included at:

```text
src/main/resources/schema.sql
```

The schema contains:

```text
users
categories
products
orders
order_details
```

### 4️⃣ Hibernate Schema Management

Development configuration uses:

```xml
<property name="hibernate.hbm2ddl.auto">update</property>
```

Therefore Hibernate can create/update the mapped tables during application execution.

---

## ⚙️ Prerequisites

Before running the project, install:

- ☕ JDK 17+
- 🐬 MySQL 8+
- 🏗️ Maven 3.8+
- 💻 Eclipse / IntelliJ IDEA / VS Code
- 🌐 Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd ECommerceHibernateApp
```

### 2️⃣ Configure MySQL

Create:

```text
ecommerce_hibernate
```

Then update the credentials in:

```text
src/main/resources/hibernate.cfg.xml
```

### 3️⃣ Build the Project

```bash
mvn clean compile
```

### 4️⃣ Run the Test Suite

```bash
mvn clean test
```

### 5️⃣ Expected Result

All DAO test classes should execute successfully.

```text
Tests run: ...
Failures: 0
Errors: 0
```

---

## 🧪 Testing

The project includes JUnit tests for the DAO layer.

| Test Class | Coverage |
|---|---|
| 🧪 `CategoryDAOTest` | Category CRUD |
| 🧪 `ProductDAOTest` | Product CRUD |
| 🧪 `UserDAOTest` | User CRUD |
| 🧪 `OrderDAOTest` | Order CRUD + related data |

Run all tests:

```bash
mvn test
```

For a clean test execution:

```bash
mvn clean test
```

---

## 🔄 DAO Architecture

```text
              ┌─────────────────────┐
              │     Application     │
              └──────────┬──────────┘
                         │
                         ▼
              ┌─────────────────────┐
              │        DAO          │
              │─────────────────────│
              │ CategoryDAO         │
              │ ProductDAO          │
              │ UserDAO             │
              │ OrderDAO            │
              └──────────┬──────────┘
                         │
                         ▼
              ┌─────────────────────┐
              │      Hibernate      │
              │     Session API     │
              └──────────┬──────────┘
                         │
                         ▼
              ┌─────────────────────┐
              │        MySQL        │
              │   ecommerce_hibernate│
              └─────────────────────┘
```

---

## 🧱 Transaction Management

Write operations use Hibernate transactions:

```java
Transaction transaction = null;

try (Session session =
        HibernateUtil.getSessionFactory().openSession()) {

    transaction = session.beginTransaction();

    // database operation

    transaction.commit();

} catch (Exception e) {

    if (transaction != null && transaction.isActive()) {
        transaction.rollback();
    }

    throw new RuntimeException("Operation failed", e);
}
```

This ensures that failed write operations do not leave an active transaction behind.

---

## 📋 Database Tables

```text
┌──────────────────┐
│      users       │
├──────────────────┤
│ id PK            │
│ username UNIQUE  │
│ password         │
│ email UNIQUE     │
│ role             │
└──────────────────┘

┌──────────────────┐
│    categories    │
├──────────────────┤
│ id PK            │
│ name UNIQUE      │
│ description      │
└────────┬─────────┘
         │ 1:N
         ▼
┌──────────────────┐
│     products     │
├──────────────────┤
│ id PK            │
│ name             │
│ price            │
│ stock_quantity   │
│ category_id FK   │
└────────┬─────────┘
         │
         │
         ▼
┌──────────────────┐
│  order_details   │
├──────────────────┤
│ id PK            │
│ quantity         │
│ unit_price       │
│ order_id FK      │
│ product_id FK    │
└────────┬─────────┘
         │ N:1
         ▼
┌──────────────────┐
│      orders      │
├──────────────────┤
│ id PK            │
│ order_date       │
│ total_amount     │
│ user_id FK       │
└──────────────────┘
```

---

## 📌 Assignment Coverage

| Requirement | Status |
|---|:---:|
| Category entity | ✅ |
| Product entity | ✅ |
| Users entity | ✅ |
| Orders entity | ✅ |
| OrderDetails entity | ✅ |
| Category → Product 1:N | ✅ |
| User → Orders 1:N | ✅ |
| Orders → OrderDetails 1:N | ✅ |
| Product → OrderDetails 1:N | ✅ |
| CRUD operations | ✅ |
| Multiple OrderDetails per Order | ✅ |
| Fetch Order + User + Products | ✅ |
| Hibernate persistence | ✅ |
| MySQL database | ✅ |
| JUnit DAO tests | ✅ |
| `schema.sql` | ✅ |
| README / setup documentation | ✅ |

---

## 🧹 Optional Features

The project focuses on the **required assignment functionality**.

The following optional enhancements are intentionally not part of the required implementation:

```text
❌ Named Queries for product/category filtering
❌ CriteriaBuilder implementation
❌ Soft Delete
❌ Pagination
```

This keeps the submitted implementation focused on the core ORM, relationship, CRUD, persistence, and testing requirements.

---

## 🔒 GitHub Security Checklist

Before pushing:

```text
☑ Remove real MySQL password
☑ Replace credentials with placeholders
☑ Add target/ to .gitignore
☑ Do not commit IDE-specific files
☑ Run mvn clean test
☑ Check git status
```

Example safe configuration:

```xml
<property name="connection.username">root</property>
<property name="connection.password">YOUR_MYSQL_PASSWORD</property>
```

---

## 📤 GitHub Push

After creating your GitHub repository:

```bash
git init

git add .

git status

git commit -m "Complete Hibernate e-commerce application"

git branch -M main

git remote add origin <YOUR_GITHUB_REPOSITORY_URL>

git push -u origin main
```

For future updates:

```bash
git add .
git commit -m "Update project"
git push
```

---

## 🌟 Repository Presentation Tips

For a clean GitHub repository:

```text
📁 src/
📄 pom.xml
📄 README.md
📄 .gitignore
```

Keep generated/build files out of GitHub:

```text
❌ target/
❌ .classpath
❌ .project
❌ .settings/
❌ .idea/
❌ *.iml
```

---

## 👨‍💻 Author

<div align="center">

### Rupam Majumdar

**B.Tech Computer Science & Engineering**

Java • Spring Boot • Hibernate • REST APIs • SQL • Deep Learning

<p>
  <a href="https://github.com/Rupam-web190">
    <img src="https://img.shields.io/badge/GitHub-Rupam--web190-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
  </a>
  <a href="https://www.linkedin.com/in/rupam-majumdar-0ab740333/">
    <img src="https://img.shields.io/badge/LinkedIn-Rupam%20Majumdar-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
  </a>
</p>

</div>

---

<div align="center">

### ⭐ If this project helped you understand Hibernate ORM, feel free to star the repository!

**Built with ☕ Java + 🧩 Hibernate + 🐬 MySQL**

</div>
