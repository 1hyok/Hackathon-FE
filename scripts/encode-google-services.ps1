# PowerShell 스크립트: google-services.json을 Base64로 인코딩
# 사용법: .\scripts\encode-google-services.ps1

param(
    [Parameter(Mandatory=$false)]
    [string]$InputFile = ".\app\google-services.json",
    
    [Parameter(Mandatory=$false)]
    [string]$OutputFile = "google-services-base64-oneline.txt"
)

Write-Host "🔐 google-services.json Base64 인코딩 중..." -ForegroundColor Cyan

# 입력 파일 확인
if (-not (Test-Path $InputFile)) {
    Write-Host "❌ 파일을 찾을 수 없습니다: $InputFile" -ForegroundColor Red
    Write-Host "`n💡 사용법:" -ForegroundColor Yellow
    Write-Host "   .\scripts\encode-google-services.ps1" -ForegroundColor White
    Write-Host "   또는" -ForegroundColor White
    Write-Host "   .\scripts\encode-google-services.ps1 -InputFile '.\app\google-services.json' -OutputFile 'output.txt'" -ForegroundColor White
    exit 1
}

try {
    # Base64 인코딩 (줄바꿈 없이)
    Write-Host "📖 파일 읽는 중: $InputFile" -ForegroundColor Yellow
    $base64String = [Convert]::ToBase64String([IO.File]::ReadAllBytes($InputFile))
    
    # 출력 파일에 저장
    $base64String | Out-File -FilePath $OutputFile -Encoding utf8 -NoNewline
    
    Write-Host "✅ 인코딩 완료!" -ForegroundColor Green
    Write-Host "📄 출력 파일: $OutputFile" -ForegroundColor Cyan
    Write-Host "📏 Base64 문자열 길이: $($base64String.Length) 문자" -ForegroundColor Cyan
    
    # 검증: 디코딩 테스트
    Write-Host "`n🔍 디코딩 테스트 중..." -ForegroundColor Yellow
    $decodedBytes = [Convert]::FromBase64String($base64String)
    $decodedJson = [System.Text.Encoding]::UTF8.GetString($decodedBytes)
    
    # JSON 유효성 간단 확인
    try {
        $json = $decodedJson | ConvertFrom-Json
        Write-Host "✅ JSON 유효성 검증 통과" -ForegroundColor Green
    } catch {
        Write-Host "⚠️  경고: JSON 파싱 실패 (파일이 손상되었을 수 있습니다)" -ForegroundColor Yellow
    }
    
    Write-Host "`n💡 다음 단계:" -ForegroundColor Cyan
    Write-Host "   1. 생성된 파일의 내용을 복사" -ForegroundColor White
    Write-Host "   2. GitHub 저장소 → Settings → Secrets → Actions" -ForegroundColor White
    Write-Host "   3. 'GOOGLE_SERVICES_JSON' Secret에 붙여넣기" -ForegroundColor White
    Write-Host "`n   또는 GitHub CLI 사용:" -ForegroundColor White
    Write-Host "   gh secret set GOOGLE_SERVICES_JSON < $OutputFile" -ForegroundColor Cyan
    
} catch {
    Write-Host "❌ 인코딩 실패: $_" -ForegroundColor Red
    exit 1
}
