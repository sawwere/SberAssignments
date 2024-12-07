package com.sawwere.sber.homework6.socnet.gift;

import com.sawwere.sber.homework6.socnet.user.User;

import java.util.List;

/**
 * Сервис для отправки подарков
 */
public interface GiftService {
    /**
     * Отправляет подарок gift от пользователя sender
     * @param senderId id пользователя, который отправил подарок
     * @param gift отправленный подарок
     * @return true, если подарок был доставлен, false - в противном случае
     */
    boolean grantGift(Long senderId, Gift gift);

    /**
     * Возвращает список подарков, принадлежащих пользователю
     * @param userId id пользователя
     * @return новый список
     */
    List<Gift> findGiftsByOwner(Long userId);
}
