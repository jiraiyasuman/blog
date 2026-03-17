package com.blog.blog_login_module.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.blog_login_module.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // ✅ FIXED
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedAttempts = 0 WHERE u.username = :username")
    void resetFailedAttempts(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedAttempts = u.failedAttempts + 1 WHERE u.username = :username")
    void incrementFailedAttempts(@Param("username") String username);

    // ✅ FIXED (important)
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.accountLocked = true WHERE u.username = :username")
    void lockAccount(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLoginIp = :ip WHERE u.username = :username")
    void updateLastLoginIp(@Param("username") String username,
                          @Param("ip") String ip);

    Optional<User> findByUsernameAndAccountLockedFalse(String username);

    @Query("SELECT u.failedAttempts FROM User u WHERE u.username = :username")
    Integer getFailedAttempts(@Param("username") String username);
}