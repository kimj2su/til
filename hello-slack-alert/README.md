# 저장소 설명
슬랙을 이용한 중요 에러 알림 샘플 프로젝트

### 필수 의존성
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'com.slack.api:slack-api-client:1.27.2'
}
```
# Create App
http://api.slack.com

  From scratch 클릭

왼쪽에 Incoming Webhooks 클릭 -> off를 on으로 변경 -> Add New Webhook to Workspace 클릭 후 채널 선택

curl 과 Webhook URL이 만들어진다.