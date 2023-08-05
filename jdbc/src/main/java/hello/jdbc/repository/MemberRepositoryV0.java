package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;

        connection = getConnection();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("error", e);
            throw new RuntimeException(e);
        } finally {
            close(connection, pstmt, null);
        }
    }

    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        connection = getConnection();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Member(rs.getString("member_id"), rs.getInt("money"));
            }
            return null;
        } catch (SQLException e) {
            log.error("error", e);
            throw new RuntimeException(e);
        } finally {
            close(connection, pstmt, rs);
        }
    }

    public void update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;

        connection = getConnection();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            log.error("error", e);
            throw new RuntimeException(e);
        } finally {
            close(connection, pstmt, null);
        }
    }

    public void delete(String memberId) {
        String sql = "delete from member where member_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;

        connection = getConnection();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            log.error("error", e);
            throw new RuntimeException(e);
        } finally {
            close(connection, pstmt, null);
        }
    }

    private void close(Connection connection, Statement stmt, ResultSet rs)  {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("error", e);
                throw new RuntimeException(e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.error("error", e);
                throw new RuntimeException(e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("error", e);
                throw new RuntimeException(e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
