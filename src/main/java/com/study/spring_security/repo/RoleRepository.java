package com.study.spring_security.repo;

import com.study.spring_security.model.Role;
import com.study.spring_security.model.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByStatusIn(List<RoleStatus> statuses);
}
