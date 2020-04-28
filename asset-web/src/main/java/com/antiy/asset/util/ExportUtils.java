package com.antiy.asset.util;

import com.antiy.asset.vo.enums.ExportType;
import com.antiy.asset.vo.query.ReportExportRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiaoqianyong
 * @DATE 2020/4/28 14:44
 * @Description
 */
@Component
public class ExportUtils {


    public void export(ReportExportRequest reportExportRequest, OutputStream outputStream) throws Exception{
        ExportType type = reportExportRequest.getType();
        if (type==ExportType.PDF){
            exportPDF(reportExportRequest.getImageCode(),outputStream);
        }else if (type==ExportType.WORD){
            exportWord(reportExportRequest.getImageCode(),outputStream);
        }
    }


     public void exportPDF(String imageCode, OutputStream outputStream)throws Exception{
        Document document = new Document(PageSize.A4);

        PdfWriter writer =PdfWriter.getInstance(document,outputStream);
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        document.open();

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(imageCode);
        for (int i = 0; i < bytes.length; ++i) {
            //调整异常数据
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        Image instance = Image.getInstance(bytes);
        //instance.scaleAbsolute(595.0F, 800.0F);//自定义大小
        instance.scalePercent(45);
        document.add(instance);

        document.close();
        outputStream.close();
    }

    public void exportWord(String imageCode, OutputStream outputStream){
        OutputStreamWriter outputStreamWriter=null;
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDefaultEncoding("utf-8");
            configuration.setClassForTemplateLoading(this.getClass(),"/template");
            Template template = configuration.getTemplate("test.ftl");


            Map<String, Object> data = new HashMap<>(16);
            data.put("imageCode", imageCode);

            //解码成图片流
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] bytes = base64Decoder.decodeBuffer(imageCode);
            BufferedImage read = ImageIO.read(new ByteArrayInputStream(bytes));

            //获取原来宽度高度
            double width = read.getWidth();
            double height = read.getHeight();

            //2.54cm = 25.4 mm = 72pt = 6pc 1cm=28.3465pt  word=29.7 * 28.3465pt
            double wordHeight=841.89 ;

            //width:414.75pt (一般图片就是word宽度）;height:89.25pt
            double setWidth=415.5 ;

            //超过文档宽度
            if (width >setWidth ){
                double ratio = setWidth / width;
                width= width*ratio;
                height=height*ratio;
                //超过文档高度
                if (height>wordHeight){
                    double heightRatio = wordHeight / height;
                    height=wordHeight;
                    width=width*heightRatio;

                }
            }

            data.put("width", width +"pt");
            data.put("height",  height+"pt");

            //写出流
             outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
            template.process(data, outputStreamWriter);

            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (outputStreamWriter!=null){
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
