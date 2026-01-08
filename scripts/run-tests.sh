#!/bin/sh
# Bash 스크립트: 테스트 실행 자동화
# 사용법: ./scripts/run-tests.sh [테스트 클래스명]

TEST_CLASS="$1"

echo "🧪 Running tests..."

if [ -n "$TEST_CLASS" ]; then
    echo "📋 Running specific test: $TEST_CLASS"
    ./gradlew :app:testDebugUnitTest --tests "$TEST_CLASS"
else
    echo "📋 Running all unit tests..."
    ./gradlew :app:testDebugUnitTest
fi

if [ $? -ne 0 ]; then
    echo "❌ Tests failed!"
    exit 1
fi

echo "✅ All tests passed!"
exit 0
