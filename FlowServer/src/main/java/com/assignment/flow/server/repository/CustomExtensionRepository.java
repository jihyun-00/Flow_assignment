package com.assignment.flow.server.repository;

import com.assignment.flow.server.entity.CustomExtension;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 커스텀 확장자(CUSTOM 테이블) JPA 리포지토리
 * - 기본 CRUD 및 findAll, count 등 제공
 */
public interface CustomExtensionRepository extends JpaRepository<CustomExtension, String> {
    long count();
}