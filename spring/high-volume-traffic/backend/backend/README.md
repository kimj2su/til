
#
```java
public class User {
    @CreatedDate
    @Column(insertable = true)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

create table user (
        created_at datetime(6),
id bigint not null auto_increment,
last_login_at datetime(6),
updated_at datetime(6),
username varchar(30) not null,
email varchar(50) not null,
password varchar(100) not null,
primary key (id)
    ) engine=InnoDB
```

위의 엔티티를 보면 `createdAt` 필드에 `@CreatedDate` 어노테이션을 사용하였고, `updatedAt` 필드에 `@LastModifiedDate` 어노테이션을 사용하였다.   
이렇게 하면 JPA가 엔티티를 저장할 때 자동으로 `createdAt` 필드에 현재 시간을 저장하고, 엔티티를 업데이트할 때 자동으로 `updatedAt` 필드에 현재 시간을 저장한다.  
create 문을 보면 updated_at 필드를 만들어주지만 뒤에 다른 명령어가 보이지 않는다.  
 
SELECT NOT() 현재시간을 구하는 쿼리를 클러스터나 마스터/슬레이브 구조에서는 마스터의 시간을 카피해서 사용하거나 각 디비의 서버 시간을 사용하게 되는데 이때 시간이 다르게 나올 수 있다.  
이는 JPA가 자동으로 처리해주기 때문이다. 그래서 동시성문제등이 있기 때문에 코드레벨에서만 처리를 하고 스키마에는 적용하지 않는다.