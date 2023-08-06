package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.PASSWORD;
import static hello.jdbc.connection.ConnectionConst.URL;
import static hello.jdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("springHikariCP");

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void save() throws SQLException {
        Member member = new Member("memberA", 10000);
        Member saveMember = repository.save(member);
    }

    @Test
    void findById() throws SQLException {
        Member member = repository.findById("memberA");
        log.info("member = {}", member);
        assertThat(member.getMemberId()).isEqualTo("memberA");
    }

    @Test
    void update() throws SQLException {
        Member member = repository.findById("memberA");
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById("memberA");
        assertThat(updatedMember.getMoney()).isEqualTo(20000);
    }

    @Test
    void delete() throws SQLException {
        Member member = repository.findById("memberA");
        repository.delete(member.getMemberId());
        Member deletedMember = repository.findById("memberA");
        assertThat(deletedMember).isNull();
    }
}