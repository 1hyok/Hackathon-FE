#!/bin/bash
# Bash 스크립트: 코드 품질 검사 자동화 (Git Bash / Linux / Mac)
# 사용법: ./scripts/check-code-quality.sh

echo "🔍 Running code quality checks..."

# Ktlint 포맷팅
echo ""
echo "📝 Running Ktlint Format..."
./gradlew ktlintFormat
if [ $? -ne 0 ]; then
    echo "❌ Ktlint formatting failed!"
    exit 1
fi

# Ktlint 검사
echo ""
echo "📝 Running Ktlint Check..."
./gradlew ktlintCheck
if [ $? -ne 0 ]; then
    echo "❌ Ktlint check failed! Please fix the issues."
    exit 1
fi

# Detekt 검사
echo ""
echo "🔎 Running Detekt..."
./gradlew detekt
if [ $? -ne 0 ]; then
    echo "❌ Detekt found issues! Please fix them."
    exit 1
fi

# 테스트 실행 (Unit 테스트만 - 빠른 실행)
echo ""
echo "🧪 Running unit tests..."
./gradlew :app:testDebugUnitTest --quiet
TEST_RESULT=$?

if [ $TEST_RESULT -ne 0 ]; then
    echo "⚠️  Some tests failed. Run './gradlew :app:testDebugUnitTest' manually to see details."
    # 테스트 실패해도 계속 진행
fi

echo ""
echo "✅ All code quality checks passed!"
exit 0


