package com.personal.requestmanagement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.personal.requestmanagement.constant.PdfNote;
import com.personal.requestmanagement.model.dto.MaterialDto;
import com.personal.requestmanagement.model.dto.RequestMaterialDto;
import com.personal.requestmanagement.model.entity.Material;
import com.personal.requestmanagement.model.pdf.PdfObject;
import com.personal.requestmanagement.model.pdf.Template;
import com.personal.requestmanagement.utils.DateUtil;
import com.personal.requestmanagement.utils.PdfUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestmanagementApplicationTests {
	
	
	@Test
	public void test() throws IOException {
		List<RequestMaterialDto> dtos = new ArrayList<>();
		
		MaterialDto materialDto = new MaterialDto();
		materialDto.setMatCode("RAM8");
		materialDto.setMatName("Ram 8Gb");
		materialDto.setUnit("Cái");
		
		MaterialDto materialDto1 = new MaterialDto();
		materialDto1.setMatCode("LG24UXK");
		materialDto1.setMatName("Màn hình LG 24");
		materialDto1.setUnit("Cái");
		
		RequestMaterialDto requestMaterialDto1 = new RequestMaterialDto();
		requestMaterialDto1.setMaterial(materialDto);
		requestMaterialDto1.setQuantity(1);
		dtos.add(requestMaterialDto1);
		
		RequestMaterialDto requestMaterialDto2 = new RequestMaterialDto();
		requestMaterialDto2.setMaterial(materialDto1);
		requestMaterialDto2.setQuantity(2);
		dtos.add(requestMaterialDto2);
		
		Template template = new Template();
		template.setDtos(dtos);
        List<PdfObject> lst = new ArrayList<>();
//        lst.add(new PdfObject(PdfNote.OPERATOR.getValue(), "test"));
//        lst.add(new PdfObject(PdfNote.MANAGER.getValue(), "test"));
        lst.add(new PdfObject(PdfNote.CREATEDDATE.getValue(), DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_mm_ss)));
        lst.add(new PdfObject(PdfNote.USERNAME.getValue(), "Nguyễn Văn Quyết"));
        lst.add(new PdfObject(PdfNote.DEPTNAME.getValue(), "Phòng nghiên cứu và phát triển"));
        lst.add(new PdfObject(PdfNote.REASON.getValue(), "Test"));
        
        template.setData(lst);
        //Write text to Pdf template
        String strDate = DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_YMDHM);
        String templateFile = "C:\\Users\\quyet.nguyenvan\\eclipse-workspace\\quyet-repo\\java\\requestmanagement\\src\\main\\resources\\static\\Template.pdf";
        String filePath = "C:\\Users\\quyet.nguyenvan\\eclipse-workspace\\quyet-repo\\java\\requestmanagement\\src\\main\\resources\\static\\document";
        //Create pdf file
//        boolean copy = PdfUtil.copyFile(templateFile, filePath);
//        if (copy) {
//            //write pdf file
//            File filePdf = new File(filePath);
//            PdfUtil.fillTemplate(filePdf, template);
//        }
        File filePdf = new File(templateFile);
        PdfUtil.fillTemplate(filePdf, template);
        
        PdfUtil.signPdf(filePdf, "operator", new File("C:\\Users\\quyet.nguyenvan\\eclipse-workspace\\quyet-repo\\java\\requestmanagement\\src\\main\\resources\\static\\dist\\sign\\Thuy.png"));
        PdfUtil.signPdf(filePdf, "manager", new File("C:\\Users\\quyet.nguyenvan\\eclipse-workspace\\quyet-repo\\java\\requestmanagement\\src\\main\\resources\\static\\dist\\sign\\Phi.png"));
	}

}
