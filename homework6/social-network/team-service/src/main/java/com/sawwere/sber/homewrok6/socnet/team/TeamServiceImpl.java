package com.sawwere.sber.homewrok6.socnet.team;

import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{
    private final UserService userService;
    private final List<Team> teams = new ArrayList<>();
    private Long currentId = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Team findById(Long teamId) {
        return teams.stream().filter(x -> x.getId().equals(teamId)).findFirst().orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> findAll() {
        return teams.stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> findByUser(Long userId) {
        return teams.stream().filter(x -> x.getMembers().contains(userId)).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team create(Team team) {
        // Тут могли бы быть разные проверки
        team.setId(currentId++);
        teams.add(team);
        return team;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMember(Long teamId, Long userId) {
        User newMember = userService.findById(userId);
        Team team = findById(teamId);
        // Различные проверки и другая логика
        if (team.getMembers().contains(userId)) {
            return false;
        }
        team.getMembers().add(userId);
        return true;
    }
}
