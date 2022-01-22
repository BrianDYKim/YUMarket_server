package team.project.yumarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security에 관한 정책을 관리하는 Config class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true) // hasAuthorize 어노테이션을 가진 경우 무조건 권한 검사를 시행함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // api의 주소에 따라서 authenticate를 받아야하는지 여부를 결정해주는 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                .antMatchers("/api").permitAll() // api를 통해서 들어오는 요청들은 filter를 모두 통과시킨다
                .anyRequest().authenticated()) // 그 외의 요청들은 모두 authenticate 검사를 시행한다
                .formLogin(); // TODO 로그인 정책 구현

        http.httpBasic();
    }

    // BCryptEncoder를 Bean으로 등록하여 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}