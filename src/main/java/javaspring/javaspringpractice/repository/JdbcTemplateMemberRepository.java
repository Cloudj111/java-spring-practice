package javaspring.javaspringpractice.repository;

import javaspring.javaspringpractice.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    // JdbcTemplate 객체 생성
    private final JdbcTemplate jdbcTemplate;

    // 생성자 하나인 경우 @Autowired 생략 가능
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // DB insert 위치 지정. JdbcInsert 용 객체 생성 및 withTableName으로 테이블 지정, GeneratedKeyColumn에 id 라는 컬럼 사용
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        // parametes라는 HashMap 객체에, colomn명과 colomn에 대응하는 Object 값 삽입
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name",member.getName());
        // 여기까지 insert 쿼리 문 작성 부분

        // key를 받아와 Id에 넣고 member 값을 반환.
        // 여기서 key 값은 현재 위치한 key 값을 의미함. 자동으로 증가하는 값이기 때문에,
        // 현재 값이 7이라면 8로 자동으로 저장되어야 하므로 현재 key 값을 받아와야 한다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    // sql 및 sql 실행하여 작업한 데이터를 가져오는 mapper
    // 결과 값 result에서 값을 반환.
    // 받은 id 값을 query '?'에 넣어 일치하는 값을 조회
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id  = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    // 받은 name 값을 query '?'에 넣어 일치하는 값을 조회
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name  = ?", memberRowMapper(), name);
        return result.stream().findAny();
     }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // 기본 코드 작성
    // DB의 <Member> 테이블에서 Row 찾기
    // 가져온 데이터 저장할 Member 객체 생성
    // 가져온 데이터(rs)에서 id와 name을 Member 객체에 저장
    // 데이터 저장한 Member 객체 돌려보냄
  /*  private RowMapper<Member> memberRowMapper(){
        return new RowMapper<Member>() {
            
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        };
    }*/

    // Lambda 식 코드 작성
    // alt + enter -> replace Lambda
    private RowMapper<Member> memberRowMapper(){
        // 가져온 데이터 저장할 Member 객체 생성
// 가져온 데이터(rs)에서 id와 name을 Member 객체에 저장
// 데이터 저장한 Member 객체 돌려보냄
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
