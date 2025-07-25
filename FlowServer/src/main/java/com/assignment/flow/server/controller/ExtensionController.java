package com.assignment.flow.server.controller;

import com.assignment.flow.server.entity.FixedExtension;
import com.assignment.flow.server.entity.CustomExtension;
import com.assignment.flow.server.service.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 확장자 관리 REST API 컨트롤러
 * - 프론트엔드에서 확장자 목록 조회, 상태 변경, 추가/삭제 요청을 처리
 * - CORS 허용(@CrossOrigin)으로 개발 환경에서 프론트와 연동
 */
@RestController
@RequestMapping("/api/extensions")
@CrossOrigin(origins = "*")
public class ExtensionController {
    // 서비스 계층 주입 (비즈니스 로직 위임)
    private final ExtensionService extensionService;

    @Autowired
    public ExtensionController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    /**
     * 고정 확장자 전체 조회 API
     * - GET /api/extensions/fixed
     */
    @GetMapping("/fixed")
    public List<FixedExtension> getFixedExtensions() {
        return extensionService.getAllFixedExtensions();
    }

    /**
     * 커스텀 확장자 전체 조회 API
     * - GET /api/extensions/custom
     */
    @GetMapping("/custom")
    public List<CustomExtension> getCustomExtensions() {
        return extensionService.getAllCustomExtensions();
    }

    /**
     * 고정 확장자 상태 변경 API
     * - PUT /api/extensions/fixed/{extensionName}/status?enabled=true
     */
    @PutMapping("/fixed/{extensionName}/status")
    public void updateFixedStatus(
            @PathVariable("extensionName") String extensionName,
            @RequestParam("enabled") boolean enabled) {
        extensionService.updateFixedExtensionStatus(extensionName, enabled);
    }

    /**
     * 커스텀 확장자 추가 API
     * - POST /api/extensions/custom?extension=
     */
    @PostMapping("/custom")
    public CustomExtension addCustomExtension(@RequestParam("extension") String extension) {
        return extensionService.addCustomExtension(extension);
    }

    /**
     * 커스텀 확장자 삭제 API
     * - DELETE /api/extensions/custom/{extensionName}
     */
    @DeleteMapping("/custom/{extensionName}")
    public void deleteCustomExtension(@PathVariable("extensionName") String extensionName) {
        extensionService.deleteCustomExtension(extensionName);
    }
}