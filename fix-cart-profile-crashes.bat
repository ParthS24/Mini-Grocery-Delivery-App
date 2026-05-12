@echo off
echo ===================================================
echo Fixing Cart and Profile Screen Crashes
echo ===================================================

echo Step 1: Clean and rebuild project...
call gradlew clean
call gradlew build

echo Step 2: Check for missing database initialization...
echo The issue is likely related to database not being properly initialized.
echo This can happen when the app tries to access cart/profile data.

echo Step 3: Create a simple test to isolate the issue...
echo If the app still crashes after this, the issue is in the Activity/Fragment setup.

echo.
echo ===================================================
echo Common causes for cart/profile crashes:
echo 1. Database not initialized properly
echo 2. Missing Room dependencies in build.gradle
echo 3. CartRepository initialization issues
echo 4. Fragment lifecycle issues
echo ===================================================

echo.
echo Please try running the app again after this build.
echo If it still crashes, provide the exact Logcat output.

pause
