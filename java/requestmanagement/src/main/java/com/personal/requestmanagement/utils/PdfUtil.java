package com.personal.requestmanagement.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

import com.personal.requestmanagement.constant.CommonConst;
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
                        int fontSize = 12;
                        boolean loaded = false;
                        for(int i = 0; i < annotationsUpdate.size(); i ++) {
                            PDAnnotation pDAnnotation = annotationsInPage.get(i);
                            if (pDAnnotation.getSubtype().equalsIgnoreCase("Text")) {
                                String content = pDAnnotation.getContents();
                                if(content != null){
                                    PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,true,true);
                                    PDFont pdfFont = PDType0Font.load(doc, new File(CommonConst.ROOT_PATH + "\\src\\main\\resources\\static\\times.ttf"));
                                    contents.setFont(pdfFont, fontSize);
                                    contents.beginText();
                                    contents.setLeading(fontSize);
                                    for(int j=0; j<data.getData().size(); j++){
                                        String vitri = data.getData().get(j).getName();
                                        if(content.trim().equalsIgnoreCase(vitri.trim())){
                                            PDRectangle pDRectangle = pDAnnotation.getRectangle();
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
                                            //update list annotations
                                            annotationsUpdate.remove(pDAnnotation);
                                        }else{
                                            loaded = true;
                                        }
                                    }
                                    if(loaded){
                                        PDRectangle pDRectangle = pDAnnotation.getRectangle();
                                        switch (content.trim()){
                                            case "index":
                                                contents.newLineAtOffset(pDRectangle.getLowerLeftX(),pDRectangle.getLowerLeftY() + 10);
                                                for(int j=1;j<=data.getDtos().size();j++){
                                                    if(j==1){ contents.showText(j+""); }
                                                    else {
                                                        contents.newLine();
                                                        contents.showText(j+"");
                                                    }
                                                }
                                                //update list annotations
                                                annotationsUpdate.remove(pDAnnotation);
                                                break;
                                            case "matCode":
                                                contents.newLineAtOffset(pDRectangle.getLowerLeftX(),pDRectangle.getLowerLeftY() + 10);
                                                for(int j=0;j<data.getDtos().size();j++){
                                                    if(j==0){ contents.showText(data.getDtos().get(j).getMaterial().getMatCode()); }
                                                    else {
                                                        contents.newLine();
                                                        contents.showText(data.getDtos().get(j).getMaterial().getMatCode());
                                                    }
                                                }
                                                //update list annotations
                                                annotationsUpdate.remove(pDAnnotation);
                                                break;
                                            case "matName":
                                                contents.newLineAtOffset(pDRectangle.getLowerLeftX(),pDRectangle.getLowerLeftY() + 10);
                                                for(int j=0;j<data.getDtos().size();j++){
                                                    if(j==0){ contents.showText(data.getDtos().get(j).getMaterial().getMatName()); }
                                                    else {
                                                        contents.newLine();
                                                        contents.showText(data.getDtos().get(j).getMaterial().getMatName());
                                                    }
                                                }
                                                //update list annotations
                                                annotationsUpdate.remove(pDAnnotation);
                                                break;
                                            case "unit":
                                                contents.newLineAtOffset(pDRectangle.getLowerLeftX(),pDRectangle.getLowerLeftY() + 10);
                                                for(int j=0;j<data.getDtos().size();j++){
                                                    if(j==0){ contents.showText(data.getDtos().get(j).getMaterial().getUnit()); }
                                                    else{
                                                        contents.newLine();
                                                        contents.showText(data.getDtos().get(j).getMaterial().getUnit());
                                                    }
                                                }
                                                //update list annotations
                                                annotationsUpdate.remove(pDAnnotation);
                                                break;
                                            case "quantity":
                                                contents.newLineAtOffset(pDRectangle.getLowerLeftX(),pDRectangle.getLowerLeftY() + 10 );
                                                for(int j=0;j<data.getDtos().size();j++){
                                                    if(j==0){ contents.showText(String.valueOf(data.getDtos().get(j).getQuantity())); }
                                                    else{
                                                        contents.newLine();
                                                        contents.showText(String.valueOf(data.getDtos().get(j).getQuantity()));
                                                    }
                                                }
                                                //update list annotations
                                                annotationsUpdate.remove(pDAnnotation);
                                                break;
                                        }
                                    }
                                    contents.endText();
                                    contents.close();
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
	
	public static boolean signPdf(File file, String vitri, File signImage) throws IOException {
        PDDocument doc = null;
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
                                if (content != null && content.equalsIgnoreCase(vitri)) {
                                    PDRectangle pDRectangle = pDAnnotation.getRectangle();
                                    float width = pDRectangle.getWidth() + 70; // size of image sign --> review again
                                    float height = pDRectangle.getHeight() + 15; //size of image sign --> review again
                                    int rotation = page.getRotation();
                                    //Creating PDImageXObject object
                                    //File img = new File(signImage);
                                    PDImageXObject pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
                                    //creating the PDPageContentStream object
                                    // PDPageContentStream contents = new PDPageContentStream(doc, page);
                                   // PDPageContentStream contents = new PDPageContentStream(doc, page, true, true);
                                    PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
                                    //Drawing the image in the PDF document
                                    contents.drawImage(pdImage, pDRectangle.getLowerLeftX() - 40, pDRectangle.getLowerLeftY()-8, width, height);
                                    contents.close();
                                    //update list annotations
                                    annotationsUpdate.remove(pDAnnotation);
                                }
                            }
                        }
                    }
                    page.setAnnotations(annotationsUpdate);
                    doc.save(file);
                    return true;
                }
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
        return false;
    }
	
	public static boolean copyFile(String file, String dest) throws IOException{
        InputStream is = null;
        OutputStream os = null;
        boolean write=false;
        try {
            is = new FileInputStream(new File(file));
            File destFile = new File(dest);
            if(!destFile.exists()) {
                File parentFolder = destFile.getParentFile();
                if(!parentFolder.exists())  parentFolder.mkdirs();
                destFile.createNewFile();
            }
            os = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            write=true;
        }catch (Exception ex){
            write = false;
            ex.printStackTrace();
        }finally {
            is.close();
            os.close();
        }
        return write;
    }
}
