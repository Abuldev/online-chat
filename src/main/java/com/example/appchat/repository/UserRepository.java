package com.example.appchat.repository;

import com.example.appchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    @Query(value = "WITH temp AS (SELECT first_side_id AS id\n" +
            "              FROM chat\n" +
            "              WHERE second_side_id = :userId\n" +
            "              UNION\n" +
            "              SELECT second_side_id\n" +
            "              FROM chat\n" +
            "              WHERE first_side_id = :userId)\n" +
            "SELECT u.*\n" +
            "FROM users u\n" +
            "WHERE u.id <> :userId\n" +
            "  AND u.username ~ :username\n" +
            "  AND u.id NOT IN (SELECT id FROM temp)\n" +
            "LIMIT 10", nativeQuery = true)
    List<User> findAllByUsernameForGlobal(@Param("userId") Long userId,
                                          @Param("username") String username);



    @Query(value = "WITH temp AS (SELECT first_side_id AS id\n" +
            "              FROM chat\n" +
            "              WHERE second_side_id = :userId\n" +
            "              UNION\n" +
            "              SELECT second_side_id\n" +
            "              FROM chat\n" +
            "              WHERE first_side_id = :userId)\n" +
            "SELECT u.*\n" +
            "FROM users u\n" +
            "WHERE u.id <> :userId\n" +
            "  AND u.username ~ :username\n" +
            "  AND u.id IN (SELECT id FROM temp)\n" +
            "LIMIT 10", nativeQuery = true)
    List<User> findAllByUsernameForLocal(@Param("userId") Long userId,
                                             @Param("username") String username);
}
