package com.sawwere.sber.homework6.socnet.user;

public interface UserService {
    User findById(Long id);

    boolean addSubscription(User user, User subscriber);
}
