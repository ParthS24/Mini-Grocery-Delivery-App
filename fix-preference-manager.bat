@echo off
echo ===================================================
echo Fix PreferenceManager Redeclaration Error
echo ===================================================

echo Step 1: Remove duplicate files...
if exist "app\src\main\java\com\minigrocery\app\utils\PreferenceManager.kt" (
    del "app\src\main\java\com\minigrocery\app\utils\PreferenceManager.kt"
    echo Deleted original PreferenceManager.kt
) else (
    echo Original PreferenceManager.kt not found
)

if exist "app\src\main\java\com\minigrocery\app\utils\PreferenceManager_Fixed.kt" (
    ren "app\src\main\java\com\minigrocery\app\utils\PreferenceManager_Fixed.kt" "PreferenceManager.kt"
    echo Renamed PreferenceManager_Fixed.kt to PreferenceManager.kt
) else (
    echo PreferenceManager_Fixed.kt not found
)

echo Step 2: Clean project...
call gradlew clean

echo Step 3: Rebuild project...
call gradlew build

echo ===================================================
echo PreferenceManager redeclaration error should be fixed!
echo ===================================================

pause
