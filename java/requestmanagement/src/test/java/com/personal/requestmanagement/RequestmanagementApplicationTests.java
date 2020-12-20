package com.personal.requestmanagement;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.personal.requestmanagement.constant.PdfNote;
import com.personal.requestmanagement.model.dto.RequestMaterialDto;
import com.personal.requestmanagement.model.pdf.PdfObject;
import com.personal.requestmanagement.model.pdf.Template;
import com.personal.requestmanagement.utils.DateUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestmanagementApplicationTests {
	
	
	@Test
	public void test() {
		List<RequestMaterialDto> dtos = new ArrayList<>();
		
		Template template = new Template();
		template.setDtos(dtos);
        List<PdfObject> lst = new ArrayList<>();
        lst.add(new PdfObject(PdfNote.OPERATOR.getValue(), "test"));
        lst.add(new PdfObject(PdfNote.MANAGER.getValue(), "test"));
        lst.add(new PdfObject(PdfNote.USERNAME.getValue(), "test"));
        lst.add(new PdfObject(PdfNote.DEPTNAME.getValue(), "test"));
        lst.add(new PdfObject(PdfNote.REASON.getValue(), "test"));
        
        template.setData(lst);
        //Write text to Pdf template
        String strDate = DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_YMDHM);
        String temName = ETemplatePdf.SO_BUY.getName();
        if (type == ERequest.RQ_DELIVER.getValue()) {
            temName = ETemplatePdf.SO_DELIVER.getName();
        }
        String template = String.format(appConfig.getStringCfByKey("url.public.folderTemplate"), temName);
        String filePath = String.format(appConfig.getStringCfByKey("url.public.folder"), sessionService.getUserNameFromRequest(request).toUpperCase(), strDate + "_" + temName);
        //Create pdf file
        boolean copy = FileSignUtil.copyFile(template, filePath);
        if (copy) {
            //write pdf file
            File filePdf = new File(filePath);
            boolean write = false;
            if (type == ERequest.RQ_DELIVER.getValue()) {
                write = pdfService.writeMultipTextToBBMH0102(filePdf, bb);
            } else {
                write = pdfService.writeMultipTextToBBMH0101(filePdf, bb);
            }
            if (write) {
                flash(ConstantUtil.SUCCESS_CODE, ConstantUtil.SUCCESS_MESSAGE);
                return ok(ConstantUtil.SUCCESS_CODE + "/" + String.valueOf(result.getId()));
            }
        }
	}

}
