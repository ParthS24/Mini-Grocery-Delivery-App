@echo off
echo Fixing Gradle issues...

echo 1. Cleaning Gradle cache...
rd /s /q "%USERPROFILE%\.gradle\caches" 2>nul

echo 2. Cleaning Gradle wrapper...
rd /s /q ".gradle" 2>nul

echo 3. Rebuilding Gradle wrapper...
call gradle wrapper --gradle-version 8.4

echo 4. Cleaning project...
call gradlew clean

echo 5. Rebuilding project...
call gradlew build --refresh-keys

echo Gradle fix completed!
echo Please try syncing the project in Android Studio now.
pause
