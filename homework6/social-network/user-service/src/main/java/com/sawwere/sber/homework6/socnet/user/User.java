package com.sawwere.sber.homework6.socnet.user;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    @Builder.Default
    private List<User> subscriptions = new ArrayList<>();
}
