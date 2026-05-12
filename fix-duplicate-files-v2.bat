@echo off
echo Fixing duplicate files and redeclaration errors...

REM Remove duplicate files that are causing conflicts
if exist "app\src\main\java\com\minigrocery\app\ui\auth\LoginActivity_Fixed.kt" (
    del "app\src\main\java\com\minigrocery\app\ui\auth\LoginActivity_Fixed.kt"
    echo Deleted LoginActivity_Fixed.kt
) else (
    echo LoginActivity_Fixed.kt not found
)

if exist "app\src\main\java\com\minigrocery\app\ui\auth\LoginViewModel_Fixed.kt" (
    del "app\src\main\java\com\minigrocery\app\ui\auth\LoginViewModel_Fixed.kt"
    echo Deleted LoginViewModel_Fixed.kt
) else (
    echo LoginViewModel_Fixed.kt not found
)

echo.
echo Cleaning project...
call gradlew clean

echo.
echo Project should now build without redeclaration errors.
echo.
echo Please sync project in Android Studio and rebuild.
pause
