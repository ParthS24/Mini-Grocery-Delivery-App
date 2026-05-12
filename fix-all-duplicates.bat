@echo off
echo ===================================================
echo Fixing All Duplicate Files and Conflicts
echo ===================================================

echo Step 1: Removing duplicate files...
if exist "app\src\main\java\com\minigrocery\app\ui\auth\LoginActivity_Fixed.kt" del /f /q "app\src\main\java\com\minigrocery\app\ui\auth\LoginActivity_Fixed.kt"
if exist "app\src\main\java\com\minigrocery\app\ui\auth\LoginViewModel_Fixed.kt" del /f /q "app\src\main\java\com\minigrocery\app\ui\auth\LoginViewModel_Fixed.kt"

echo Step 2: Removing wrong package folder...
if exist "app\src\main\java\com\example\minigrocerydeliveryapp" (
    echo Found wrong package folder, removing...
    rmdir /s /q "app\src\main\java\com\example\minigrocerydeliveryapp"
    if !exist "app\src\main\java\com\example\minigrocerydeliveryapp" (
        echo Successfully removed wrong package folder
    ) else (
        echo Wrong package folder not found
    )
) else (
    echo Wrong package folder not found
)

echo Step 3: Cleaning project...
call gradlew clean

echo Step 4: Rebuilding project...
call gradlew build

echo ===================================================
echo All fixes applied! Please check for any remaining errors.
echo ===================================================

pause
