package dailynailheroku.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import dailynailheroku.services.impl.DailyNailUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DailyNailUserService dailyNailUserService;
    private final PasswordEncoder passwordEncoder;
    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(DailyNailUserService dailyNailUserService, PasswordEncoder passwordEncoder, AccessDeniedHandler accessDeniedHandler) {
        this.dailyNailUserService = dailyNailUserService;
        this.passwordEncoder = passwordEncoder;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // allow access to static resources to anyone
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/img/**", "/fonts/**").permitAll()
                // allow access to index, user login and reg to anyone
                .antMatchers("/", "/users/terms-and-conditions", "/users/login", "/users/register", "/articles/a/**", "/articles/categories/**",
                        "/access-denied", "/403", "/404", "/error", "/maintenance").permitAll()
                .antMatchers("/articles/create/**", "/articles/edit/**", "/articles/all", "/jokes/create", "/comments/delete/**").hasAnyRole("ADMIN", "EDITOR", "REPORTER")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                // protect all other pages
                .antMatchers("/**").authenticated()
//                .anyRequest().authenticated()
                .and()
//                .exceptionHandling().accessDeniedPage("/403")
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                // configure login with HTML form
                .formLogin()
                // our login page will be served by the controller with mapping /users/login
                .loginPage("/users/login")
                // the name of the username input field in OUR login form is username (optional)
                .usernameParameter("email") // "username"
                // the name of the password input field in OUR login form is password (optional)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                // on login success redirect here
                .defaultSuccessUrl("/")
                // on login failure redirect here
                .failureForwardUrl("/users/login-error")
        .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .key("remember Me Encryption Key")
                .rememberMeCookieName("rememberMeCookieName")
                .tokenValiditySeconds(10000)
        .and()
                // which endpoint performs logout, (should be POST request!!!)
                .logout()
                .logoutUrl("/logout")
                // where to land after logout
                .logoutSuccessUrl("/")
                // remove session from the server
                .invalidateHttpSession(true)
                // delete the session cookie
                .deleteCookies("JSESSIONID");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(dailyNailUserService)
                .passwordEncoder(passwordEncoder);
    }
}
