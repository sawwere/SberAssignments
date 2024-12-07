package com.sawwere.sber.homework6.socnet.topic;

import com.sawwere.sber.homework6.socnet.user.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private Long id;
    private Long authorId;
    private Topic topic;
    private String text;
}
