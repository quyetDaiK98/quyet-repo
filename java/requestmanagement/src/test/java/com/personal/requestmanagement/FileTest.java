package com.personal.requestmanagement;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.utils.PdfUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileTest {
	
	@Test
	public void test() throws IOException {
		PdfUtil.copyFile(CommonConst.TEMPLATE_PATH, CommonConst.DOC_STORE_PATH + "\\quyetnv\\doc.pdf");
	}
	
}
