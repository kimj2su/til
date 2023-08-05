package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();
    @Test
    void save() {
        Member member = new Member("memberA", 10000);
        Member saveMember = repository.save(member);
    }

    @Test
    void findById() {
        Member member = repository.findById("memberA");
        log.info("member = {}", member);
        assertThat(member.getMemberId()).isEqualTo("memberA");
    }

    @Test
    void update() {
        Member member = repository.findById("memberA");
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById("memberA");
        assertThat(updatedMember.getMoney()).isEqualTo(20000);
    }

    @Test
    void delete() {
        Member member = repository.findById("memberA");
        repository.delete(member.getMemberId());
        Member deletedMember = repository.findById("memberA");
        assertThat(deletedMember).isNull();
    }
}