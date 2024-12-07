package com.sawwere.sber.homewrok6.socnet.team;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private Long id;
    private String name;
    private Long creatorId;
    private List<Long> members;
}
