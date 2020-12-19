package com.personal.requestmanagement.constant;

import java.util.HashMap;
import java.util.Map;

public enum PdfNote {
	DEPT("DepN", "Phòng ban"),
    USER("ProS", "Người trình"),
    GROUP("GroN", "Tổ nhóm"),
    POSITION("PosT", "Chức vụ"),
    DATE_REQUEST("DaRe", "Ngày đề nghi"),
    TYPE_REQUEST("TyRe", "Loại"),
    AMOUNT_ADVANCE_NUM("AmMu", "Số tiền tạm ứng bằng số"),
    AMOUNT_ADVANCE_TEXT("AmTe", "Số tiền tạm ứng bằng chữ"),
    ACTUAL_AMOUNT_NUM("AcMu", "Số tiền thực chi bằng số"),
    ACTUAL_AMOUNT_TEXT("AcTe", "Số tiền thực chi bằng chữ"),
    BALANCE_AMOUNT_NUM("BaMu", "Số tiền chênh lệch bằng số"),
    BALANCE_AMOUNT_TEXT("BaTe", "Số tiền chênh lệch bằng chữ"),
    SUBJECT_TO("SubT", "Nội dung"),
    PROJECTCODE("ProC", "Dự án"),
    BENEFICIARY("BenC", "Trả cho"),
    AT_BANK("Abak", "Tại ngân hàng"),
    ACC_No("AcNo", "Số tài khoản"),
    ADVANCE_NO("AdReN", "Giấy tạm ứng số"),
    NEWLINE("---", "Sử dụng cho ghi chú"),
    REMARKS("RemS", "Ghi chú"),
    BORROWER("UseN", "Người sử dụng vật tư"),
    ORGANIZATION("OrgN", "Đơn vị mượn vật tư"),
    USERNOTE("ProNote", "Người mượn cam kết"),
    DEPTNOTE("DepNote", "Phòng cam kết"),
    COMPNOTE("ComNote", "Tên công ty cam kết"),
    BORNOTE("BorNote", "Người sử dụng cam kết"),
    WAREHOUSESTAFF("WaSt", "Nhân viên kho"),
    WAREHOUSEMAN("WaMa", "Quản lý kho"),
    WAREHOUSECONFIRM("WarCon", "Nhân viên kho xác nhận trả"),
    DATE_POS("date", "Thời gian ký"),
    BORROWERCONFIRM("BorCon", "Người mượn xác nhận trả");


    private final String value;
    private final String name;

    private PdfNote(String value, String name) {
        this.value = value;
        this.name = name;
    }

    private static final Map<String, PdfNote> eMap = new HashMap<String, PdfNote>();

    static {
        for (PdfNote ePdfNote : PdfNote.values()) {
            eMap.put(ePdfNote.getValue(), ePdfNote);
        }
    }

    public static String getNameByValue(String value) {
        PdfNote ePdfNote = eMap.get(value);
        if (ePdfNote != null) {
            return ePdfNote.getName();
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
