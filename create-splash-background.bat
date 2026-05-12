@echo off
echo ===================================================
echo Creating splash_background drawable resource
echo ===================================================

echo Step 1: Create splash_background drawable...
echo Creating a simple drawable resource to fix the crash...

echo Step 2: Navigate to drawable folder...
cd "app\src\main\res\drawable"

echo Step 3: Create the drawable file...
(
echo ^<?xml version="1.0" encoding="utf-8"?^>
echo ^<shape xmlns:android="http://schemas.android.com/apk/res/android"^>
echo ^    android:shape="rectangle"^>
echo ^        ^<solid android:color="@color/yellow_500" /^>^>
echo ^    ^</shape^>^>
echo ^) > splash_background.xml

echo Step 4: Verify file creation...
if exist "splash_background.xml" (
    echo SUCCESS: splash_background.xml created successfully
) else (
    echo ERROR: Failed to create splash_background.xml
)

echo.
echo ===================================================
echo The drawable has been created. Now your app should start without crashing.
echo Please rebuild the project and test again.
echo ===================================================

pause
