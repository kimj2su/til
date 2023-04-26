# 실전 카프카 Producer 애플리케이션 구현

# File에 텍스트가 추가될 때 마다 Producer 메시지 전송
new FileEventSource는 파일을 계속 모니터링하다가 new FileEventHandler()를 호출한다.
