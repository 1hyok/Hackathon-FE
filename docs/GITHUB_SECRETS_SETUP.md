# GitHub Secrets 설정 가이드

## Base64 인코딩 오류 해결

`base64: invalid input` 오류의 가장 흔한 원인은 **Base64 문자열에 포함된 줄바꿈(Newlines)** 때문입니다.

## 올바른 Base64 인코딩 방법

### Windows (PowerShell) - 권장

```powershell
# 줄바꿈 없이 인코딩 (권장)
[Convert]::ToBase64String([IO.File]::ReadAllBytes(".\app\google-services.json")) | Out-File -Encoding utf8 google-services-base64-oneline.txt

# 또는 한 줄로
[Convert]::ToBase64String([IO.File]::ReadAllBytes(".\app\google-services.json")) | Set-Content google-services-base64-oneline.txt
```

**생성된 파일의 내용을 복사하여 GitHub Secrets에 등록하세요.**

### Mac (macOS)

```bash
# 줄바꿈 없이 인코딩
base64 -i app/google-services.json -o google-services-base64-oneline.txt
```

### Linux (Ubuntu 등)

```bash
# -w 0 옵션이 핵심입니다 (줄바꿈 끄기)
base64 -w 0 app/google-services.json > google-services-base64-oneline.txt
```

## GitHub Secrets 등록 방법

### 방법 1: GitHub 웹 UI 사용 (권장)

1. GitHub 저장소로 이동
2. **Settings** → **Secrets and variables** → **Actions** 클릭
3. **New repository secret** 클릭
4. **Name**: `GOOGLE_SERVICES_JSON`
5. **Secret**: 위에서 생성한 base64 문자열 전체를 붙여넣기
   - **중요**: 줄바꿈이 없어야 합니다!
   - 전체 문자열을 한 번에 복사/붙여넣기
6. **Add secret** 클릭

### 방법 2: GitHub CLI 사용

```bash
# 파일에서 직접 읽어서 등록
gh secret set GOOGLE_SERVICES_JSON < google-services-base64-oneline.txt

# 또는 직접 입력
gh secret set GOOGLE_SERVICES_JSON --body "$(cat google-services-base64-oneline.txt)"
```

### 방법 3: PowerShell 스크립트 사용

```powershell
# 스크립트 실행 (토큰 필요)
.\scripts\add-github-secret.ps1 -Token "your_github_token"
```

## 워크플로우 설정 확인

현재 워크플로우 파일 (`.github/workflows/deploy-apk.yml`)은 다음과 같이 설정되어 있습니다:

```yaml
- name: Create google-services.json
  env:
    GOOGLE_SERVICES_JSON: ${{ secrets.GOOGLE_SERVICES_JSON }}
  run: |
    # Base64 디코딩 (줄바꿈 제거 후 디코딩)
    echo "$GOOGLE_SERVICES_JSON" | tr -d '\n' | base64 --decode > app/google-services.json
    
    # 파일 생성 확인
    if [ ! -f app/google-services.json ]; then
      echo "❌ google-services.json 파일 생성 실패!"
      exit 1
    fi
    
    # JSON 유효성 검증
    if ! cat app/google-services.json | grep -q '"project_info"'; then
      echo "❌ google-services.json 파일이 유효한 JSON 형식이 아닙니다!"
      exit 1
    fi
    
    echo "✅ google-services.json 파일 생성 완료"
```

## 문제 해결

### 오류: `base64: invalid input`

**원인**: Base64 문자열에 줄바꿈이 포함되어 있음

**해결**:
1. GitHub Secrets에서 기존 `GOOGLE_SERVICES_JSON` 삭제
2. 위의 방법으로 줄바꿈 없이 다시 인코딩
3. 새로 생성된 base64 문자열을 Secrets에 등록

### 오류: `google-services.json 파일 생성 실패`

**원인**: Base64 디코딩 실패 또는 경로 문제

**해결**:
1. Base64 문자열이 올바른지 확인
2. 워크플로우에서 파일 경로 확인 (`app/google-services.json`)
3. Secrets 값에 공백이나 특수문자가 없는지 확인

### 오류: `유효한 JSON 형식이 아닙니다`

**원인**: 디코딩된 파일이 손상되었거나 잘못된 형식

**해결**:
1. 원본 `google-services.json` 파일이 올바른지 확인
2. Base64 인코딩/디코딩 과정에서 문제가 없는지 확인
3. 로컬에서 테스트:
   ```bash
   # 디코딩 테스트
   cat google-services-base64-oneline.txt | base64 --decode > test.json
   # JSON 검증
   cat test.json | jq .
   ```

## Best Practices

### 1. `.gitignore` 확인

`google-services.json` 파일은 절대 Git에 커밋하지 마세요:

```gitignore
# Google Services (e.g. APIs or Firebase)
google-services.json
google-services-base64*.txt
```

### 2. Secret 값 검증

Secret 등록 후 워크플로우가 자동으로 JSON 유효성을 검증합니다.

### 3. 로컬 테스트

워크플로우 실행 전 로컬에서 테스트:

```bash
# Base64 디코딩 테스트
cat google-services-base64-oneline.txt | base64 --decode > test.json

# JSON 검증 (jq 설치 필요)
cat test.json | jq .

# 파일 내용 확인
cat test.json
```

### 4. Secret 업데이트

Secret을 업데이트하려면:
1. GitHub 웹 UI에서 기존 Secret 삭제
2. 새로운 값으로 다시 등록

또는 GitHub CLI 사용:
```bash
gh secret set GOOGLE_SERVICES_JSON --body "$(cat google-services-base64-oneline.txt)"
```

## 참고 자료

- [GitHub Secrets 문서](https://docs.github.com/en/actions/security-guides/encrypted-secrets)
- [Base64 인코딩/디코딩](https://en.wikipedia.org/wiki/Base64)
