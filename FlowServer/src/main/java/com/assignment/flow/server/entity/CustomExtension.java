package com.assignment.flow.server.entity;

import jakarta.persistence.*;

/**
 * 커스텀 확장자(CUSTOM 테이블) 엔티티
 * - 사용자가 직접 추가/삭제하는 확장자 정보를 저장
 * - 확장자명(PK)만 관리
 */
@Entity
@Table(name = "CUSTOM")
public class CustomExtension {

    // 확장자명 (PK)
    @Id
    @Column(name = "extension_name", length = 20)
    private String extensionName;

    // JPA 기본 생성자 (필수)
    public CustomExtension() {
    }

    // 확장자명을 지정하는 생성자
    public CustomExtension(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }
}