# E-Commerce Backend API Documentation

## Base URL

```
http://localhost:4999/api
```

## Common Response Wrapper

All API responses follow the `ApiResponse<T>` envelope:

```json
{
  "success": true,
  "message": "string",
  "data": { },
  "timestamp": "2026-06-08T12:00:00.000"
}
```

- `success` — boolean indicating success/failure
- `message` — human readable message
- `data` — the actual response payload (generic type `T`)
- `timestamp` — ISO 8601 server timestamp

Error responses:

```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2026-06-08T12:00:00.000"
}
```

## Paginated Response (`PageResponse<T>`)

Used for list endpoints with pagination.

```json
{
  "success": true,
  "message": "Success",
  "data": {
    "content": [ ],
    "page": 0,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10,
    "last": false,
    "first": true
  }
}
```

**Default pagination values:**
- `page = 0`
- `size = 10`
- `sortBy = "id"`
- `sortDir = "asc"`

---

# Enums Reference

| Enum | Values |
|---|---|
| `UserRole` | `CUSTOMER`, `ADMIN` |
| `UserStatus` | `ACTIVE`, `INACTIVE`, `BLOCKED` |
| `OrderStatus` | `PENDING`, `CONFIRMED`, `SHIPPED`, `DELIVERED`, `CANCELLED` |
| `PaymentStatus` | `PENDING`, `COMPLETED`, `FAILED`, `REFUNDED` |
| `PaymentMethod` | `UPI`, `CREDIT_CARD`, `DEBIT_CARD`, `NET_BANKING`, `COD` |

---

# Authentication (AuthController)

Base: `/api/auth`

## Register

**POST** `/api/auth/register`

**Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "mobileNumber": "9876543210",
  "password": "password123",
  "confirmPassword": "password123"
}
```

**Response:** `ApiResponse<UserResponse>`

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "mobileNumber": "9876543210",
  "role": "CUSTOMER",
  "status": "ACTIVE",
  "createdAt": "2026-06-08T12:00:00"
}
```

---

## Login

**POST** `/api/auth/login`

**Request Body:**

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:** `ApiResponse<JwtResponse>`

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "dGhpcyBpcyBhIHJlZnJl...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "mobileNumber": "9876543210",
    "role": "CUSTOMER",
    "status": "ACTIVE",
    "createdAt": "2026-06-08T12:00:00"
  }
}
```

---

## Refresh Token

**POST** `/api/auth/refresh-token`

**Request Body:**

```json
{
  "refreshToken": "dGhpcyBpcyBhIHJlZnJl..."
}
```

**Response:** `ApiResponse<JwtResponse>` (same structure as Login)

---

## Forgot Password

**POST** `/api/auth/forgot-password`

**Request Body:**

```json
{
  "email": "john@example.com"
}
```

**Response:** `ApiResponse<Void>` — message: `"OTP sent to your email"`

---

## Reset Password

**POST** `/api/auth/reset-password`

**Request Body:**

```json
{
  "otp": "123456",
  "email": "john@example.com",
  "newPassword": "newpassword123",
  "confirmPassword": "newpassword123"
}
```

**Response:** `ApiResponse<Void>` — message: `"Password reset successfully"`

---

# Users (UserController)

Base: `/api/users` (requires authentication)

## Get Profile

**GET** `/api/users/profile`

**Response:** `ApiResponse<UserResponse>`

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "mobileNumber": "9876543210",
  "role": "CUSTOMER",
  "status": "ACTIVE",
  "createdAt": "2026-06-08T12:00:00"
}
```

---

## Update Profile

**PUT** `/api/users/profile`

**Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Smith",
  "mobileNumber": "9876543210"
}
```

**Response:** `ApiResponse<UserResponse>` — message: `"Profile updated successfully"`

---

# Categories (CategoryController)

Base: `/api/categories`

## Create Category (Admin)

**POST** `/api/categories`

**Request Body:**

```json
{
  "name": "Electronics",
  "description": "Electronic items",
  "status": true
}
```

**Response:** `ApiResponse<CategoryResponse>`

```json
{
  "id": 1,
  "name": "Electronics",
  "description": "Electronic items",
  "status": true
}
```

---

## Get All Categories

**GET** `/api/categories`

**Response:** `ApiResponse<List<CategoryResponse>>`

```json
[
  { "id": 1, "name": "Electronics", "description": "...", "status": true },
  { "id": 2, "name": "Clothing", "description": "...", "status": true }
]
```

---

## Get Category By ID

**GET** `/api/categories/{id}`

**Response:** `ApiResponse<CategoryResponse>`

---

## Update Category (Admin)

**PUT** `/api/categories/{id}`

**Request Body:** Same as Create

**Response:** `ApiResponse<CategoryResponse>` — message: `"Category updated successfully"`

---

## Delete Category (Admin)

**DELETE** `/api/categories/{id}`

**Response:** `ApiResponse<Void>` — message: `"Category deleted successfully"`

---

# Products (ProductController)

Base: `/api/products`

## Create Product (Admin)

**POST** `/api/products`

**Request Body:**

```json
{
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "sku": "IP15-128-BLK",
  "price": 999.99,
  "salePrice": 899.99,
  "stockQuantity": 50,
  "brand": "Apple",
  "status": true,
  "categoryId": 1
}
```

**Response:** `ApiResponse<ProductResponse>`

```json
{
  "id": 1,
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "sku": "IP15-128-BLK",
  "price": 999.99,
  "salePrice": 899.99,
  "stockQuantity": 50,
  "brand": "Apple",
  "status": true,
  "categoryId": 1,
  "categoryName": "Electronics",
  "images": ["http://example.com/img1.jpg"],
  "createdAt": "2026-06-08T12:00:00",
  "updatedAt": "2026-06-08T12:00:00"
}
```

---

## Get All Products (Paginated)

**GET** `/api/products?page=0&size=10&sortBy=id&sortDir=asc`

**Response:** `ApiResponse<PageResponse<ProductResponse>>`

---

## Get Product By ID

**GET** `/api/products/{id}`

**Response:** `ApiResponse<ProductResponse>`

---

## Update Product (Admin)

**PUT** `/api/products/{id}`

**Request Body:** Same as Create

**Response:** `ApiResponse<ProductResponse>` — message: `"Product updated successfully"`

---

## Delete Product (Admin)

**DELETE** `/api/products/{id}`

**Response:** `ApiResponse<Void>` — message: `"Product deleted successfully"`

---

## Search Products

**GET** `/api/products/search?name=&category=&brand=&minPrice=&maxPrice=&sortBy=&page=0&size=10`

**Response:** `ApiResponse<PageResponse<ProductResponse>>`

---

# Product Images (ProductImageController)

Base: `/api/products`

## Add Images (Admin)

**POST** `/api/products/{productId}/images` (Content-Type: multipart/form-data)

**Form Data:** `files` (List of MultipartFile)

**Response:** `ApiResponse<List<ProductImageResponse>>`

```json
[
  { "id": 1, "imageUrl": "http://example.com/img1.jpg" },
  { "id": 2, "imageUrl": "http://example.com/img2.jpg" }
]
```

---

## Get Product Images

**GET** `/api/products/{productId}/images`

**Response:** `ApiResponse<List<ProductImageResponse>>`

---

## Delete Image (Admin)

**DELETE** `/api/products/images/{imageId}`

**Response:** `ApiResponse<Void>` — message: `"Image deleted successfully"`

---

# Addresses (AddressController)

Base: `/api/addresses` (requires authentication)

## Create Address

**POST** `/api/addresses`

**Request Body:**

```json
{
  "fullName": "John Doe",
  "mobile": "9876543210",
  "addressLine1": "123 Main St",
  "addressLine2": "Apt 4B",
  "city": "New York",
  "state": "NY",
  "country": "USA",
  "postalCode": "10001"
}
```

**Response:** `ApiResponse<AddressResponse>`

```json
{
  "id": 1,
  "fullName": "John Doe",
  "mobile": "9876543210",
  "addressLine1": "123 Main St",
  "addressLine2": "Apt 4B",
  "city": "New York",
  "state": "NY",
  "country": "USA",
  "postalCode": "10001"
}
```

---

## Get User's Addresses

**GET** `/api/addresses`

**Response:** `ApiResponse<List<AddressResponse>>`

---

## Update Address

**PUT** `/api/addresses/{addressId}`

**Request Body:** Same as Create

**Response:** `ApiResponse<AddressResponse>` — message: `"Address updated successfully"`

---

## Delete Address

**DELETE** `/api/addresses/{addressId}`

**Response:** `ApiResponse<Void>` — message: `"Address deleted successfully"`

---

# Cart (CartController)

Base: `/api/cart` (requires authentication)

## Get Cart

**GET** `/api/cart`

**Response:** `ApiResponse<CartResponse>`

```json
{
  "id": 1,
  "userId": 1,
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "productImage": "http://example.com/img1.jpg",
      "price": 999.99,
      "salePrice": 899.99,
      "quantity": 2,
      "subTotal": 1799.98
    }
  ],
  "totalItems": 2,
  "totalAmount": 1799.98
}
```

---

## Add Item

**POST** `/api/cart/items`

**Request Body:**

```json
{
  "productId": 1,
  "quantity": 2
}
```

**Response:** `ApiResponse<CartResponse>` — message: `"Item added to cart"`

---

## Update Item Quantity

**PUT** `/api/cart/items/{itemId}`

**Request Body:** Same as Add Item

**Response:** `ApiResponse<CartResponse>` — message: `"Cart item updated"`

---

## Remove Item

**DELETE** `/api/cart/items/{itemId}`

**Response:** `ApiResponse<CartResponse>` — message: `"Item removed from cart"`

---

## Clear Cart

**DELETE** `/api/cart`

**Response:** `ApiResponse<CartResponse>` — message: `"Cart cleared"`

---

# Orders (OrderController)

Base: `/api/orders` (requires authentication)

## Place Order

**POST** `/api/orders`

**Request Body:**

```json
{
  "addressId": 1,
  "couponCode": "SAVE10"
}
```

**Response:** `ApiResponse<OrderResponse>`

```json
{
  "id": 1,
  "orderNumber": "ORD-A1B2C3D4",
  "totalAmount": 899.99,
  "status": "PENDING",
  "paymentStatus": "PENDING",
  "createdAt": "2026-06-08T12:00:00",
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "productImage": "http://example.com/img1.jpg",
      "quantity": 1,
      "price": 899.99,
      "subTotal": 899.99
    }
  ],
  "shippingAddress": {
    "id": 1,
    "fullName": "John Doe",
    "mobile": "9876543210",
    "addressLine1": "123 Main St",
    "addressLine2": "Apt 4B",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "postalCode": "10001"
  }
}
```

---

## Get User's Orders (Paginated)

**GET** `/api/orders?page=0&size=10`

**Response:** `ApiResponse<PageResponse<OrderResponse>>`

---

## Get Order History (Paginated)

**GET** `/api/orders/history?page=0&size=10`

**Response:** `ApiResponse<PageResponse<OrderResponse>>`

---

## Get Order By ID

**GET** `/api/orders/{id}`

**Response:** `ApiResponse<OrderResponse>`

---

## Cancel Order

**PUT** `/api/orders/{id}/cancel`

**Response:** `ApiResponse<OrderResponse>` — message: `"Order cancelled successfully"`

---

# Payments (PaymentController)

Base: `/api/payments`

## Process Payment

**POST** `/api/payments`

**Request Body:**

```json
{
  "orderId": 1,
  "paymentMethod": "UPI"
}
```

**Response:** `ApiResponse<PaymentResponse>`

```json
{
  "id": 1,
  "orderId": 1,
  "transactionId": "TXN123456789",
  "amount": 899.99,
  "paymentMethod": "UPI",
  "paymentStatus": "COMPLETED",
  "createdAt": "2026-06-08T12:00:00"
}
```

---

## Get Payment By Order ID

**GET** `/api/payments/{orderId}`

**Response:** `ApiResponse<PaymentResponse>`

---

# Reviews (ReviewController)

Base: `/api/reviews`

## Add Review

**POST** `/api/reviews`

**Request Body:**

```json
{
  "productId": 1,
  "rating": 4,
  "comment": "Great product!"
}
```

**Response:** `ApiResponse<ReviewResponse>`

```json
{
  "id": 1,
  "productId": 1,
  "userId": 1,
  "userName": "John Doe",
  "rating": 4,
  "comment": "Great product!",
  "createdAt": "2026-06-08T12:00:00"
}
```

---

## Get Product Reviews

**GET** `/api/reviews/product/{productId}`

**Response:** `ApiResponse<List<ReviewResponse>>`

---

## Delete Review

**DELETE** `/api/reviews/{reviewId}`

**Response:** `ApiResponse<Void>` — message: `"Review deleted successfully"`

---

# Wishlist (WishlistController)

Base: `/api/wishlist` (requires authentication)

## Add to Wishlist

**POST** `/api/wishlist`

**Request Body:**

```json
{
  "productId": 1
}
```

**Response:** `ApiResponse<WishlistResponse>`

```json
{
  "id": 1,
  "productId": 1,
  "productName": "iPhone 15",
  "productImage": "http://example.com/img1.jpg",
  "price": 999.99,
  "salePrice": 899.99
}
```

---

## Get Wishlist

**GET** `/api/wishlist`

**Response:** `ApiResponse<List<WishlistResponse>>`

---

## Remove from Wishlist

**DELETE** `/api/wishlist/{wishlistId}`

**Response:** `ApiResponse<Void>` — message: `"Product removed from wishlist"`

---

# Coupons (CouponController)

Base: `/api/coupons` (Admin)

## Create Coupon (Admin)

**POST** `/api/coupons`

**Request Body:**

```json
{
  "code": "SAVE10",
  "discountPercentage": 10.00,
  "startDate": "2026-06-01",
  "endDate": "2026-07-01",
  "active": true
}
```

**Response:** `ApiResponse<CouponResponse>`

```json
{
  "id": 1,
  "code": "SAVE10",
  "discountPercentage": 10.00,
  "startDate": "2026-06-01",
  "endDate": "2026-07-01",
  "active": true
}
```

---

## Get All Coupons (Admin)

**GET** `/api/coupons`

**Response:** `ApiResponse<List<CouponResponse>>`

---

## Update Coupon (Admin)

**PUT** `/api/coupons/{id}`

**Request Body:** Same as Create

**Response:** `ApiResponse<CouponResponse>` — message: `"Coupon updated successfully"`

---

## Delete Coupon (Admin)

**DELETE** `/api/coupons/{id}`

**Response:** `ApiResponse<Void>` — message: `"Coupon deleted successfully"`

---

# Inventory (InventoryController)

Base: `/api/inventory`

## Get Inventory By Product

**GET** `/api/inventory/{productId}`

**Response:** `ApiResponse<InventoryResponse>`

```json
{
  "id": 1,
  "productId": 1,
  "productName": "iPhone 15",
  "productSku": "IP15-128-BLK",
  "availableQuantity": 48,
  "reservedQuantity": 2
}
```

---

## Update Inventory (Admin)

**PUT** `/api/inventory/{productId}`

**Request Body:**

```json
{
  "availableQuantity": 100,
  "reservedQuantity": 5
}
```

**Response:** `ApiResponse<InventoryResponse>` — message: `"Inventory updated successfully"`

---

## Get Low Stock Items (Admin)

**GET** `/api/inventory/low-stock?threshold=10`

**Response:** `ApiResponse<List<InventoryResponse>>`

---

# Notifications (NotificationController)

Base: `/api/notifications` (requires authentication)

## Get Notifications (Paginated)

**GET** `/api/notifications?page=0&size=10`

**Response:** `ApiResponse<PageResponse<NotificationResponse>>`

```json
{
  "id": 1,
  "title": "Order Confirmed",
  "message": "Your order ORD-A1B2C3D4 has been confirmed.",
  "type": "EMAIL",
  "isRead": false,
  "createdAt": "2026-06-08T12:00:00"
}
```

---

## Get Unread Count

**GET** `/api/notifications/unread-count`

**Response:** `ApiResponse<Map<String, Long>>`

```json
{
  "unreadCount": 3
}
```

---

## Mark as Read

**PUT** `/api/notifications/{id}/read`

**Response:** `ApiResponse<Void>` — message: `"Notification marked as read"`

---

## Mark All as Read

**PUT** `/api/notifications/read-all`

**Response:** `ApiResponse<Void>` — message: `"All notifications marked as read"`

---

# Reports (ReportController)

Base: `/api/reports` (Admin only)

## Sales Report

**GET** `/api/reports/sales?startDate=2026-05-09&endDate=2026-06-08`

**Response:** `ApiResponse<ReportResponse>`

```json
{
  "reportType": "SALES",
  "data": { },
  "generatedAt": "2026-06-08T12:00:00"
}
```

---

## Orders Report

**GET** `/api/reports/orders?startDate=2026-05-09&endDate=2026-06-08`

**Response:** `ApiResponse<ReportResponse>`

---

## Customers Report

**GET** `/api/reports/customers`

**Response:** `ApiResponse<ReportResponse>`

---

## Inventory Report

**GET** `/api/reports/inventory`

**Response:** `ApiResponse<ReportResponse>`

---

> `ReportResponse.data` contains a dynamic `Map<String, Object>` whose structure depends on the report type.

---

# DTO Field Summary

## Request DTOs

| DTO | Field | Type | Constraints |
|---|---|---|---|
| **RegisterRequest** | firstName | String | NotBlank, Size(2-50) |
| | lastName | String | NotBlank, Size(2-50) |
| | email | String | NotBlank, Email |
| | mobileNumber | String | NotBlank, Pattern(10 digits) |
| | password | String | NotBlank, Size(6-100) |
| | confirmPassword | String | NotBlank |
| **LoginRequest** | email | String | NotBlank, Email |
| | password | String | NotBlank |
| **RefreshTokenRequest** | refreshToken | String | NotBlank |
| **ForgotPasswordRequest** | email | String | NotBlank, Email |
| **ResetPasswordRequest** | otp | String | NotBlank |
| | email | String | NotBlank |
| | newPassword | String | NotBlank, Size(6-100) |
| | confirmPassword | String | NotBlank |
| **UserProfileRequest** | firstName | String | NotBlank, Size(2-50) |
| | lastName | String | NotBlank, Size(2-50) |
| | mobileNumber | String | NotBlank, Pattern(10 digits) |
| **CategoryRequest** | name | String | NotBlank |
| | description | String | |
| | status | Boolean | |
| **ProductRequest** | name | String | NotBlank |
| | description | String | |
| | sku | String | NotBlank |
| | price | BigDecimal | NotNull, Positive |
| | salePrice | BigDecimal | PositiveOrZero |
| | stockQuantity | Integer | NotNull, PositiveOrZero |
| | brand | String | |
| | status | Boolean | |
| | categoryId | Long | NotNull |
| **AddressRequest** | fullName | String | NotBlank |
| | mobile | String | NotBlank |
| | addressLine1 | String | NotBlank |
| | addressLine2 | String | |
| | city | String | NotBlank |
| | state | String | NotBlank |
| | country | String | NotBlank |
| | postalCode | String | NotBlank |
| **CartItemRequest** | productId | Long | NotNull |
| | quantity | Integer | NotNull, Positive |
| **OrderRequest** | addressId | Long | NotNull |
| | couponCode | String | |
| **PaymentRequest** | orderId | Long | NotNull |
| | paymentMethod | PaymentMethod | NotNull (enum) |
| **ReviewRequest** | productId | Long | NotNull |
| | rating | Integer | NotNull, Min(1), Max(5) |
| | comment | String | |
| **CouponRequest** | code | String | NotBlank |
| | discountPercentage | BigDecimal | NotNull, DecimalMin(0.01), DecimalMax(100.00) |
| | startDate | LocalDate | NotNull |
| | endDate | LocalDate | NotNull |
| | active | Boolean | |
| **InventoryRequest** | availableQuantity | Integer | NotNull, PositiveOrZero |
| | reservedQuantity | Integer | PositiveOrZero |
| **WishlistRequest** | productId | Long | NotNull |

## Response DTOs

| DTO | Fields |
|---|---|
| **JwtResponse** | accessToken, refreshToken, tokenType, expiresIn, user (UserResponse) |
| **UserResponse** | id, firstName, lastName, email, mobileNumber, role, status, createdAt |
| **CategoryResponse** | id, name, description, status |
| **ProductResponse** | id, name, description, sku, price, salePrice, stockQuantity, brand, status, categoryId, categoryName, images (List), createdAt, updatedAt |
| **ProductImageResponse** | id, imageUrl |
| **AddressResponse** | id, fullName, mobile, addressLine1, addressLine2, city, state, country, postalCode |
| **CartResponse** | id, userId, items (List of CartItemResponse), totalItems, totalAmount |
| **CartItemResponse** | id, productId, productName, productImage, price, salePrice, quantity, subTotal |
| **OrderResponse** | id, orderNumber, totalAmount, status, paymentStatus, createdAt, items (List of OrderItemResponse), shippingAddress (AddressResponse) |
| **OrderItemResponse** | id, productId, productName, productImage, quantity, price, subTotal |
| **PaymentResponse** | id, orderId, transactionId, amount, paymentMethod, paymentStatus, createdAt |
| **ReviewResponse** | id, productId, userId, userName, rating, comment, createdAt |
| **CouponResponse** | id, code, discountPercentage, startDate, endDate, active |
| **InventoryResponse** | id, productId, productName, productSku, availableQuantity, reservedQuantity |
| **NotificationResponse** | id, title, message, type, isRead, createdAt |
| **WishlistResponse** | id, productId, productName, productImage, price, salePrice |
| **ReportResponse** | reportType, data (Map<String, Object>), generatedAt |
