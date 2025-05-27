# SocialConnect Backend

## Overview
SocialConnect is a web-based social media platform that enables seamless digital communication, images sharing, and social engagement.

## Features
Based on the SocialConnect platform requirements, the backend supports the following functionalities:
- **User Registration and Authentication**: REST endpoints for email/password registration.
- **Profile Management**: CRUD operations for user profiles, including profile picture uploads.
- **Content Creation and Interaction**: APIs for creating, editing, and deleting posts (images), comments and likes.
- **Friend Management**: APIs for sending, accepting, and rejecting friend requests, and retrieving friend lists.
- **Privacy Settings**: Endpoints to manage user privacy settings for posts and profiles.
- **Search Functionality**: Search APIs for finding users.
- **User Management**: Admin endpoints for viewing profiles and banning users.
- **Analytics and Reporting**: APIs for user engagement metrics (e.g., active users, post interactions) and content performance.
- **Role Management**: Super admin endpoints for assigning administrative roles.

## Non-Functional Requirements
- **Scalability**: Supports at least 10,000 concurrent users without performance degradation.
- **Security**: Implements data encryption (e.g., passwords) and secure authentication.
- **Performance**: Ensures response times under 200ms and database performance during peak loads.
- **Reliability**: Achieves 99.9% uptime with a recovery time objective (RTO) of less than 1 hour.
- **Data Integrity**: Prevents data corruption with consistent database transactions.
- **Privacy**: Complies with data protection regulations.

## Tech Stack
- **Framework**: Spring Boot (3.3.11)
- **Database**: MySQL
- **API**: RESTful APIs with JSON
- **Version Control**: Git & GitHub

## Prerequisites
- Java 21
- Maven
- MySQL (v8 or higher)
- Access to a running MySQL database

## Installation
1. Clone the repository:
   ```powershell
   git clone https://github.com/abdelrahman218/social_media_platform_backend
   ```
2. Navigate to the project directory:
    ```bash
    cd social-media-platform-backend
    ```
3. Configure the database:
- Create a MySQL database named social_media_platform.
- Update application.properties with database credentials:

    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/social_media_platform
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```
4. Run backend:
- Run AppApplication.java
- Access the API at http://localhost:8080.

## Development
- **Code Structure**: Follow Spring Boot’s layered architecture (controllers, services, repositories).
- **Database**: Use MySQL.
- **Security**: Implement JWT for authentication and encrypt sensitive data.
- **Performance**: Optimize database queries and ensure response times under 200ms.
- **Maintainability**: Use modular code for future updates.

## Deployment

```powershell
mvn package
```
Deploy the JAR file to a server or use Docker. <br>
Ensure database scalability and monitor for 99.9% uptime. <br>
Test for data integrity and recovery time under 1 hour for critical failures.
