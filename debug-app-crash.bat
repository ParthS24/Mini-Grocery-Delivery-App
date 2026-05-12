@echo off
echo ===================================================
echo App Crash Debugging Script
echo ===================================================

echo Step 1: Clean and rebuild...
call gradlew clean
call gradlew build

echo Step 2: Check for missing dependencies...
echo Checking if all required dependencies are available...

echo Step 3: Verify MainActivity initialization...
echo Looking for potential issues in MainActivity...

echo Step 4: Check AndroidManifest.xml configuration...
echo Verifying manifest configuration...

echo Step 5: Test app in debug mode...
echo.
echo IMPORTANT: Run the app in DEBUG MODE to see specific crash details
echo 1. Click the Debug button (next to Run button)
echo 2. Check Logcat for error messages
echo 3. Look for "FATAL EXCEPTION" messages
echo.

echo ===================================================
echo If app still crashes, check these common causes:
echo 1. Missing R.id.nav_host_fragment in layout
echo 2. DataStore initialization issues
echo 3. Missing dependencies in build.gradle
echo 4. Navigation component setup problems
echo 5. ViewModel initialization errors
echo ===================================================

pause
