package CarmineGargiulo.Capstone_Project_Back_End.security;

import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.UnauthorizedException;
import CarmineGargiulo.Capstone_Project_Back_End.services.UsersService;
import CarmineGargiulo.Capstone_Project_Back_End.tools.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWT jwt;

    @Autowired
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autHeader = request.getHeader("Authorization");
        if(autHeader == null || !autHeader.startsWith("Bearer ")) throw new UnauthorizedException("Authorization header missing or it's format is invalid");
        String token = autHeader.replace("Bearer ", "");
        jwt.verifyToken(token);
        User logged = usersService.getUserById(Long.parseLong(jwt.extractUserIdFromToken(token)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(logged, null, logged.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
