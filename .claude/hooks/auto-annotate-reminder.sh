#!/usr/bin/env bash
# UserPromptSubmit hook — 코드 이해/동작 질문 시 답변 후 해당 코드에 주석/KDoc 을
# 자동 보강하도록 매 프롬프트에 reminder 를 additionalContext 로 주입한다.
# (이전엔 user-memory feedback_auto_annotate_on_question.md 에 있던 동작을 훅으로 일원화)
cat > /dev/null 2>&1  # stdin(prompt JSON) 소비 (SIGPIPE 방지)
cat <<'JSON'
{"hookSpecificOutput":{"hookEventName":"UserPromptSubmit","additionalContext":"[자동 주석 규칙] 이번 사용자 입력이 코드의 이해·동작·이유를 묻는 질문이라면, 채팅 답변만으로 끝내지 말고 답변 직후 질문 대상이 된 코드 파일에 질문 내용을 반영한 주석/KDoc 을 보강하라. 단순 작업 지시나 코드 수정 요청에는 적용하지 않는다."}}
JSON
