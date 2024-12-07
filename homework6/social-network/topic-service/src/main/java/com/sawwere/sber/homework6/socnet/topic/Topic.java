package com.sawwere.sber.homework6.socnet.topic;

import com.sawwere.sber.homework6.socnet.user.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private Long id;
    private Long creatorId;
    private String name;
    private String text;
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();
}
