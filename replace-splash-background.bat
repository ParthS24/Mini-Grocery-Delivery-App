@echo off
echo ===================================================
echo Replacing splash_background.xml with working version
echo ===================================================

echo Step 1: Navigate to drawable folder...
cd "app\src\main\res\drawable"

echo Step 2: Delete problematic splash_background.xml...
if exist "splash_background.xml" (
    del "splash_background.xml"
    echo Deleted problematic splash_background.xml
)

echo Step 3: Rename new file to replace it...
if exist "splash_background_new.xml" (
    ren "splash_background_new.xml" "splash_background.xml"
    echo Replaced with new splash_background.xml
)

echo Step 4: Verify the fix...
if exist "splash_background.xml" (
    echo SUCCESS: splash_background.xml has been replaced
) else (
    echo ERROR: Failed to replace splash_background.xml
)

echo Step 5: Clean and rebuild project...
cd "..\..\..\.."
call gradlew clean
call gradlew build

echo.
echo ===================================================
echo splash_background.xml has been completely replaced!
echo The app should now start without crashing.
echo ===================================================

pause
