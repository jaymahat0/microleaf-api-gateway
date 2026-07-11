package com.microleaf.api_gateway.feignConnect;

import com.microleaf.api_gateway.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserInterServiceCommunication {

    @GetMapping("/user/{username}")
    User getUserByUsername(
            @PathVariable("username") String username
    );
}
