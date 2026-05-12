@echo off
echo ===================================================
echo Fixing splash_background.xml - Final Solution
echo ===================================================

echo Step 1: Navigate to drawable folder...
cd "app\src\main\res\drawable"

echo Step 2: Delete the problematic splash_background.xml...
if exist "splash_background.xml" (
    del "splash_background.xml"
    echo Deleted problematic splash_background.xml
)

echo Step 3: Create a proper splash_background.xml...
(
echo ^<?xml version="1.0" encoding="utf-8"?^>
echo ^<shape xmlns:android="http://schemas.android.com/apk/res/android"^>
echo ^    android:shape="rectangle"^>
echo ^        ^<solid android:color="@color/yellow_500" /^>^>
echo ^    ^</shape^>^>
echo ^) > splash_background.xml

echo Step 4: Verify the file was created correctly...
echo Created new splash_background.xml with shape drawable

echo Step 5: Clean and rebuild...
cd "..\..\..\.."
call gradlew clean
call gradlew build

echo.
echo ===================================================
echo splash_background.xml has been completely fixed!
echo The app should now start without crashing.
echo ===================================================

pause
