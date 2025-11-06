@echo off
if exist gradlew (
  gradlew %*
) else (
  echo Please run with your local gradle or install the gradle wrapper.
  exit /b 1
)
