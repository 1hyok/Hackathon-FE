# PowerShell 스크립트: 테스트 실행 자동화
# 사용법: .\scripts\run-tests.ps1 [테스트 클래스명]

param(
    [string]$TestClass = ""
)

Write-Host "🧪 Running tests..." -ForegroundColor Cyan

if ($TestClass -ne "") {
    Write-Host "`n📋 Running specific test: $TestClass" -ForegroundColor Yellow
    & .\gradlew.bat :app:testDebugUnitTest --tests $TestClass
} else {
    Write-Host "`n📋 Running all unit tests..." -ForegroundColor Yellow
    & .\gradlew.bat :app:testDebugUnitTest
}

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n❌ Tests failed!" -ForegroundColor Red
    exit 1
}

Write-Host "`n✅ All tests passed!" -ForegroundColor Green
exit 0
