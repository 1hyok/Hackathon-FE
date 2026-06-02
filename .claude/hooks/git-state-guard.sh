#!/usr/bin/env bash
# PreToolUse Bash hook: block dangerous git state changes without explicit user instruction.
#
# 차단 대상:
#   - git push --force (force push 만)
#   - git reset --hard
#   - git rebase
#   - git clean -f
#   - git checkout -- <file>  (작업 파일 폐기)
#   - git restore <file>      (작업 파일 폐기)
#
# 자율 허용 (사용자 지시 2026-06-01): git branch -D / -d, git checkout <branch> 는
# Claude 가 명시 지시 없이 수행 가능. 브랜치 삭제·전환은 손실 위험 낮고 머지된 브랜치 정리에 필요.
set -uo pipefail

input="$(cat)"
cmd="$(echo "$input" | jq -r '.tool_input.command // empty')"
[ -z "$cmd" ] && exit 0

deny() {
    jq -nc --arg reason "git state 변경은 사용자 명시 지시 필요. '$1' 자율 실행 금지." \
      '{hookSpecificOutput: {hookEventName: "PreToolUse", permissionDecision: "deny", permissionDecisionReason: $reason}}'
    exit 0
}

# git push — force push 만 차단. 일반 push 와 --delete 는 통과.
# Force push (-f, --force, --force-with-lease, +refspec) 는 commit 손실 위험 → 차단 유지.
if [[ "$cmd" =~ (^|[[:space:]]|\;|\&|\|)git[[:space:]]+push([[:space:]]|$) ]]; then
    if [[ "$cmd" =~ git[[:space:]]+push[[:space:]].*(-f([[:space:]]|$)|--force([[:space:]]|=|$)|--force-with-lease) ]] \
        || [[ "$cmd" =~ git[[:space:]]+push[[:space:]]+[^[:space:]]+[[:space:]]+\+ ]]; then
        deny "git push --force"
    fi
fi

# git reset --hard
if [[ "$cmd" =~ git[[:space:]]+reset[[:space:]].*--hard ]]; then
    deny "git reset --hard"
fi

# git rebase
if [[ "$cmd" =~ (^|[[:space:]]|\;|\&|\|)git[[:space:]]+rebase([[:space:]]|$) ]]; then
    deny "git rebase"
fi

# git branch -D / -d (브랜치 삭제) — 사용자 지시로 자율 허용 (2026-06-01). 차단하지 않음.

# git clean -f / -fd / -ffd 등
if [[ "$cmd" =~ git[[:space:]]+clean[[:space:]].*-[a-zA-Z]*f ]]; then
    deny "git clean -f"
fi

# git checkout -- <file> (작업 파일 폐기). 브랜치 전환(git checkout <branch>)은 허용.
if [[ "$cmd" =~ git[[:space:]]+checkout[[:space:]]+-- ]]; then
    deny "git checkout --"
fi

# git restore <file> (작업 파일 폐기)
if [[ "$cmd" =~ (^|[[:space:]]|\;|\&|\|)git[[:space:]]+restore([[:space:]]|$) ]]; then
    deny "git restore"
fi

exit 0
