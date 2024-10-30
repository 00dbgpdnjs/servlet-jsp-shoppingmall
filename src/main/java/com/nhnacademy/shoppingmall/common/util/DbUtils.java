package com.nhnacademy.shoppingmall.common.util;


import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo#1-1 {ip},{database},{username},{password} 설정
        basicDataSource.setUrl("jdbc:mysql://133.186.241.167:3306/nhn_academy_54");
        basicDataSource.setUsername("nhn_academy_54");
        basicDataSource.setPassword("[El3ou_R3Mu_8f1V");

        //todo#1-2 initialSize, maxTotal, maxIdle, minIdle 은 모두 5로 설정합니다.
            // 실제 서비스에서는 각 파라미터를 톰캣 스레드 풀 사이즈 만큼
            // -> 각 요청 마다 디비 커넥션
        basicDataSource.setInitialSize(20);
        basicDataSource.setMaxTotal(20); // 10이면 10개까지 만들 수 있음
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMinIdle(20);

        //todo#1-3 Validation Query를 설정하세요
        // 데이터베이스가 응답하는지 확인
            // setTestOnBorrow true 이어야함
        basicDataSource.setValidationQuery("select 1");
        // BasicDataSource에서 데이터베이스 커넥션을 가져올 때 연결 유효성을 검사하도록
        basicDataSource.setTestOnBorrow(true);

        basicDataSource.setMaxWait(Duration.ofSeconds(2));

        //todo#1-4 적절히 변경하세요
        DATASOURCE = basicDataSource;

    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}
