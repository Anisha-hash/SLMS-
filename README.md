Smart Library Management System (SLMS)
A backend-focused Java-based Smart Library Management System to automate the process of managing books, members, and transactions in a library. This system includes robust error handling, component integration, and event-driven processing with a clean UI.

Features Overview:
✔ Core Feature Implementation (5 marks)
Book management: Add, update, delete, and view books.

Member management: Register, update, delete members.

Issue/return books with due date tracking.

Search functionality by title, author, ISBN.

Fine calculation for overdue returns.

✔ Error Handling and Robustness (5 marks)
Input validation and exception handling (e.g., invalid ID, null fields).

Try-catch blocks around file and DB operations.

User-friendly error messages.

✔ Integration of Components (5 marks)
JDBC integration with MySQL database.

MVC pattern for separation of UI, business logic, and database access.

Modular Java classes (DAO, Service, Model layers).

✔ Event Handling and Processing (5 marks)
Event listeners for button clicks and form submissions.

Feedback on success/failure of operations (dialog boxes).

Timers for due-date reminders and alerts.

✔ Data Validation (5 marks)
Validations for empty fields, formats (e.g., email, ISBN).

Length checks, date format verification.

Duplicate checks for existing users/books.

✔ Code Quality and Innovative Features (3 marks)
JavaDoc comments and proper indentation.

Clean, reusable code with interfaces and abstract classes.

Innovative Feature: Smart book suggestions based on borrowing history.

✔ Project Documentation (3 marks)
Setup instructions, database schema, use case diagram, class diagram.

Contribution guidelines and issue templates.

Tech Stack:
Language: Java (JDK 17)

Database: MySQL

IDE: IntelliJ IDEA / Eclipse

GUI: Java Swing

Architecture: MVC + DAO

How to Run:
Clone the Repo

Setup Database

Import slms_schema.sql from /database folder to MySQL.

Update DB credentials in DBConnection.java.

Compile and Run

Open project in IDE.

Run Main.java.

Project Structure::
SLMS/
│
├── src/
│   ├── model/
│   ├── dao/
│   ├── service/
│   ├── ui/
│   └── util/
│
├── database/
│   └── slms_schema.sql
├── README.md
└── LICENSE

Future Scope::
Add role-based login (Admin, Student, Staff).

Integrate barcode scanner.

Generate usage analytics and reports.

Implement cloud backup for database.

License::
This project is licensed under the MIT License.
