package Projects.DoItNow_BackEnd_App.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.exceptions.UnauthorizedException;
import Projects.DoItNow_BackEnd_App.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	JWTTools jwtTools;

	@Autowired
	UserService userService;

	// * * * * * * * * * * doFilterInternal * * * * * * * * * *
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Please pass the token in the authorization header.");

		String token = authHeader.substring(7);
		System.out.println("Token ---> " + token);

		jwtTools.verifyToken(token);

		String id = jwtTools.extractSubject(token);
		User currentUser = userService.findUserById(UUID.fromString(id));

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(currentUser, null,
				currentUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}

	// * * * * * * * * * * shouldNotFilter * * * * * * * * * *
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		System.out.println(request.getServletPath());
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}
