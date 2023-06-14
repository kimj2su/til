# 페이징 기능 성능 개선하기

## 1 - 1. No Offset은 왜 빠른가?
```sql
SELECT *
FROM members
WHERE 조건문
ORDER BY id DESC
OFFSET 페이지번호
LIMIT 페이지사이즈
```
보통 최신 -> 과거순으로 조회하기 때문에 desc를 붙였습니다.  
이와 같은 형태의 페이징 쿼리가 뒤로갈수록 느린 이유는 결국 앞에서 읽었던 행을 다시 읽어야 하기 때문입니다.  

예를 들어 offset 10000, limit 20 이라 하면 최종적으로 10,020개의 행을 읽어야 합니다. (10,000부터 20개를 읽어야함)  
이렇게 되면 앞의 만개의 행을 버리고 20개의 행을 읽어야 하기 때문에 뒤로 갈수록 느려집니다.
No Offset 방식은 바로 이 부분에서 조회 시작 부분을 인덱스로 빠르게 찾아 매번 첫 페이지만 읽도록 하는 방식입니다.

```sql
SELECT *
FROM members
WHERE 조건문
AND id < 마지막조회ID # 직전 조회 결과의 마지막 id
ORDER BY id DESC
LIMIT 페이지사이즈
```

이 전에 조회된 결과를 한번에 건너뛸수 있게 마지막 조회 결과의 ID를 조건문에 사용하여 매번 이전 페이지 전체를 건너 뛸 수 있음을 의미합니다.
즉, 아무리 페이지가 뒤로 가더라고 처음 페이지를 읽은 것과 동일한 성능을 가지게 됩니다.

## 기존의 페이징 코드
```java
@Repository
@RequiredArgsConstructor
public class MemberRepositoryFactory {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MemberDto> paginationLegacy(String name, int pageNo, int pageSize) {
        return jpaQueryFactory
                .select(Projections.fields(MemberDto.class,
                        member.id,
                        member.name,
                        member.age
                ))
                .from(member)
                .where(member.name.like(name + "%")) // like 는 뒤에 % 를 붙여야 인덱스가 작동함.
                .orderBy(member.id.desc())
                .offset((long) pageNo * pageSize) // 지정된 페이지 위치에서
                .limit(pageSize) // 지정된 사이즈 만큼
                .fetch();
    }
}
```
offset은 pageNo가 사용되는 것이 아니라 몇번째 row부터 시작할지를 나타냅니다.  
즉, offset 10000, limit 20 으로 지정해야 10,000부터 20개의 데이터를 조회할 수 있습니다.  
pageNo는 0부터 시작된다고 가정한 상태입니다.   
1부터 시작하실 분들은 (pageNo - 1) * pageSize 을 사용하시면 됩니다.

## 1 - 2. No Offset 쿼리로 변경하기
No offset의 원리를 살펴보면 맨 처음 요청으로 제일 최신 데이터 10개를 달라고한다 ->  
(id = 10000 ~ 9991 데이터 반환) order by id desc limit 0, 10 이라고 쿼리를 날립니다.  
그리고 다시 요청할때 id < 9991 데이터를 달라고 합니다. -> where id < 9991 order by id desc limit 0, 10
```java
public List<MemberDto> paginationNoOffsetBuilder(Long memberId, String name, int pageSize) {
        
    // 1. id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리이다.
    BooleanBuilder dynamicId = new BooleanBuilder();
    
    if (memberId != null) {
        dynamicId.and(member.id.lt(memberId));
    }
    return jpaQueryFactory
            .select(Projections.fields(MemberDto.class,
                    member.id,
                    member.name,
                    member.age
            ))
            .from(member)
            .where(dynamicId, // 동적 쿼리
                    member.name.like(name + "%")) // like 는 뒤에 % 를 붙여야 인덱스가 작동함.
            .orderBy(member.id.desc())
            .limit(pageSize) // 지정된 사이즈 만큼
            .fetch();
}
```

첫번째 페이지에서는 memberId가 없기 때문에 동적쿼리가 들어가야합니다. 그래서 BooleanBuilder를 사용했습니다.  
이제 리팩토링을 통해 가독성 좋게 만들면 다음과 같이 됩니다.
```java
public List<MemberDto> paginationNoOffset(Long memberId, String name, int pageSize) {

    // 1. id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리이다.
    BooleanBuilder dynamicId = new BooleanBuilder();


    return jpaQueryFactory
            .select(Projections.fields(MemberDto.class,
                    member.id,
                    member.name,
                    member.age
            ))
            .from(member)
            .where(
                    ltMemberId(memberId), // 동적 쿼리
                    member.name.like(name + "%") // like 는 뒤에 % 를 붙여야 인덱스가 작동함.
            )
            .orderBy(member.id.desc())
            .limit(pageSize) // 지정된 사이즈 만큼
            .fetch();
}

private BooleanExpression ltMemberId(Long memberId) {
    if (memberId == null) {
        return null;    // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
    }
    return member.id.lt(memberId);
}
```

# 코드 검증
기존 페이징
```java
@Test
    void paginationLegacy() {
        // given : 선행조건 기술
        String prefixName = "a";
        for (int i = 1; i <= 30; i++) {
            memberRepository.save(Member.builder()
                    .name(prefixName + i)
                    .build());
        }

        // when : 기능 수행
        List<MemberDto> members = memberRepositoryFactory.paginationLegacy(prefixName, 1, 10);

        // then : 결과 확인
        assertThat(members.size()).isEqualTo(10);
        assertThat(members.get(0).getName()).isEqualTo("a20");
        assertThat(members.get(9).getName()).isEqualTo("a11");
    }
```
No Offset 1번째 페이지
```java
@Test
void paginationNoOffset1() {
    // given : 선행조건 기술
    String prefixName = "a";
    for (int i = 1; i <= 30; i++) {
        memberRepository.save(Member.builder()
                .name(prefixName + i)
                .build());
    }

    // when : 기능 수행
    List<MemberDto> members = memberRepositoryFactory.paginationNoOffsetBuilder(null, prefixName, 10);

    // then : 결과 확인
    assertThat(members.size()).isEqualTo(10);
    assertThat(members.get(0).getName()).isEqualTo("a30");
    assertThat(members.get(9).getName()).isEqualTo("a21");
}
```
No Offset 2번째 페이지
```java
@Test
void paginationNoOffset2() {
    // given : 선행조건 기술
    String prefixName = "a";
    for (int i = 1; i <= 30; i++) {
        memberRepository.save(Member.builder()
                .name(prefixName + i)
                .build());
    }

    // when : 기능 수행
    List<MemberDto> members = memberRepositoryFactory.paginationNoOffset(21L, prefixName, 10);

    // then : 결과 확인
    assertThat(members.size()).isEqualTo(10);
    assertThat(members.get(0).getName()).isEqualTo("a20");
    assertThat(members.get(9).getName()).isEqualTo("a11");
}
```

# 1 - 3 성능 비교

테스트 DB 
- Docker mysql

테스트 테이블
 - 10000  row
 - 1개 컬럼

기존 페이징
``` sql
select
    m1_0.member_id,
    m1_0.name 
from
    member m1_0 
where
    m1_0.name like 'a%' 
order by
    m1_0.member_id desc limit 999990, 10
쿼리 측정 시간 = 4.93384175
```

No Offset

``` sql
select
    m1_0.member_id,
    m1_0.name 
from
    member m1_0 
where
    m1_0.member_id < 11 
    and m1_0.name like 'a%' 
order by
    m1_0.member_id desc limit 10
쿼리 측정 시간 = 0.193790958
```

같은 member 테이블에서 같은 결과 같을 가지고 오는 조회를 했을때 성능이 25배 정도 차이가 납니다.  


# 
No Offset 방식으로 개선할 수 있다면 정말 좋겠지만, NoOffset 페이징을 사용할 수 없는 상황이라면 커버링 인덱스로 성능을 개선할 수 있습니다.  
커버링 인덱스란 쿼리를 충족시키는 데 필요한 모든 데이터를 갖고 있는 인덱스를 이야기합니다.

즉, select, where, order by, limit, group by 등에서 사용되는 모든 컬럼이 Index 컬럼안에 다 포함된 경우인데요.

여기서 하나의 의문이 드는 것은 select절까지 포함하게 되면 너무 많은 컬럼이 인덱스에 포함되지 않겠냐는 것인데요.
그래서 실제로 커버링 인덱스를 태우는 부분은 select를 제외한 나머지만 우선으로 수행합니다.   

