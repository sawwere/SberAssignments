package com.sawwere.sber.homework6.socnet.gift;

import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {
    private final UserService userService;
    private final List<Gift> gifts = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean grantGift(Long senderId, Gift gift) {
        User sender = userService.findById(senderId);
        User receiver = userService.findById(gift.getOwnerId());
        // Здесь логика по отправке подарков
        return !sender.equals(receiver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Gift> findGiftsByOwner(Long userId) {
        return gifts.stream().filter(x -> x.getOwnerId().equals(userId)).toList();
    }
}
