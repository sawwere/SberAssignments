package com.sawwere.sber.homewrok6.socnet.team;

import java.util.List;

/**
 * Сервис для работы с группами
 */
public interface TeamService {
    /**
     * Возвращает группу по ее id
     * @param teamId id группы
     * @return объект-группу
     */
    Team findById(Long teamId);

    /**
     * Возвращает список всех групп
     * @return новый список
     */
    List<Team> findAll();

    /**
     * Возвращает список групп заданного пользователя
     * @param userId id пользователя
     * @return новый список
     */
    List<Team> findByUser(Long userId);

    /**
     * Создает группу
     * @param team группа, которую нужно создать
     * @return созданная сущность
     */
    Team create(Team team);

    /**
     * Добавляет пользователя в заданную группу
     *
     * @param teamId id группы
     * @param userId id пользователя
     * @return true, если пользователь был успешно добавлен
     */
    boolean addMember(Long teamId, Long userId);
}
