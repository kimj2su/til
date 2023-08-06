# 커넥션 풀 이해
1. 애플리케이션 로직은 DB 드라이버를 통해 커넥션을 조회한다.
2. DB드라이버는 DB와 TCP/IP 커넥션을 연결한다. 물론 이 과정에서  3 way handshake 가 일어난다.
3. DB 드라이버는  커넥션이 연결되면 ID/PW와 기타 부가정보를 DB에 전달한다.
4. DB는 ID.PW를 통해 내부 인증을 완료하고, 내부에 DB 세션을 생성한다.
5. DB는 커넥션 생성이 완료되었다는 응답을 보낸다.
6. DB 드라이브는 커넥션 객체를 생성해서 클라이언트에 반환한다.

이렇게 커넥션을 새로 만드는 것은 과정도 복잡하고 시간도 많이 소모되는 일이므로 커넥션 풀을 만들어서 관리한다.

스프링 부트 2.x.x 버전부터는 HicaariCP를 기본 커넥션 풀로 사용한다.

```java
@Test
void driverManager() throws SQLException {
    Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    Connection connection2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    log.info("connection1 = {}, class={}", connection1, connection1.getClass());
    log.info("connection2 = {}, class={}", connection2, connection2.getClass());
}

@Test
void dateSourceDriverManager() throws SQLException {
    // DriverManagerDataSource - 항상 새로운 커넥션응 획득
    DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    useDataSource(dataSource);
}

private void useDataSource(DataSource dataSource) throws SQLException {
    Connection connection1 = dataSource.getConnection();
    Connection connection2 = dataSource.getConnection();
    log.info("connection1 = {}, class={}", connection1, connection1.getClass());
    log.info("connection2 = {}, class={}", connection2, connection2.getClass());
}
```
DriverManager와 DriverManagerDataSource 차이점은 커넥션을 가져올때 파라미터를 넘기느냐와 안넘기느냐의 차이이다.  
이렇게 되면 설정과 사용의 분리가 일어난다. 설정에 관한 코드를 한곳에서 관리하는게 변경에 유연하게 대처할 수 있다.

## 커넥션 풀 사용
```java
@Test
void dataSourceConnectionPool() throws SQLException {
    // 커넥션 풀링
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);
    dataSource.setMaximumPoolSize(10);
    dataSource.setPoolName("springHikariCP");

    useDataSource(dataSource);
}
```
커넥션풀은 별도의 쓰레드를 사용한다. 커넥션을 하는 비용이 비싸기 때문에 애플리케이션에 부담을 덜 주기 위함.