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
