package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepositoryV3;

    public void accountTransaction(String fromMemberId, String toMemberId, int money) throws SQLException {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            bizlogin(fromMemberId, toMemberId, money);
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw e;
        }

    }

    private void bizlogin(String fromMemberId, String toMemberId, int money) throws SQLException {
        Member fromMember = memberRepositoryV3.findById(fromMemberId);
        Member toMember = memberRepositoryV3.findById(toMemberId);

        memberRepositoryV3.update(fromMemberId, fromMember.getMoney() - money);
        memberRepositoryV3.update(toMemberId, toMember.getMoney() + money);


    }
}
