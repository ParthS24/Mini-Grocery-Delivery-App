# Mini Grocery Delivery App

A complete Android grocery delivery application built with Kotlin, following MVVM architecture, similar to Blinkit/Zepto style apps.

## 📱 Features

### Core Features
- **User Authentication**: Phone number based login with OTP verification
- **Product Catalog**: Browse products by categories with search functionality
- **Shopping Cart**: Add/remove items with quantity management
- **Checkout Process**: Complete order placement with address and payment options
- **Order Management**: Order success tracking and confirmation
- **User Profile**: Profile management and settings

### Advanced Features
- **Favorites/Wishlist**: Save favorite products
- **Product Details**: Detailed product information view
- **Search & Filter**: Real-time product search and category filtering
- **Cart Badge**: Real-time cart item count display
- **Shimmer Loading**: Beautiful loading animations
- **Dark Mode**: System theme support (ready for implementation)
- **Responsive Design**: Optimized for different screen sizes

## 🏗️ Architecture

### MVVM Architecture
```
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── ProductDao.kt
│   │   └── CartDao.kt
│   ├── model/
│   │   ├── Product.kt
│   │   ├── CartItem.kt
│   │   ├── Category.kt
│   │   └── Order.kt
│   └── repository/
│       ├── ProductRepository.kt
│       ├── CartRepository.kt
│       └── UserRepository.kt
├── ui/
│   ├── auth/
│   │   ├── LoginActivity.kt
│   │   └── OtpActivity.kt
│   ├── home/
│   │   ├── HomeFragment.kt
│   │   └── adapters/
│   ├── cart/
│   │   ├── CartFragment.kt
│   │   └── CartAdapter.kt
│   ├── checkout/
│   │   ├── CheckoutActivity.kt
│   │   └── OrderSuccessActivity.kt
│   ├── product/
│   │   └── ProductDetailsActivity.kt
│   ├── profile/
│   │   └── ProfileFragment.kt
│   └── splash/
│       └── SplashActivity.kt
└── utils/
    ├── Constants.kt
    ├── Extensions.kt
    └── PreferenceManager.kt
```

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: XML Layouts (No Jetpack Compose)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database
- **Async**: Coroutines & Flow
- **Navigation**: Navigation Component
- **Images**: Glide
- **Animations**: Lottie
- **UI Components**: Material Design Components
- **View Binding**: Enabled
- **Data Binding**: Enabled

## 📦 Dependencies

### Core Dependencies
```gradle
// Android Core
implementation 'androidx.core:core-ktx:1.12.0'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'

// Architecture Components
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.7.0'
implementation 'androidx.navigation:navigation-fragment-ktx:2.7.6'
implementation 'androidx.navigation:navigation-ui-ktx:2.7.6'

// Room Database
implementation 'androidx.room:room-runtime:2.6.1'
implementation 'androidx.room:room-ktx:2.6.1'
ksp 'androidx.room:room-compiler:2.6.1'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'

// Image Loading
implementation 'com.github.bumptech.glide:glide:4.16.0'
ksp 'com.github.bumptech.glide:compiler:4.16.0'

// DataStore
implementation 'androidx.datastore:datastore-preferences:1.0.0'

// UI Components
implementation 'com.facebook.shimmer:shimmer:0.5.0'
implementation 'com.airbnb.android:lottie:6.2.0'
implementation 'de.hdodenhof:circleimageview:3.1.0'
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.9.10+

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run the application

### Build Configuration
```gradle
android {
    compileSdk 34
    defaultConfig {
        applicationId "com.minigrocery.app"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }
}
```

## 📱 App Screens

### 1. Splash Screen
- App logo with animation
- 2-second display duration
- Auto-navigation based on login status

### 2. Authentication Flow
- **Login Screen**: Phone number input with validation
- **OTP Screen**: 4-digit OTP verification
- Fake OTP: `1234`

### 3. Home Screen
- Search bar for product search
- Banner carousel with promotional content
- Category horizontal scroll
- Product grid layout (2 columns)
- Cart badge with item count

### 4. Product Details
- Product images gallery
- Detailed product information
- Add to cart with quantity selection
- Favorite toggle functionality
- Stock availability indicator

### 5. Shopping Cart
- Cart items list with quantity controls
- Price calculation (subtotal, delivery, total)
- Remove items functionality
- Empty cart state

### 6. Checkout
- Delivery address input
- Payment method selection (COD/Online)
- Order summary
- Form validation

### 7. Order Success
- Success animation
- Order ID and details
- Estimated delivery time
- Continue shopping options

### 8. Profile
- User information display
- Settings menu
- Logout functionality

## 🎨 UI/UX Design

### Design System
- **Primary Color**: Yellow (#FFC107)
- **Secondary Color**: Green (#4CAF50)
- **Background**: White/Gray shades
- **Typography**: Material Design type scale
- **Components**: Material Design 3

### Key UI Elements
- Rounded corners (8-16dp radius)
- Soft shadows and elevation
- Clean spacing (16dp margin standard)
- Material ripple effects
- Smooth transitions

## 📊 Database Schema

### Products Table
```sql
CREATE TABLE products (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    category TEXT,
    price REAL NOT NULL,
    discount INTEGER DEFAULT 0,
    originalPrice REAL,
    imageUrl TEXT,
    rating REAL DEFAULT 4.5,
    stock INTEGER DEFAULT 100,
    unit TEXT DEFAULT 'pcs',
    isFavorite INTEGER DEFAULT 0,
    isAvailable INTEGER DEFAULT 1
);
```

### Cart Items Table
```sql
CREATE TABLE cart_items (
    id TEXT PRIMARY KEY,
    productId TEXT NOT NULL,
    productName TEXT NOT NULL,
    productImage TEXT,
    price REAL NOT NULL,
    quantity INTEGER NOT NULL,
    unit TEXT,
    category TEXT,
    addedAt INTEGER DEFAULT (strftime('%s','now'))
);
```

## 🔧 Configuration

### Constants
- **Fake OTP**: `1234`
- **Delivery Charge**: ₹40
- **Free Delivery Threshold**: ₹500
- **Minimum Order**: ₹100

### Categories
- Fruits, Vegetables, Dairy, Snacks
- Beverages, Bakery, Meat, Personal Care

## 🧪 Testing

### Sample Data
The app includes 20+ sample products with:
- Realistic product names and descriptions
- Actual image URLs from Unsplash
- Various categories and price ranges
- Discount and rating information

### Test Credentials
- **Phone Number**: Any valid 10-digit number
- **OTP**: `1234`

## 📸 App Screenshots

### Main Screens
1. **Splash Screen**: Clean app branding
2. **Login Screen**: Phone number input
3. **OTP Screen**: 4-digit verification
4. **Home Screen**: Product browsing
5. **Cart Screen**: Shopping cart management
6. **Checkout**: Order placement
7. **Success**: Order confirmation
8. **Profile**: User settings

### UI Features
- Search functionality
- Category filtering
- Cart badge notifications
- Shimmer loading effects
- Smooth animations
- Responsive layouts

## 🔄 Data Flow

### Authentication Flow
```
Splash → Check Login Status
├── Logged In → Home
└── Not Logged In → Login → OTP → Home
```

### Shopping Flow
```
Home → Product Details → Add to Cart → Cart → Checkout → Success → Home
```

## 🚀 Performance Optimizations

### Database
- Room database with proper indexing
- Efficient queries with Flow
- Background thread operations

### UI
- ViewBinding for view access
- RecyclerView with DiffUtil
- Image caching with Glide
- Shimmer effects for loading states

### Memory
- Proper lifecycle management
- View recycling in RecyclerView
- Coroutine scope management

## 🔒 Security Considerations

### Data Storage
- User preferences encrypted with DataStore
- No sensitive data in SharedPreferences
- Local database for cart items only

### Input Validation
- Phone number format validation
- Required field validation
- SQL injection prevention with Room

## 🌐 Network Integration

### API Ready
The app is structured to easily integrate with real APIs:
- Retrofit for network calls
- Repository pattern for data sources
- Error handling and retry logic

### Sample Data
Currently uses local sample data for demonstration:
- 20+ realistic products
- 8 categories
- Proper image URLs

## 📱 Device Compatibility

### Minimum Requirements
- **Android Version**: 7.0 (API 24)+
- **RAM**: 2GB recommended
- **Storage**: 50MB available space

### Tested Devices
- Pixel 4+ series
- Samsung Galaxy S8+
- OnePlus devices
- Xiaomi devices

## 🛠️ Development Guidelines

### Code Quality
- Kotlin coding conventions
- MVVM architecture pattern
- Repository pattern implementation
- Proper error handling
- Comprehensive comments

### Best Practices
- Single Responsibility Principle
- Dependency Injection ready
- Testable architecture
- Clean code principles

## 📝️ Future Enhancements

### Planned Features
- Real API integration
- Push notifications
- Order history
- Multiple addresses
- Payment gateway integration
- Product reviews
- Wishlist sharing

### Technical Improvements
- Unit tests implementation
- UI tests with Espresso
- CI/CD pipeline
- Crash reporting
- Analytics integration

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create feature branch
3. Make changes
4. Test thoroughly
5. Submit pull request

### Code Standards
- Follow Kotlin conventions
- Maintain MVVM architecture
- Add proper comments
- Update documentation

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Support

For questions or support:
- Create an issue in the repository
- Check existing documentation
- Review code comments

---

**Built with ❤️ using Android, Kotlin, and Material Design**

<img width="407" height="826" alt="Screenshot 2026-07-23 182350" src="https://github.com/user-attachments/assets/ad649c08-e53d-4cc8-9f27-0d0602ed27c4" />
<img width="402" height="818" alt="Screenshot 2026-07-23 182418" src="https://github.com/user-attachments/assets/f7bebd17-379e-420b-a7ca-3af5929cfd76" />
<img width="396" height="816" alt="Screenshot 2026-07-23 182524" src="https://github.com/user-attachments/assets/418e5917-9d24-492e-9922-d68560fb0555" />
<img width="395" height="807" alt="Screenshot 2026-07-23 182645" src="https://github.com/user-attachments/assets/242ef20d-5b92-4ea3-a867-06f6ce9f175b" />
<img width="412" height="805" alt="Screenshot 2026-07-23 182732" src="https://github.com/user-attachments/assets/e55c8ad3-add2-4bb0-a631-8229bb7048fe" />
<img width="440" height="820" alt="Screenshot 2026-07-23 182752" src="https://github.com/user-attachments/assets/919e44cd-3d55-4992-b6a4-797b37983dc7" />
<img width="446" height="822" alt="Screenshot 2026-07-23 182806" src="https://github.com/user-attachments/assets/6d1b4649-a20b-475b-aef4-133b000c13cc" />
<img width="452" height="828" alt="Screenshot 2026-07-23 182824" src="https://github.com/user-attachments/assets/c433b476-0eff-4d1e-9c8a-dbdf3fbe95c9" />














