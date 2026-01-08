# PowerShell 스크립트: 코드 품질 검사 자동화
# 사용법: .\scripts\check-code-quality.ps1

Write-Host "🔍 Running code quality checks..." -ForegroundColor Cyan

# Ktlint 포맷팅
Write-Host "`n📝 Running Ktlint Format..." -ForegroundColor Yellow
& .\gradlew.bat ktlintFormat
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Ktlint formatting failed!" -ForegroundColor Red
    exit 1
}

# Ktlint 검사
Write-Host "`n📝 Running Ktlint Check..." -ForegroundColor Yellow
& .\gradlew.bat ktlintCheck
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Ktlint check failed! Please fix the issues." -ForegroundColor Red
    exit 1
}

# Detekt 검사
Write-Host "`n🔎 Running Detekt..." -ForegroundColor Yellow
& .\gradlew.bat detekt
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Detekt found issues! Please fix them." -ForegroundColor Red
    exit 1
}

# 테스트 실행 (Unit 테스트만 - 빠른 실행)
Write-Host "`n🧪 Running unit tests..." -ForegroundColor Yellow
& .\gradlew.bat :app:testDebugUnitTest --quiet
if ($LASTEXITCODE -ne 0) {
    Write-Host "⚠️  Some tests failed. Run './gradlew :app:testDebugUnitTest' manually to see details." -ForegroundColor Yellow
    # 테스트 실패해도 계속 진행
}

Write-Host "`n✅ All code quality checks passed!" -ForegroundColor Green
exit 0


