plugins {
    id("hackathon.android.library")
}

android {
    namespace = "com.example.hackathon.core.domain"
}

dependencies {
    // repository 인터페이스의 공개 시그니처가 model/network 타입을 노출하므로 api 로 전파.
    api(project(":core:model"))
    // CombinationRepository 가 파라미터로 android.net.Uri 와 일부 DTO 를 직접 다뤄 network/android 의존.
    // TODO: 도메인 순수화(Uri·DTO 를 도메인 모델로 치환) 후 :core:network 의존 제거 — 별도 작업.
    api(project(":core:network"))
}
