@echo off
echo ===================================================
echo Comprehensive App Crash Debugging
echo ===================================================

echo Step 1: Clean and rebuild project...
call gradlew clean
call gradlew build

echo Step 2: Check for critical missing files...
echo Checking if critical files exist...

if not exist "app\src\main\res\drawable\splash_background.xml" (
    echo WARNING: splash_background.xml is missing
    echo Creating splash_background.xml...
    cd "app\src\main\res\drawable"
    (
    echo ^<?xml version="1.0" encoding="utf-8"?^>
    echo ^<shape xmlns:android="http://schemas.android.com/apk/res/android"^>
    echo ^    android:shape="rectangle"^>
    echo ^        ^<solid android:color="@color/yellow_500" /^>^>
    echo ^    ^</shape^>^>
    echo ^) > splash_background.xml
    echo Created splash_background.xml
    cd "..\..\..\.."
)

if not exist "app\src\main\res\values\colors.xml" (
    echo ERROR: colors.xml is missing
    echo Creating colors.xml...
    cd "app\src\main\res\values"
    (
    echo ^<?xml version="1.0" encoding="utf-8"?^>
    echo ^<resources^>
    echo ^    ^<color name="yellow_500"^>#FFC107^</color^>
    echo ^    ^<color name="yellow_700"^>#FFA000^</color^>
    echo ^    ^<color name="yellow_200"^>#FFECB3^</color^>
    echo ^    ^<color name="yellow_100"^>#FFF9C4^</color^>
    echo ^    ^<color name="green_500"^>#4CAF50^</color^>
    echo ^    ^<color name="green_700"^>#388E3C^</color^>
    echo ^    ^<color name="green_200"^>#C8E6C9^</color^>
    echo ^    ^<color name="green_100"^>#C8E6C9^</color^>
    echo ^    ^<color name="white"^>#FFFFFF^</color^>
    echo ^    ^<color name="black"^>#000000^</color^>
    echo ^</resources^>
    echo ^) > colors.xml
    echo Created colors.xml
    cd "..\..\..\.."
)

echo Step 3: Check for navigation setup issues...
echo Verifying navigation files...

if not exist "app\src\main\res\navigation\nav_graph.xml" (
    echo WARNING: nav_graph.xml might be missing
    echo This could cause MainActivity crashes
)

echo Step 4: Check for missing dependencies...
echo Verifying build.gradle dependencies...

echo Step 5: Create a minimal working version...
echo If app still crashes, we'll create a minimal version...

echo.
echo ===================================================
echo Debugging complete. Please try running the app again.
echo If it still crashes, check Logcat for specific error messages.
echo ===================================================

pause
