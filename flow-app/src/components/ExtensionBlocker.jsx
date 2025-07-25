import { useEffect, useState } from "react";
import axios from "axios";
import "./ExtensionBlocker.css";

export default function ExtensionBlocker() {
    // 고정 확장자 목록 (DB에서 받아옴, 체크박스 렌더링용)
    const [fixed, setFixed] = useState([]); // DB에서 받아온 고정 확장자 목록을 저장

    // 커스텀 확장자 목록 (DB에서 받아옴, 태그 렌더링용)
    const [custom, setCustom] = useState([]); // DB에서 받아온 커스텀 확장자 목록을 저장

    // 입력란에 사용자가 입력한 커스텀 확장자 값
    const [customInput, setCustomInput] = useState(""); // 사용자가 입력한 커스텀 확장자 값

    // API 호출 중 로딩 상태 표시
    const [loading, setLoading] = useState(false);

    // 에러 메시지 표시용
    const [error, setError] = useState("");

    // 컴포넌트 마운트 시 고정/커스텀 확장자 목록을 백엔드에서 불러옴
    useEffect(() => {
        fetchData();
    }, []);

    // 고정/커스텀 확장자 목록을 백엔드에서 받아와 상태로 저장
    const fetchData = async () => {
        setLoading(true);
        setError("");
        try {
            // 두 API를 병렬로 호출하여 고정/커스텀 확장자 목록을 모두 받아옴
            const [fixedRes, customRes] = await Promise.all([ // 두 요청을 동시에 보냄으로써 속도 향상 및 효율성 증가
                axios.get("/api/extensions/fixed"),
                axios.get("/api/extensions/custom")
            ]);
            setFixed(Array.isArray(fixedRes.data) ? fixedRes.data : []);
            setCustom(Array.isArray(customRes.data) ? customRes.data : []);
        } catch {
            setError("데이터를 불러오지 못했습니다.");
        }
        setLoading(false);
    };

    // 고정 확장자 체크박스 변경 시 백엔드에 상태 업데이트 요청
    const handleFixedChange = async (name, checked) => {
        try {
            await axios.put(`/api/extensions/fixed/${name}/status`, null, {
                params: { enabled: checked }
            });
            fetchData(); // 변경 후 목록 갱신
        } catch {
            setError("상태 변경 실패");
        }
    };

    // +추가 버튼 클릭 시 커스텀 확장자 추가
    const handleAddCustom = async () => {
        if (!customInput.trim()) return; // 입력란이 비어있으면 추가 안 함
        // 영문 및 숫자만 허용 (한글 및 특수문자 제거)
        if (!/^[a-zA-Z0-9]+$/.test(customInput.trim())) {
            setError("영문 및 숫자만 입력 가능합니다.");
            return;
        }
        // 20자 초과 체크 (최대 20자)
        if (customInput.trim().length > 20) {
            setError("20자 이내로 입력해 주세요.");
            return;
        }
        // 200개 초과 체크 (최대 200개)
        if (custom.length >= 200) {
            setError("최대 200개까지 등록할 수 있습니다.");
            return;
        }
        // 고정 확장자 중복 체크 (대소문자 구분 없이)
        if (fixed.some(f => f.extensionName.toLowerCase() === customInput.trim().toLowerCase())) {
            setError("고정 확장자에 이미 존재합니다.");
            return;
        }
        try {
            await axios.post("/api/extensions/custom", null, {
                params: { extension: customInput.trim() }
            });
            setCustomInput("");
            setError(""); // 성공 시 에러 초기화
            fetchData();
        } catch (e) {
            setError(e.response?.data?.message || "중복된 확장자입니다.");
        }
    };

    // X 버튼 클릭 시 커스텀 확장자 삭제
    const handleDeleteCustom = async (name) => {
        try {
            await axios.delete(`/api/extensions/custom/${name}`);
            fetchData();
        } catch {
            setError("삭제 실패");
        }
    };

    // 엔터키 입력 시 +추가와 동일하게 동작
    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleAddCustom();
        }
    };

    // 커스텀 확장자 입력란에서 띄어쓰기(공백) 입력이 안 되도록 처리
    const handleCustomInputChange = (e) => {
        // 모든 공백(띄어쓰기) 제거
        const value = e.target.value.replace(/\s/g, "");
        setCustomInput(value);
    };

    // 렌더링: 고정 확장자 체크박스, 커스텀 확장자 입력/추가/삭제 UI
    return (
        <div className="file-extension-blocker">
            <div className="top-border"></div>
            <div className="header">
                <div className="header-icon">
                    <div className="header-icon-dot"></div>
                </div>
                <h1 className="header-title">파일 확장자 차단</h1>
            </div>
            <div className="divider"></div>
            {loading && <div className="mb-4 text-blue-500">로딩 중...</div>}
            <div>
                <p className="description">
                    파일확장자에 따라 특정 형식의 파일을 첨부하거나 전송하지 못하도록 제한
                </p>
                <div className="section">
                    <h3 className="section-title">고정 확장자</h3>
                    <div className="checkbox-grid">
                        {fixed.map((f) => (
                            <label key={f.extensionName} className="checkbox-item">
                                <input
                                    type="checkbox"
                                    checked={f.isBlocked === 1}
                                    onChange={e => handleFixedChange(f.extensionName, e.target.checked)}
                                    className="checkbox-input"
                                />
                                <span className="checkbox-label">{f.extensionName}</span>
                            </label>
                        ))}
                    </div>
                </div>
                <div className="section">
                    <h3 className="section-title">커스텀 확장자</h3>
                    <div className="custom-input-container">
                        <input
                            type="text"
                            value={customInput}
                            onChange={handleCustomInputChange}
                            onKeyPress={handleKeyPress}
                            placeholder="확장자 입력"
                            className="custom-input"
                        />
                        <button
                            onClick={handleAddCustom}
                            className="add-button"
                        >
                            +추가
                        </button>
                    </div>
                    {error && <div className="custom-error">{error}</div>}
                </div>
                <div className="extensions-container">
                    <div className="extensions-counter">{custom.length}/200</div>
                    <div className="extensions-list">
                        {custom.map((c) => (
                            <span key={c.extensionName} className="extension-tag">
                                {c.extensionName}
                                <button
                                    onClick={() => handleDeleteCustom(c.extensionName)}
                                    className="extension-remove"
                                >
                                    ×
                                </button>
                            </span>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}