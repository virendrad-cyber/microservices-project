package com.microservices.apigateway.filter;


import com.microservices.apigateway.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        System.out.println("PATH: " + path);

        // Allow only auth APIs
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("AUTH HEADER: " + authHeader);

        //  BLOCK if no token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing Authorization Header");
        }

        String token = authHeader.substring(7);

        boolean isValid = jwtUtil.validateToken(token);
        System.out.println("TOKEN VALID: " + isValid);

        //  BLOCK if invalid token
        if (!isValid) {
            return unauthorized(exchange, "Invalid or Expired Token");
        }

        String username = jwtUtil.extractUsername(token);

        //  Forward user info
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(r -> r.header("X-User-Name", username))
                .build();

        return chain.filter(mutatedExchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        byte[] bytes = message.getBytes();

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(bytes)));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}