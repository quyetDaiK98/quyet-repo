package com.personal.requestmanagement.constant;

import java.util.HashMap;
import java.util.Map;

public enum PdfNote {
	OPERATOR("operator", "operator"),
	MANAGER("manager", "manager"),
	USERNAME("userName", "userName"),
	DEPTNAME("deptName", "deptName"),
	NEWLINE("---", "Sử dụng cho ghi chú"),
	CREATEDDATE("createdDate", "createdDate"),
	REASON("reason", "reason");
    


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
