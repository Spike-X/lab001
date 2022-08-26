package com.aircraft.codelab.labcore.poi;

import com.aircraft.codelab.labcore.Lab001ApplicationTests;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import java.util.HashMap;

/**
 * 2022-08-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class poiTl {
    @DisplayName("我的第一个测试")
    @Test
    void ascTest() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("D:\\md\\template.docx").render(
                new HashMap<String, Object>() {{
                    // windows 方框
//                    put("title1", (char) 9633);
                    put("title1", new TextRenderData("\u2610"));
                    // windows 方框勾
//                    put("title2", (char) 9745);
                    put("title2", new TextRenderData("\u2611"));
//                    put("title2", new TextRenderData("\u0052", new Style("Wingdings 2", 14)));
                    // linux 方框
                    put("title3", "\u2610");
                    // linux 方框勾
                    put("title4", "\u2611");
//                    put("title3", "□");
//                    put("title4", "☑");
                    put("title5", true);
                    put("title6", false);
                }});
        template.writeAndClose(new FileOutputStream("D:\\md\\output.docx"));
        String s = convertDocxToPdf("D:\\md\\output.docx");
    }

    public String convertDocxToPdf(String path) throws Exception {
        log.info("开始Word文件转PDF =====>");
        InputStream inputStream = new FileInputStream(path);
        String pdfName = path.substring(0, path.indexOf("."));
        String pdfPath = pdfName.concat(".pdf");
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return "";
        }
        try {
            long old = System.currentTimeMillis();
            //新建一个pdf文档
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = new Document(inputStream);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            os.flush();
            os.close();
            inputStream.close();
            //转化用时
            log.info("word转换pdf共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            log.error("word转换pdf异常：{}", e.getMessage());
        }
        return pdfPath;
    }

    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Lab001ApplicationTests.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            log.error("getLicense异常：{}", e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        asciiToString(8730);//ASCII转换为字符串
        asciiToString(9633);//ASCII转换为字符串
        asciiToString(9745);//ASCII转换为字符串
        stringToAscii("□");//字符串转换为ASCII码
    }

    public static void asciiToString(int num) {
        System.out.println(num + " -> " + (char) num);
    }

    public static void stringToAscii(String str) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            System.out.println(aChar + " -> " + (int) aChar);
        }
    }
}
