#AmazonApp

## Description
This is a simple E-Commerce console-based application written in Java. The application allows users to place orders, view order history, and manage product stock. It includes basic functionality such as product selection, discount calculation, stock validation, and custom exception handling for out-of-stock scenarios.

## Features
- **Product Management**: Supports different product categories like Electronics and Clothing.
- **Order Processing**: Users can place orders, and the system validates stock availability.
- **Discount System**: Electronics have a 10% discount, and Clothing has a 15% discount.
- **Order History**: Users can view their past orders.
- **Stock Validation**: Prevents orders from being placed if stock is insufficient.
- **Exception Handling**: Uses a custom `OutOfStockException` for stock validation.

## Technologies Used
- Java
- Object-Oriented Programming (OOP) principles
- Exception Handling
- Collections Framework (List, Map)
- Stream API (for order total calculations)

## How to Run the Application
1. **Clone the Repository**
   ```sh
   git clone https://github.com/your-repository/ecommerce-app.git
   cd ecommerce-app
   
