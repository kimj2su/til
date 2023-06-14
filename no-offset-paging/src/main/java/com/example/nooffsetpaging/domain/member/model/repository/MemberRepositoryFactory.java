package com.example.nooffsetpaging.domain.member.model.repository;

import com.example.nooffsetpaging.domain.member.dto.MemberDto;
import com.example.nooffsetpaging.domain.member.model.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;

import static com.example.nooffsetpaging.domain.member.model.QMember.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepositoryFactory {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MemberDto> paginationLegacy(String name, int pageNo, int pageSize) {
        var stopWatch = new StopWatch();
        stopWatch.start();
        List<MemberDto> members = jpaQueryFactory
                .select(Projections.fields(MemberDto.class,
                        member.id.as("id"),
                        member.name
                ))
                .from(member)
                .where(member.name.like(name + "%")) // like 는 뒤에 % 를 붙여야 인덱스가 작동함.
                .orderBy(member.id.desc())
                .offset((long) pageNo * pageSize) // 지정된 페이지 위치에서
                .limit(pageSize) // 지정된 사이즈 만큼
                .fetch();
        stopWatch.stop();
        System.out.println("쿼리 측정 시간 = " + stopWatch.getTotalTimeSeconds());
        return members;
    }

    public List<MemberDto> paginationNoOffsetBuilder(Long memberId, String name, int pageSize) {

        // 1. id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리이다.
        BooleanBuilder dynamicId = new BooleanBuilder();

        if (memberId != null) {
            dynamicId.and(member.id.lt(memberId));
        }
        return jpaQueryFactory
                .select(Projections.fields(MemberDto.class,
                        member.id.as("id"),
                        member.name
                ))
                .from(member)
                .where(dynamicId, // 동적 쿼리
                        member.name.like(name + "%")) // like 는 뒤에 % 를 붙여야 인덱스가 작동함.
                .orderBy(member.id.desc())
                .limit(pageSize) // 지정된 사이즈 만큼
                .fetch();
    }

    public List<MemberDto> paginationNoOffset(Long memberId, String name, int pageSize) {
        var stopWatch = new StopWatch();
        stopWatch.start();
        List<MemberDto> members = jpaQueryFactory
                .select(Projections.fields(MemberDto.class,
                        member.id.as("id"),
                        member.name
                ))
                .from(member)
                .where(
                        ltMemberId(memberId), // 동적 쿼리
                        member.name.like(name + "%") // like 는 뒤에 % 를 붙여야 인덱스가 작동함.
                )
                .orderBy(member.id.desc())
                .limit(pageSize) // 지정된 사이즈 만큼
                .fetch();
        stopWatch.stop();
        System.out.println("쿼리 측정 시간 = " + stopWatch.getTotalTimeSeconds());
        return members;
    }

    private BooleanExpression ltMemberId(Long memberId) {
        if (memberId == null) {
            return null;    // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return member.id.lt(memberId);
    }
}
