package com.assignment.flow.server.service;

import com.assignment.flow.server.entity.FixedExtension;
import com.assignment.flow.server.entity.CustomExtension;
import com.assignment.flow.server.repository.FixedExtensionRepository;
import com.assignment.flow.server.repository.CustomExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 확장자 관리 비즈니스 로직 서비스
 * - 고정/커스텀 확장자 목록 조회, 상태 변경, 추가/삭제, 유효성 검사 등 담당
 */
@Service
public class ExtensionService {

    // 고정 확장자 JPA 리포지토리 (DB 연동)
    private final FixedExtensionRepository fixedRepo;
    // 커스텀 확장자 JPA 리포지토리 (DB 연동)
    private final CustomExtensionRepository customRepo;

    @Autowired
    public ExtensionService(FixedExtensionRepository fixedRepo, CustomExtensionRepository customRepo) {
        this.fixedRepo = fixedRepo;
        this.customRepo = customRepo;
    }

    /**
     * 고정 확장자 전체 목록 조회
     * - 프론트에서 체크박스 렌더링용
     */
    public List<FixedExtension> getAllFixedExtensions() {
        return fixedRepo.findAll();
    }

    /**
     * 커스텀 확장자 전체 목록 조회
     * - 프론트에서 태그 렌더링용
     */
    public List<CustomExtension> getAllCustomExtensions() {
        return customRepo.findAll();
    }

    /**
     * 고정 확장자 차단/해제 상태 변경
     * - 체크박스 on/off와 연동
     */
    @Transactional
    public void updateFixedExtensionStatus(String extensionName, boolean enabled) {
        FixedExtension ext = fixedRepo.findById(extensionName).orElseThrow();
        ext.setIsBlocked(enabled ? 1 : 0); // 1: 차단, 0: 해제
        fixedRepo.save(ext);
    }

    /**
     * 커스텀 확장자 추가 (유효성 검사 포함)
     * - 1~20자, 200개 제한, 대소문자 구분 없이 중복 방지, 고정 확장자와 중복 방지
     * - 입력된 확장자는 모두 소문자로 변환하여 DB에 저장
     */
    @Transactional
    public CustomExtension addCustomExtension(String extensionName) {
        if (extensionName == null || extensionName.trim().isEmpty() || extensionName.length() > 20) {
            throw new IllegalArgumentException("확장자는 1~20자 이내여야 합니다.");
        }
        if (customRepo.count() >= 200) {
            throw new IllegalStateException("최대 200개의 커스텀 확장자만 등록할 수 있습니다.");
        }
        String lower = extensionName.trim().toLowerCase();
        boolean exists = customRepo.findAll().stream()
                .anyMatch(ext -> ext.getExtensionName().equalsIgnoreCase(lower));
        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 확장자입니다.");
            // 확장자는 입력받을 때 모두 소문자로 변환하여 처리
            // -> 악의적으로 확장자를 대소문자 섞어서 파일을 업로드하여
            // -> 접근 및 방해할 수 있기에 파일을 받아올 때 소문자로 모두 변환하여 받아오게 함
            // 따라서 커스텀 확장자는 대소문자 구분 없이 소문자로만 추가
            // 고정 확장자와의 중복 비교도 모두 소문자로 통일해서 진행
        }
        CustomExtension ext = new CustomExtension(extensionName);
        return customRepo.save(ext);
    }

    /**
     * 커스텀 확장자 삭제 (X 버튼 클릭 시)
     */
    @Transactional
    public void deleteCustomExtension(String extensionName) {
        customRepo.deleteById(extensionName);
    }
}