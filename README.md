# Super*Duper*Drive Cloud Storage

## Motivation

Super*Duper*Drive, a pioneering contender in the fiercely competitive Cloud Storage arena, remains undaunted despite the
presence of industry giants like Google Drive and Dropbox. The company aims to distinguish itself by integrating
personal information management features into its application. The initial product release comprises three fundamental
user-facing features:

1. **Simple File Storage:** Empowering users to effortlessly upload, download, and delete files.
2. **Note Management:** Facilitating the addition, modification, and removal of text-based notes.
3. **Password Management:** Providing a secure platform to save, edit, and delete website credentials.

The pivotal goal of this project is to develop a comprehensive web application, covering the server-side, website
interface, and essential testing components.

## Implementation

### Tools Used

The project leverages a range of modern technologies and tools:

- **SpringBoot:** For robust back-end development.
- **Thymeleaf:** For creating dynamic, server-rendered web pages.
- **Spring MVC:** For building web applications using the Model-View-Controller architecture.
- **Maven:** For project build and dependency management.

### Features Implemented

#### Core Features:

1. **Simple File Storage:** Users can upload, download, and delete files, simplifying data management.
2. **Note Management:** Users have the flexibility to add, edit, and delete text-based notes.
3. **Password Management:** Enabling users to securely store, modify, and remove website credentials.

#### Additional Features:

- **Secure User Access:** Implemented with Spring Security to control unauthorized access to pages other than login and
  signup.
- **Database Integration:** A database schema is designed and implemented to store user data securely.
- **Testing with Selenium:** User-facing functionality is thoroughly tested using Selenium to ensure robustness and
  correctness.

## Steps of Execution

### 1. Back-End with Spring Boot

- **Security Setup:** Establish user access management and authentication mechanisms using Spring Security. Unauthorized
  access to pages other than login and signup is prohibited.
- **Controller Development:** Develop controllers responsible for processing front-end requests and handling various
  application functionalities.
- **Database Interaction:** Connect the application with a database by designing Java classes that align with the
  database schema. MyBatis mapper interfaces are implemented to allow interaction with the database, supporting CRUD
  operations.

### 2. Front-End with Thymeleaf

- **Customize HTML Templates:** Modify existing HTML templates to integrate Thymeleaf attributes and enable dynamic
  rendering of content.
- **Data Integration:** Populate the templates with real data obtained from the server, ensuring a seamless user
  experience.
- **Interactive Functionality:** Implement user interaction features as specified by the project requirements.

### 3. Application Tests with Selenium

- **User Management Tests:** Develop Selenium tests to verify user signup, login, and access restrictions, ensuring the
  security and correctness of user access.
- **Note and Credential Tests:** Comprehensive testing is carried out for creating, viewing, editing, and deleting notes
  and credentials, guaranteeing the reliability of these core features.
- **Data Security:** Implement best practices to maintain the security of user data, such as hashing passwords and
  encryption.

## Note

This project encompasses back-end development, front-end design, and comprehensive testing to create a robust and secure
Cloud Storage application. It focuses on delivering essential user features and data security while adhering to industry
best practices.

The project's structure promotes efficient development and thorough testing, aiming to provide users with a seamless and
secure Cloud Storage solution.

