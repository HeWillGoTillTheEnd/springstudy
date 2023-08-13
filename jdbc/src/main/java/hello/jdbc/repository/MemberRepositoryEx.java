package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;
//인터페이스의 예외를 구현하기 위해서는 구현체가 던지는 체크 예외를 인터페이스에서도 던져줘야하는데
//그렇게 될 경우 인터페이스가 해당 예외에 종속이 되는 문제가 발생한다.
public interface MemberRepositoryEx {

    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}

