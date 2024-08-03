# KrypTrade
KryptTrade is a Spring Boot-based cryptocurrency trading application designed to simplify trading and portfolio management. It provides functionalities for trading cryptocurrencies, tracking portfolio performance, and ensuring secure access through integrated security features.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- An IDE (e.g., IntelliJ IDEA, Eclipse)
- A database server (e.g., MySQL, MariaDB)
- Razorpay and Stripe API keys

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/kryptrade.git
cd kryptrade
```
## Configuration

Configure the database settings in `src/main/resources/application.properties` if you want to use a specific database:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password
   ```
Configure Razorpay, update the application.properties file with your Razorpay API key and secret:

   ```properties
razorpay.api.key=your_razorpay_api_key
razorpay.api.secret=your_razorpay_api_secret
stripe.api.key=your_stripe_api_key
 ```
## Database Design
The database design for Kryptrade is documented in the [db.md](db.md) file. This file includes details about the database schema, tables, relationships, and other relevant information needed to set up and understand the database structure.

You can find db.md in the root directory of the project
## Building The Application
 Build the project using Maven:

   ```
   mvn clean install
   ```

 Run the Spring Boot application:

   ```
   mvn spring-boot:run
   ```

The application will start, and you can access it at `http://localhost:8080`.



