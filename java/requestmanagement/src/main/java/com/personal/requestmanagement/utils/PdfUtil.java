package com.personal.requestmanagement.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

import com.personal.requestmanagement.constant.PdfNote;
import com.personal.requestmanagement.model.pdf.Template;

public class PdfUtil {
	
	public static boolean fillTemplate(File file, Template data)throws IOException{
        PDDocument doc = null;
        boolean writed = false;
        try {
            doc = PDDocument.load(file);
            PDPageTree pageTree = doc.getPages();
            if (pageTree != null && pageTree.getCount() > 0) {
                for (PDPage page : pageTree) {
                    List<PDAnnotation> annotationsInPage = page.getAnnotations();
                    List<PDAnnotation> annotationsUpdate = null;
                    if (annotationsInPage != null && annotationsInPage.size() > 0) {
                        annotationsUpdate = annotationsInPage;
                        for(int i = 0; i < annotationsInPage.size(); i ++) {
                            PDAnnotation pDAnnotation = annotationsInPage.get(i);
                            if (pDAnnotation.getSubtype().equalsIgnoreCase("Text")) {
                                String content = pDAnnotation.getContents();
                                if(content != null && data.getData() != null){
                                    for(int j=0; j<data.getData().size(); j++){
                                        String vitri = data.getData().get(j).getName();
                                        if(content.trim().equalsIgnoreCase(vitri.trim())){
                                            PDRectangle pDRectangle = pDAnnotation.getRectangle();
                                            PDFont pdfFont = PDType0Font.load(doc, new File("C:\\Users\\ADM\\Desktop\\Git\\quyet-repo\\java\\requestmanagement\\src\\main\\resources\\static\\times.ttf"));
                                            int fontSize = 12;
                                            PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,true,true);
                                            //Write text in the PDF document
                                            contents.setFont(pdfFont, fontSize);
                                            contents.beginText();
                                            contents.setLeading(fontSize);
                                            contents.newLineAtOffset(pDRectangle.getLowerLeftX(),pDRectangle.getLowerLeftY() + 10);
                                            String value = data.getData().get(j).getValue();
                                            if(value == null) {
                                                value = "";
                                            }else if(value.split(PdfNote.NEWLINE.getValue()).length > 0){
                                                for(int s=0; s < value.split(PdfNote.NEWLINE.getValue()).length; s++){
                                                    String vl = value.split(PdfNote.NEWLINE.getValue())[s];
                                                    if(s == 0){
                                                        contents.showText(vl);
                                                    }else{
                                                        contents.newLine();
                                                        contents.showText(vl);
                                                    }
                                                }
                                            }else {
                                                contents.showText(value);
                                            }
                                            contents.endText();
                                            contents.close();
                                            //update list annotations
                                            annotationsUpdate.remove(pDAnnotation);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    page.setAnnotations(annotationsUpdate);
                    doc.save(file);
                    writed = true;
                }
            }
        } catch (Exception ex) {
            writed = false;
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
        return writed;
    }
}
