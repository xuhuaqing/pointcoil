package com.pointcoil.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Value;
import org.apache.commons.codec.binary.Base64;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by WuShuang on 2019/11/20.
 */
public class QRCode {


    /**
     *          * 生成包含字符串信息的二维码图片
     *          * @param outputStream 文件输出流路径
     *          * @param content 二维码携带信息
     *          * @param qrCodeSize 二维码图片大小
     *          * @param imageFormat 二维码的格式
     *          * @throws WriterException
     *          * @throws IOException
     *         
     */
    public static boolean createQrCode(File file, String content, int qrCodeSize, String imageFormat) throws WriterException, IOException {
//设置二维码纠错级别ＭＡＰ
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        // 矫错级别  
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串  
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i - 100, j - 100, 1, 1);
                }
            }
        }
        return ImageIO.write(image, imageFormat, file);
    }

    private static final String url = "http://www.hzdxq.cn/pointcoil/img/pointcoil_img/";

    public static String qrCode(String contents) {

        int width = 200;
        int height = 200;
        String format = "png";
        String imageString;
        String uuid;
        try {
            /*
             * file 随机名称
             */
            uuid = UUID.randomUUID().toString();
            /*
             * 创建二维码生成器
             */
            BitMatrix bm = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height);

            /*
             * 用随机名创建文件地址
             */

            Path filePath = new File("/code/img/pointcoil_img/" + uuid + ".png").toPath();

            File file = new File(filePath.toUri());

            /*
             * 生成二维码保存到file
             */
            MatrixToImageWriter.writeToPath(bm, format, filePath);

            /*
             * 创建二维码file，因为上面生成了，所以该文件一定存在
             */
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            /**
             * 返回Base64编码过的字节数组字符串
             */
            imageString = Base64.encodeBase64String(imageBytes);
            bos.close();
        } catch (Exception e) {
            return null;
        }

        return url+uuid+".png";


    }


}
