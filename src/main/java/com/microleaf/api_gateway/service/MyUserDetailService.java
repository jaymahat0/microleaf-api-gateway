package com.microleaf.api_gateway.service;

import com.microleaf.api_gateway.config.UserPrincipal;
import com.microleaf.api_gateway.feignConnect.UserInterServiceCommunication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class MyUserDetailService
        implements ReactiveUserDetailsService {

    private final UserInterServiceCommunication
            userInterServiceCommunication;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return Mono.fromCallable(() ->
                        userInterServiceCommunication
                                .getUserByUsername(username)
                )
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(
                        Mono.error(
                                new UsernameNotFoundException(
                                        "User not found: " + username
                                )
                        )
                )
                .map(UserPrincipal::new);
    }
}