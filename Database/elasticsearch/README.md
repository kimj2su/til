# Elasticsearch

### 1. Elasticsearch란?
모든 사람이 필요한 것을 더 빨리 찾도록하기 위한 광범위한 개방형 검색 플랫폼입니다.  
HTTP의 JSON 인터페이스를 지원, 다양한 프로그래밍 언어 지원 

### 2. Elasticsearch의 용도
- 검색 엔진
- 분석 및 인사이트 제공
  - 로그 분석
  - 이벤트 분석
  - 성능 분석
- 머신러닝

### 3. Elasticstack
- Elasticsearch가 개발될 무렵 진행된 오픈소스 프로젝트
  - 로그 수집 - 로그스테시
  - 시각화 UI - 키바나

### 4. Elasticsearch의 특징
- 용도 - 검색 및 집계
- 스키마 - 자동 생성
- 인터펭스 - REST API
- 분산 적제 - 샤딩
- 트랜잭션 - 미지언
- JOIN - 미지원

### 5. Elasticsearch의 요청과 응답
- 모든 동작을 REST API로 수행
- 입력 - PUT
- 조회 - GET
- 수정 - POST
- 삭제 - DELETE

### 6. Elasticsearch의 데이터 구조
- Index
  - 데이터를 저장하는 단위
  - RDBMS의 Database와 유사
  - 다큐먼트를 모은 논리적 구조
- 다큐먼트 - 데이터가 저장되는 기본 단위
- 매핑 - 스키마구조

### 7. Elasticsearch와 RDB 비교
| 엘라스틱 서치 | RDB |
|-------|-----|
| 인덱스   | 테이블 |
| 도큐먼트  | 행(ROW) |
| 필드    | 컬럼(COL) |
| 매핑    | 스키마 |