package com.sawwere.sber.homework6.socnet.gift;

import com.sawwere.sber.homework6.socnet.user.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gift {
    private Long id;
    private String name;
    private Long ownerId;
}
