# Facts API Service

## Overview

The Facts API Service is designed to fetch random facts from an external API, shorten URLs, and track access statistics. This service is ideal for applications that provide users with interesting facts and easily shareable URLs. Key features include rate limiting to prevent abuse and caching to enhance performance.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Spring Cloud Gateway**: For API Gateway and routing.
- **Resilience4j**: For implementing rate limiting and resilience patterns.
- **WireMock**: For mocking external API calls in tests.
- **Lombok**: To reduce boilerplate code.
- **Java 17**: As the programming language.
- **Maven**: For project management and build.

## How to Run the Application

### Prerequisites

- Java 17

### Steps

1. **Clone the repository**
    ```sh
    git clone <repository_url>
    cd facts-api-service
    ```

2. **Build the project using Maven Wrapper**
    ```sh
    ./mvnw clean install
    ```

3. **Run the application**
    ```sh
    ./mvnw spring-boot:run
    ```

## Swagger URL

Once the application is running, you can access the Swagger UI for API documentation at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Core Features

### URL Shortening Algorithm

1. **Fetch Random Fact**: The service fetches a random fact from an external API.
2. **Generate Shortened URL**:
   - A unique numeric ID is generated using a counter.
   - This numeric ID is converted to a base62 string, ensuring the URL is shortened.
3. **Store the Fact**: The original fact and its corresponding shortened URL are stored in an in-memory repository.
4. **Retrieve Fact**: When a shortened URL is accessed, the service retrieves the original fact from the repository.

### Rate Limiting

Rate limiting is implemented using Resilience4j. It ensures that the number of requests to the service does not exceed a specified limit within a given time frame, protecting the service from being overwhelmed by too many requests.

### Caching

#### 80-20 Rule

The caching strategy follows the 80-20 rule, also known as the Pareto Principle. This rule suggests that roughly 80% of the traffic is generated by 20% of the data. By caching this 20% of frequently accessed data, we can significantly improve performance and reduce the load on the backend services.

#### Cache Strategies

- **LRU (Least Recently Used)**: This strategy evicts the least recently used items first. It's useful when we want to ensure that recently accessed items remain in the cache.

## Assumptions about Traffic and Lifespan

### Traffic Assumptions

- The system is designed to handle up to 1000 requests per second.
- The read-to-write ratio is assumed to be 100:1, meaning that for every 100 read requests, there is 1 write request.
- This ensures that the system can handle high read traffic efficiently, leveraging the cache for frequently accessed data.

### Lifespan Assumptions

- The shortened URLs are designed to be unique for up to 7 characters, providing a large number of unique combinations (62^7 ≈ 3.5 trillion).
- Given the rate of 1000 requests per second, the system can handle approximately:
   - 1000 requests/second * 60 seconds/minute * 60 minutes/hour * 24 hours/day * 365 days/year ≈ 31.5 billion requests per year.
- With the unique combination capacity and request rate, the system is designed to run for many years (over 100 years) without running out of unique URLs.

## Memory and Storage Requirements

### Memory Requirements

- **In-Memory Repository**:
   - Assuming each fact with its metadata takes approximately 1 KB of memory.
   - For 1 million facts, the memory requirement would be approximately 1 GB.
- **Cache**:
   - The cache will store the most frequently accessed facts.
   - If 20% of the facts are recently accessed (200,000 facts), the memory requirement for the cache would be approximately 200 MB.

### Storage Requirements

- **Persistent Storage**:
   - Each fact with its metadata stored on disk will also take approximately 1 KB.
   - For long-term storage, if the service handles 31.5 billion requests per year, and each fact is unique, the storage requirement would be approximately 31.5 TB per year.
   - However, given the read-to-write ratio (100:1), not all requests will result in new facts. Assuming 1% of requests generate new facts, the storage requirement would be approximately 315 GB per year.

By implementing these strategies and assumptions, the Facts API Service is built to be scalable, efficient, and long-lasting.
