#

```java
public class User {

  @CreatedDate
  @Column(insertable = true)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;
}

create table

user(
    created_at datetime(6),

id bigint
not null auto_increment,

last_login_at datetime(6),

updated_at datetime(6),

username varchar(30) not null,

email varchar(50) not null,

password varchar(100) not null,

primary key(id)
    )engine=InnoDB
```

위의 엔티티를 보면 `createdAt` 필드에 `@CreatedDate` 어노테이션을 사용하였고, `updatedAt` 필드에 `@LastModifiedDate` 어노테이션을
사용하였다.   
이렇게 하면 JPA가 엔티티를 저장할 때 자동으로 `createdAt` 필드에 현재 시간을 저장하고, 엔티티를 업데이트할 때 자동으로 `updatedAt` 필드에 현재 시간을
저장한다.  
create 문을 보면 updated_at 필드를 만들어주지만 뒤에 다른 명령어가 보이지 않는다.

SELECT NOT() 현재시간을 구하는 쿼리를 클러스터나 마스터/슬레이브 구조에서는 마스터의 시간을 카피해서 사용하거나 각 디비의 서버 시간을 사용하게 되는데 이때 시간이 다르게
나올 수 있다.  
이는 JPA가 자동으로 처리해주기 때문이다. 그래서 동시성문제등이 있기 때문에 코드레벨에서만 처리를 하고 스키마에는 적용하지 않는다.

# 실제 서비스 중에 스키마 수정이 필요한 경우

## Online DDL(Online Data Definition Language)

- 테이블 구조를 변경할 때, 가용성을 유지하며 변경을 수행할 수 있는 기능
- 내장된 기능올 추가적인 설치나 설정이 필요 없음
- MySQL에서는 InnoDB를 사용하여 Online DDL 작업을 지원

```
ALTER TABLE example_table ADD COLUMN email VARCHAR(255),ALGORITHM=INPLACE,LOCK=NONE;
ALTER TABLE example_table ADD INDEX idx_content(content),ALGORITHM=INPLACE,LOCK=NONE;
ALTER TABLE example_table MODIFY COLUMN content VARCHAR(255),ALGORITHM=INPLACE,LOCK=NONE;
```

- ALGORITHM
    - INPLACE: 테이블 Lock 없이 온라인으로 작업 수행
    - COPY: 테이블 복사본을 생성해서 작업, 테이블 Lock이 필요할 수 있음
- LOCK
    - NONE: CRUD 작업 모두 허용
    - SHARED: 읽기만 허용
    - EXCLUSIVE: 모두 차단

## Online DDL 특징 및 장점

- 비차단 작업
    - 테이블을 변경하는 동안 읽기 / 쓰기 작업 가능
    - 전체 테이블 Lock이 필요 X
    - (일부 작업이나 매우 큰 변경 사항은 Lock이 발생할 수 있음)
- 효율성
    - 테이블 복사본을 만들지 않고, 필요한 부분만 수정
    - 디스크, 메모리 사용량이 적어 DB 성능에 미치는 영향을 최소화

## Percona Toolkit

- Percona에서 제공하는 MySQL / MariaDB 관리 Tool
- 주요 기능
    - pt-online-schema-change: Online DDL과 비슷한 기능
    - pt-table-checksum: Master/Slave 간의 데이터 무결성을 확인
    - pt-table-sync: Master/Slave 간의 데이터 동기화
    - pt-query-digest: 쿼리 로그를 분석하여 가장 비용이 많이 드는 쿼리를 분석/최적화

- Online DDL과의 차이점
    - 더 많은 기능과 유연성을 제공하고 대규모/고가용성 환경에서 유용함
        - Online DDL은 복잡한 스키마 변경은 대부분 테이블 Lock이 발생함
        - ALGORITHM=INPLACE로 설정시 성능 저하가 발생할 수 있음
    - 작업시 모니터링 가능(예 - progress bar)
    - 테이블을 복사 후 swap하는 방식(Trigger 방식)

## Percona Toolkit 설치

- https://docs.percona.com/percona-toolkit/installation.html

- brew install percona-toolkit

## pt-online-schema-change 사용법

```
pt-online-schema-change --alter "ADD INDEX comment_author_id_index ()" D=example-db,t=example_table --host=localhost --password=password --user=root --execute --recursion-method=none
```

# Elasticsearch

jdbc clinet - https://www.elastic.co/kr/downloads/past-releases#jdbc-client

- Apache Lucene
    - Java로 작성된 고성능 full-text 검색 라이브러리
    - 문서를 분석하여 index(색인)을 생성
    - 텍스트는 토큰으로 분할하고 각 토큰의 위치와 빈도를 기록하여 검색을 효율적으로 만듦
    - 검색을 관련성(유사도) 점수로 정렬하여 관련성이 높은 문서를 상위에 표시
- Elasticsearch
    - Apache Lucene 기반의 실시간 분산 검색 및 분석 엔진
    - JSON 기반의 RESTful API를 통해 상호 작용
    - 실시간으로 데이터를 처리해서 즉시 검색 가능하도록 제공
    - 다양한 집계 기능(aggregation)

# ELK Stack

- Elasticsearch
    - 역할 : 분산 검색 및 분석 엔진
    - 기능 : 대량의 구조화된 및 비구조화된 데이터를 실시간으로 저장, 검색, 분석.
- Logstash
    - 역할: 데이터 수집 및 처리 파이프라인
    - 기능: 다양한 소스(서버로그, 애플리케이션 로그등)로부터 데이터를 수집, 변환, 전송. 데이터를 Elasticsearch에 저장할 수 있도록 처리
- Kibana
    - 역할 : 데이터 시각화 및 대시보드 도구.
    - 기능 : Elasticsearch에 저장된 데이터를 기반으로 대시보드, 차트, 지도 등을 생성하여 시각화. 실시간으로 데이터 분석과 모니터링이 가능.

# kibana 주요기능

- 데이터 시각화 : 차트, 그래프, 지도, 타임라인...
- 대시보드 : 차트 활용하여 실시간 데이터 모니터링
- 데이터 탐색(검색) : Lucene와 Kibana Query Language(KQL)를 사용하여 데이터를 검색
- 경보 및 보고서: 대시보드(=차트) 기반 보고서 생성 및 이메일 전송
- Canvas: 사용자 지정 디자인 및 애니메이션을 포함한 인터렉티브한 데이터 시각화

- 로그 분석
    - kubernetes, on-premise 서버의 log 및 metric 제공
- 보안 모니터링
    - 네트워크 트래픽, 시스템 로그, 보안 이벤트 등을 모니터링하여 보안 위험 탐지 및 대응
- 운영 업무
    - 서버 및 어플리케이션 리소스 모니터링
    - 비즈니스 데이터(매출, 사용자 행동 등)을 시각화
    - 운영 업무(관리자, CX ) 모니터링 -> 당일, 전일(D-1) 조회 수 상위 10위 게시글 리스트 조회

# Logstash 주요 기능

- 데이터 수집(input)
    - 입력 플로그인을 통해 여러 소스로부터 데이터를 수집
    - 파일, 데이터베이스, 메시지 큐, 클라우드 서비스 등에서 데이터를 가져올 수 있음
- 데이터 변환(Filter)
    - 데이터를 변환하고 처리하기 위한 필터 플러그인ㄴ을 제공
    - 필터를 데이터를 정리, 변환, 구조화하여 분석 가능한 형태로 변경 함
- 데이터 출력(output)
    - 변환된 데이터를 다양한 출력 플러그인을 통해 전송
    - 일반적인 출력 대상은 Elasticsearch이지만, 다른 시스템(ex- 파일, HTTP)으로도 전송 가능
- 파이프라인 구성
    - 복잡한 데이터 처리 파이프라인을 구성 가능
    - 여러 입력, 필터, 출력 단계를 경합하여 데이터 흐름(flow)을 정의
    - 각 단계는 독립적으로 구성할 수 있으며 조건문을 사용하여 특정 조건에 따라 데이터를 처리할 수도 있음

# beats 역할 및 기능

- 경량 에이전트이자 데이터 수집기로, 다양한 소스에서 데이터를 수집하여 Elasticsearch나 Logstash로 전송
- 특정 유형의 데이터를 수집하기 위해서 사전 정의된 모듈을 제공(ex - MySQL, HaProxt, Kafka, AWS...)

## beats의 종류

- Filebeat: 로그 수집
- Metricbeat: 시스템 메트릭(CPU, 메모리, 디스크 사용량) 수집
- Packetbeat: 네트워크 트래픽 캡처 및 성능/보안 상태 모니터링
- Heartbeat: 웹 서비스, DB 가용성 모니터링
- Auditbeat: 시스템 보안 이벤트 수집 및 사용자 활동 추적

# 아키텍처 설명

## Standalone 모드

- 단일 서버로 운영되는 방식
    - 반대는 Cluster 모드
- 1개의 서버에서 모든 기능을 동작시킴
    - 데이터 저장, 검색, incdexing, cluster management
- 장점
    - 설정과 관리가 상대적으로 간단하고 외부 서버와의 통신을 고려할 필요가 없음
- 단점
    - 대규모 데이터 처리가 어려움
    - 고가용성 기능이 없으므로 장애 발생시 서비스 가용성에 영향을 줌

## 클러스터 아키텍처 구조

- Node(노드) : 물리/가상 서버
    - Master Node
        - 클러스터 상태 및 모든 노드 관리
        - shard 할당, index 생성
    - Data Node
        - 실제 데이터를 저장(CRUD 처리)
        - shard(primary, replica) 저장
    - Coordinate Node
        - 검색 요청을 다른 노드에 라우팅(분산)
        - 마스터, 데이터 노드와 직접 상호 작용X

## Shard(샤드)

- Shard
    - index를 구성하는 기본 단위
    - data를 분산 저장하여 데이터 손실을 방지함
    - 특정 node에 병목되지 않도록 분산 처리하기 위함
- 종류
    - primary: 실제로 저장하는 기본 shard
    - replica: primary shard의 복제본
        - 고가용성 및 검색 성능 향상

## Shard != replica

- Shard: data를 분산 저장하기 위한 개념
- Replica: shard의 복제본

## Document

- ElasticSearch에서 가장 작은 단위
- JSON 형식의 데이터
- 지원하는 데이터 타입
    - binary
    - boolean
    - keyword
    - number
    - date
    - object(json)
    - nested(relationship)
    - text

## index / Type

- index(인덱스)
    - data(document)를 저장하는 논리적 단위
    - 각 index는 고유한 이름을 가지고 여러개의 shard로 구성
- type(타입)
    - index 내부의 논리적인 데이터 구조
    - index는 여러개의 type을 가질 수 있음
    - 7.x 버전부터는 type을 사용하지 않음, index:type=1:1로 매핑된다.

# inverted index(역 인덱스) 주요 개념

- term: 문서에 등장하는 개별 단어 or 토큰
    - 형태소: 일정한 의미가 있는 가장 작은 말의 단위
- posting list: 특정 term이 등장하는 모든 문서의 리스트, 해당 term의 위치 정보도 포함
- 단어-문서 매핑: 역 인덱스는 단어를 key로 해당 단어가 포함된 문서 ID의 리스트를 값을 가지는 매핑을 제공

# mapping

- document가 저장될 때 각 field의 data type을 정의하는 schema
    - field data type
        - text, keyword, integer, date, boolean...
    - analyzer
      -text field에 대해 어떤 분석기를 사용할 것인지 정의
    - field property
        - index, store, doc_values 여부 지정 가능
    - muliple field
        - 하나의 text field를 text와 keyword type으로 동시에 indexing 가능

# settings

- index의 동작 방식을 제어하는 설정(shard, replica, analyzer...)
    - number_of_shards
        - 데이터를 분할하여 저장하는 단위
    - number_of_replicas
        - primary shard의 복제본 수, 데이터의 고가용성을 보장
    - analysis
        - 형태소 분석기, 토크나이저, 토큰 필터 등을 정의
    - refresh_interval
        - index 갱신 주기, 기본값은 1초

# brain split 문제

- 클러스터 내의 노드들이 네트워크 분리 또는 기타 장애로 인해 서로 통신할 수 없게 되면서 각 노드가 자신이 마스터 노드라고 잘못 판단하는 현상
    - 분산 시스템, 특히 클러스터 환경에서 발생할 수 있는 문제
    - 일부 노드들이 서로 통신할 수 없게 되어 각 노드가 자신이 마스터라고 잘못 판단할 때 발생
    - 데이터 일관성 문제와 클러스터의 안정성 저하를 초래
    - 최악의 경우, 시스템 복구 불가능 초래

## brain split 방지 방법

- Master Node는 홀수개로 구성
- Minimum Master Nodes 설정
    - 클러스터가 최소 몇 개의 마스터 노드와 통신할 수 있어야 정상적으로 작동할지를 정의
    - 이 설정을 통해 클러스터가 여러 개의 마스터 노드를 생성하는 것을 방지
    - 예) discovery.zen.minimum_master_nodes:2
- Zen Discovery
    - 클러스터 내의 노드들이 서로를 발견하고, 통신을 관리하며, 네트워크 분리를 감지하여 Brain Split 문제를 방지

# re-indexing 문제

- setting, mapping 를 변경해야되는 상황
    - shard, replica, field type, dictionary, analyzer 변경
- ElasticSearch Cluster 버전 업그레이드
    - 호환성을 위해서 데이터를 다시 indexing 해야하는 경우가 있음
- 성능 최적화
    - 데이터 구조 최적화, 삭제된 문서 처리, 불필요한 데이터 제거
    - analyzer 최적화, 데이터 볼륨 증가

# re-indexing이 필요한 경우

- rolling re-indexing
    - 기존 index는 유지하고 새로운 인덱스를 생성하여 점진적으로 데이터를 복사
    - 끝난 뒤에 application이 새로운 인덱스를 참조하도록 변경
- 일괄 처리
    - 데이터 세트를 여러개의 작은 배치로 나눠서 성능 저하를 최소화하여 진행
- re-index API 사용
    - 편하긴 하지만 mapping이 잘못되어 field 타입이나 이름이 충돌나면 오류가 발생할 수 있음
    - 문제가 발생했을 때 원인 파악이 쉽지 않음