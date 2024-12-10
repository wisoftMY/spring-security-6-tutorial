package com.study.spring_security.repo;


import com.study.spring_security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u JOIN FETCH u.userRoles ur JOIN FETCH ur.role WHERE u.username = :username")
    Users findByUsernameWithRoles(@Param("username") String username);
}
