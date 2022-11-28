package com.example.appchat.repository;

import com.example.appchat.entity.Chat;
import com.example.appchat.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findAllByFirstSideUsernameContainsIgnoreCaseOrSecondSideUsernameContainsIgnoreCaseOrderByUpdatedAt(String firstSide_username, String secondSide_username, Pageable pageable);

    //    @Query(value = "",nativeQuery = true)
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"firstSide", "secondSide"})
    List<Chat> findAllByFirstSideIdOrSecondSideId(Long firstSide_id, Long secondSide_id);

    Optional<Chat> findFirstByIdOrFirstSide_UsernameAndSecondSide_UsernameOrSecondSide_UsernameAndFirstSide_Username(Long id, String firstSide_username, String secondSide_username, String secondSide_username2, String firstSide_username2);

}
