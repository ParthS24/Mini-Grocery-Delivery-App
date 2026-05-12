@echo off
echo Removing duplicate files with wrong package name...

REM Delete the wrong package folder
if exist "app\src\main\java\com\example\minigrocerydeliveryapp" (
    echo Deleting: app\src\main\java\com\example\minigrocerydeliveryapp
    rmdir /s /q "app\src\main\java\com\example\minigrocerydeliveryapp"
    if !exist "app\src\main\java\com\example\minigrocerydeliveryapp" (
        echo Successfully deleted duplicate folder!
    ) else (
        echo Failed to delete duplicate folder.
    )
) else (
    echo Duplicate folder not found.
)

echo.
echo Cleaning project...
call gradlew clean

echo.
echo Done! Please sync project in Android Studio.
pause
