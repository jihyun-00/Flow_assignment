package com.assignment.flow.server.repository;

import com.assignment.flow.server.entity.FixedExtension;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 고정 확장자(FIXED 테이블) JPA 리포지토리
 * - 기본 CRUD 및 findAll 등 제공
 */
public interface FixedExtensionRepository extends JpaRepository<FixedExtension, String> {
}