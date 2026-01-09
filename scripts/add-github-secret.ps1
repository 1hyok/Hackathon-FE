# PowerShell 스크립트: GitHub Secret 자동 추가
# 사용법: .\scripts\add-github-secret.ps1

param(
    [Parameter(Mandatory=$true)]
    [string]$Token,
    
    [Parameter(Mandatory=$false)]
    [string]$SecretName = "GOOGLE_SERVICES_JSON",
    
    [Parameter(Mandatory=$false)]
    [string]$RepoOwner = "1hyok",
    
    [Parameter(Mandatory=$false)]
    [string]$RepoName = "Hackathon-FE"
)

Write-Host "🔐 GitHub Secret 추가 중..." -ForegroundColor Cyan

# Base64 파일 읽기 (여러 파일명 지원)
$base64Files = @(
    "google-services-base64-oneline.txt",
    "google-services-base64.txt"
)

$base64File = $null
foreach ($file in $base64Files) {
    if (Test-Path $file) {
        $base64File = $file
        break
    }
}

if (-not $base64File) {
    Write-Host "❌ Base64 파일을 찾을 수 없습니다!" -ForegroundColor Red
    Write-Host "   찾는 파일: $($base64Files -join ', ')" -ForegroundColor Yellow
    Write-Host "`n💡 Base64 파일 생성 방법:" -ForegroundColor Cyan
    Write-Host "   [Convert]::ToBase64String([IO.File]::ReadAllBytes('.\app\google-services.json')) | Out-File -Encoding utf8 google-services-base64-oneline.txt" -ForegroundColor White
    exit 1
}

Write-Host "📄 파일 사용: $base64File" -ForegroundColor Green
$secretValue = Get-Content $base64File -Raw | ForEach-Object { $_.Trim() }

# 줄바꿈 제거 확인
if ($secretValue -match "`r`n" -or $secretValue -match "`n") {
    Write-Host "⚠️  경고: Base64 문자열에 줄바꿈이 포함되어 있습니다. 제거 중..." -ForegroundColor Yellow
    $secretValue = $secretValue -replace "`r`n|`n|`r", ""
}

# GitHub API: Repository public key 가져오기
Write-Host "`n📥 Repository public key 가져오는 중..." -ForegroundColor Yellow
$headers = @{
    "Authorization" = "Bearer $Token"
    "Accept" = "application/vnd.github.v3+json"
}

$publicKeyUrl = "https://api.github.com/repos/$RepoOwner/$RepoName/actions/secrets/public-key"
try {
    $publicKeyResponse = Invoke-RestMethod -Uri $publicKeyUrl -Method Get -Headers $headers
    $publicKey = $publicKeyResponse.key
    $keyId = $publicKeyResponse.key_id
    Write-Host "✅ Public key 가져오기 성공" -ForegroundColor Green
} catch {
    Write-Host "❌ Public key 가져오기 실패: $_" -ForegroundColor Red
    exit 1
}

# Secret 값을 public key로 암호화
Write-Host "`n🔒 Secret 값 암호화 중..." -ForegroundColor Yellow
try {
    # .NET을 사용하여 암호화
    Add-Type -AssemblyName System.Security
    
    $publicKeyBytes = [System.Convert]::FromBase64String($publicKey)
    $secretBytes = [System.Text.Encoding]::UTF8.GetBytes($secretValue)
    
    # RSA 암호화
    $rsa = New-Object System.Security.Cryptography.RSACryptoServiceProvider
    $rsa.ImportSubjectPublicKeyInfo($publicKeyBytes, [ref]$null)
    $encryptedBytes = $rsa.Encrypt($secretBytes, $false)
    $encryptedValue = [System.Convert]::ToBase64String($encryptedBytes)
    
    Write-Host "✅ 암호화 완료" -ForegroundColor Green
} catch {
    Write-Host "❌ 암호화 실패: $_" -ForegroundColor Red
    Write-Host "`n💡 대안: GitHub CLI를 사용하세요:" -ForegroundColor Yellow
    Write-Host "   gh secret set $SecretName --body `"$secretValue`"" -ForegroundColor Cyan
    exit 1
}

# GitHub API: Secret 추가/업데이트
Write-Host "`n📤 GitHub Secret 업로드 중..." -ForegroundColor Yellow
$secretUrl = "https://api.github.com/repos/$RepoOwner/$RepoName/actions/secrets/$SecretName"
$body = @{
    encrypted_value = $encryptedValue
    key_id = $keyId
} | ConvertTo-Json

try {
    Invoke-RestMethod -Uri $secretUrl -Method Put -Headers $headers -Body $body -ContentType "application/json"
    Write-Host "`n✅ GitHub Secret '$SecretName' 추가 완료!" -ForegroundColor Green
    Write-Host "   저장소: $RepoOwner/$RepoName" -ForegroundColor Cyan
} catch {
    Write-Host "`n❌ Secret 추가 실패: $_" -ForegroundColor Red
    if ($_.Exception.Response.StatusCode -eq 401) {
        Write-Host "   토큰이 유효하지 않거나 권한이 없습니다." -ForegroundColor Yellow
    } elseif ($_.Exception.Response.StatusCode -eq 404) {
        Write-Host "   저장소를 찾을 수 없습니다." -ForegroundColor Yellow
    }
    Write-Host "`n💡 대안: GitHub CLI를 사용하세요:" -ForegroundColor Yellow
    Write-Host "   gh secret set $SecretName --body `"$secretValue`"" -ForegroundColor Cyan
    exit 1
}

Write-Host "`n🎉 완료! 이제 GitHub Actions에서 이 Secret을 사용할 수 있습니다." -ForegroundColor Green
