package me.nano.springsecurityjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.nano.springsecurityjwt.SpringApplicationContext;
import me.nano.springsecurityjwt.dto.UserDto;
import me.nano.springsecurityjwt.requests.UserLoginRequest;
import me.nano.springsecurityjwt.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /* attemptAuthentication exist in UsernamePasswordAuthenticationFilter */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            /* UserLoginRequest contain email & password (class in request) */

            /* compare our request with email and passrd also our user (email & pwd) */
            UserLoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* this function execute if we find the user with email and pwd */

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        User user = ((User) auth.getPrincipal());

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        ArrayList<String> authsList = new ArrayList<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            authsList.add(authority.getAuthority());
        }

        String userName = user.getUsername();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

        UserDto userDto = userService.getUser(userName);

        /* build token */
        String token = Jwts.builder()
                .setSubject(userName)
                .claim("id", userDto.getId())
                .claim("Role", auth.getAuthorities().stream().map(u->u.getAuthority()).collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)) /* payload infos */
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET ) /* token generated by the app */
                .compact();



        /* context is a mecanism give us the possiblity to retrieve in any place in the app as a "bean" */
        /* we should declare the context in the / of the project */

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("user_id", userDto.getUserId());
        res.getWriter().write("{\"token\": \""+ token + "\", \"id\": \""+userDto.getUserId()+"\"}");
    }
}
