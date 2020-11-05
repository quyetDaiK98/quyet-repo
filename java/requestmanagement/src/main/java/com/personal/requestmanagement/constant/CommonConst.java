package com.personal.requestmanagement.constant;

import java.util.HashMap;

public class CommonConst {
	public static final int ERROR_FORM = 400;
	
	public static final int SUCCESS_ACTION_CODE = 200;
	
	public static final String SUCCESS_ACTION_MESS = "Thao tác thành công";

	public static final int ERROR_ACTION_CODE = 500;

	public static final String ERROR_ACTION_MESS = "Thao tác thất bại";

	public static HashMap<Integer, String> REQUEST_STATUS;

	public static HashMap<Integer, String> REQUEST_TYPE;

	static {
		REQUEST_STATUS = new HashMap<>();
		REQUEST_STATUS.put(1, "Dự thảo");
		REQUEST_STATUS.put(2, "Đã duyệt");
		REQUEST_STATUS.put(3, "Đã xử lý");
		REQUEST_STATUS.put(4, "Từ chối");
		
		REQUEST_TYPE = new HashMap<>();
		REQUEST_TYPE.put(1, "Đề nghị xin nghỉ phép");
		REQUEST_TYPE.put(2, "Đề nghị mua vật tư");
	}
}
