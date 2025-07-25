package com.assignment.flow.server.entity;

import jakarta.persistence.*;

/**
 * 고정 확장자(FIXED 테이블) 엔티티
 * - 파일 확장자 차단 정책에서 고정적으로 관리되는 확장자 정보를 저장
 * - 확장자명(PK)과 차단 여부(0: 해제, 1: 차단)로 구성
 */
@Entity
@Table(name = "FIXED")
public class FixedExtension {

    // 확장자명 (PK) -> bat, cmd, com, cpl, exe, scr, js
    @Id
    @Column(name = "extension_name", length = 20)
    private String extensionName;

    // 차단 여부 (0: 해제, 1: 차단)
    // -> 체크박스 상태와 연동
    @Column(name = "is_blocked", nullable = false)
    private int isBlocked = 0; // 0: 해제

    // JPA 기본 생성자 (필수)
    public FixedExtension() {
    }

    // 확장자명과 차단여부를 지정하는 생성자
    public FixedExtension(String extensionName, int isBlocked) {
        this.extensionName = extensionName;
        this.isBlocked = isBlocked;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }
}