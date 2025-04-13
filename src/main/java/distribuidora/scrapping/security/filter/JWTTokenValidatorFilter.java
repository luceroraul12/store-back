package distribuidora.scrapping.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import distribuidora.scrapping.security.handler.ErrorResponse;
import distribuidora.scrapping.security.service.JwtUtilService;
import distribuidora.scrapping.security.service.ScrappingUserDetails;
import io.jsonwebtoken.JwtException;

@Component
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

	@Autowired
	private ScrappingUserDetails userDetailsService;

	@Autowired
	private JwtUtilService jwtUtilService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		try {
			final String authorizationHeader = request
					.getHeader("Authorization");

			String username = null;
			String jwt = null;

			if (authorizationHeader != null
					&& authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
				username = jwtUtilService.extractUsername(jwt);
			}

			if (username != null && SecurityContextHolder.getContext()
					.getAuthentication() == null) {

				UserDetails userDetails = this.userDetailsService
						.loadUserByUsername(username);

				if (jwtUtilService.validateToken(jwt, userDetails)) {

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken
							.setDetails(new WebAuthenticationDetailsSource()
									.buildDetails(request));
					SecurityContextHolder.getContext()
							.setAuthentication(authenticationToken);
				}
			}

		} catch (JwtException ex) {
			ex.printStackTrace();
		    // Maneja la excepción JwtException
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    ErrorResponse error = new ErrorResponse(ex.getMessage().toString());
		    response.getWriter().write(error.toString());
		    return; // Detiene la ejecución del filtro
		}
		chain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		return request.getRequestURI().equals("/login");
	}
}
