package com.devjr.taco.cloud.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.devjr.taco.cloud.tools.UtilityConfig;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    public static final String S_PASS_ENCODE = "53cr3t";

    public enum ERole{
        ROLE_USER, ROLE_ADMIN;
    }

    public enum Type{
        INMEMORY, JDBC, LDAP, CUSTOM;
    }

    Type typeSecurity = Type.CUSTOM;

    //For JDBC
    @Autowired
    DataSource dataSource;
    public static final String DEF_USERS_BY_USERNAME_QUERY = "SELECT username, password, enabled FROM users WHERE username = ?";
    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = "SELECT username, authority FROM authorities WHERE username = ?";
    public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY = "SELECT g.id, g.group_name, ga.authority "
            + "FROM groups g, group_members gm, group_authorities ga "
            + "WHERE gm.username = ? AND g.id = ga.group_id AND g.id = gm.group_id";

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder(){
        return new StandardPasswordEncoder(S_PASS_ENCODE);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        switch(this.typeSecurity){
        case INMEMORY:
            auth.inMemoryAuthentication().withUser("jacinto").password("1234").authorities("ROLE_USER").and()
                    .withUser("pepe").password("0000").authorities("ROLE_USER");
            break;
        case JDBC:
            auth.jdbcAuthentication().dataSource(this.dataSource).usersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY)
                    .authoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY).passwordEncoder(this.encoder());
            break;
        case LDAP:
            auth.ldapAuthentication().userSearchBase("ou=people").userSearchFilter("(uid={0})")
                    .groupSearchBase("ou=groups").groupSearchFilter("member={0}").passwordCompare()
                    //by defect with attribute 'userPassword'
                    .passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("passcode");
            //by defect listening localhost:33389
            /*  PETA » bichear el acceso a un servidor LDAP remoto
             * .contextSource().root("dc=tacocloud,dc=com") //replace root() by url("ldap://tacocloud.com:389/dc=tacocloud,dc=com");
            .ldif("classpath:users.ldif");*/
            break;
        case CUSTOM:
            auth.userDetailsService(this.userDetailsService).passwordEncoder(this.encoder());
            break;
        default:
            System.out.println("TypeSecurity not supported.");
            break;
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers(UtilityConfig.S_PATH_DESIGN, UtilityConfig.S_PATH_ORDERS, UtilityConfig.S_PATH_ORDER,
                        UtilityConfig.S_PATH_LOGOUT)
                .access("hasRole('ROLE_USER')").antMatchers("/", "/**").access("permitAll")
                //Login
                .and().formLogin().loginPage(UtilityConfig.S_PATH_LOGIN).failureUrl("/login?error=true")
                .defaultSuccessUrl(UtilityConfig.S_PATH_HOME, true)
                //Logout
                .and().logout().logoutSuccessUrl(UtilityConfig.S_PATH_HOME)
                //CSRF with Token
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                //Pages to be loaded in frame from the same origin
                .and().headers().frameOptions().sameOrigin();
    }

}