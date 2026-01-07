import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})          // ✅ IMPORTANT
            .csrf(csrf -> csrf.disable());

        // keep your existing auth rules as-is:
        // .authorizeHttpRequests(...)

        return http.build();
    }
}
