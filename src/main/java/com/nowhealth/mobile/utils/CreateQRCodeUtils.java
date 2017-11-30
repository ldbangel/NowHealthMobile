package com.nowhealth.mobile.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.swetake.util.Qrcode;


/**
 * 二维码的生成与解析工具类
 */
public class CreateQRCodeUtils {
	
	private static final Logger logger = Logger.getLogger(CreateQRCodeUtils.class);
	 /** 
     * 生成二维码(QRCode)图片的公共方法 
     * @param content 存储内容 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     * @return 
     */ 
	private static BufferedImage qrCodeCommon(String content, String imgType, int size,String ccbPath) {  
        BufferedImage bufImg = null; 
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            qrcodeHandler.setQrcodeVersion(size);  
            // 获得内容的字节数组，设置编码格式  
            byte[] contentBytes = content.getBytes("utf-8");  
            // 图片尺寸  
            int imgSize = 90 + 10 * (size - 1);  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            // 设置背景颜色  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, imgSize, imgSize);  
            // 设定图像颜色> BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量，不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容> 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
            boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
            for (int i = 0; i < codeOut.length; i++) {  
                for (int j = 0; j < codeOut.length; j++) {  
                    if (codeOut[j][i]) {  
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                    }  
                }  
            } 
            logger.info("二维码成功生成！！");
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
            }  
            // Image logo = ImageIO.read(new File(ccbPath));//中间logo所在的路径
            Image logo = ImageIO.read(new URL(ccbPath));//
            //* 设置logo的大小,这里设置为二维码图片的20%,因为过大会盖掉二维码
            int widthLogo = logo.getWidth(null)>bufImg.getWidth()*3/10?(bufImg.getWidth()*3/10):logo.getWidth(null), 
                heightLogo = logo.getHeight(null)>bufImg.getHeight()*3/10?(bufImg.getHeight()*3/10):logo.getWidth(null);
            // logo放在中心
            int x = (bufImg.getWidth() - widthLogo) / 2;
            int y = (bufImg.getHeight() - heightLogo) / 2;
            //实例化一个Image对象。
            //gs.drawImage(logo, 60, 60, null);
            //开始绘制图片
            gs.drawImage(logo, x, y, widthLogo, heightLogo, null);
            gs.dispose();  
            bufImg.flush();  
        } catch (Exception e) {  
        	logger.info("二维码生成失败" + e.getMessage());
            e.printStackTrace();  
        }  
        return bufImg;  
    } 
	
	/**
	 * 生成不带logo的二维码
	 * @param content
	 * @param imgType
	 * @param size
	 * @return
	 */
	private static BufferedImage qrCodeCommon(String content, String imgType, int size) {  
        BufferedImage bufImg = null; 
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            qrcodeHandler.setQrcodeVersion(size);  
            // 获得内容的字节数组，设置编码格式  
            byte[] contentBytes = content.getBytes("utf-8");  
            // 图片尺寸  
            int imgSize = 70 + 12 * (size - 1);  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            // 设置背景颜色  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, imgSize, imgSize);  
            // 设定图像颜色> BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量，不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容> 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
            boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
            for (int i = 0; i < codeOut.length; i++) {  
                for (int j = 0; j < codeOut.length; j++) {  
                    if (codeOut[j][i]) {  
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                    }  
                }  
            } 
            logger.info("二维码成功生成！！");
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
            }  
            gs.dispose();  
            bufImg.flush();  
        } catch (Exception e) {  
        	logger.info("二维码生成失败" + e.getMessage());
            e.printStackTrace();  
        }  
        return bufImg;  
    } 
	
    /** 
     * 生成带logo图标的二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */ 
    public String encoderQRCode(String content, ByteArrayOutputStream output, String imgType, int size,String ccbPath) {  
    	String result=""; 
        try {  
            BufferedImage bufImg = this.qrCodeCommon(content, imgType, size, ccbPath); // 生成二维码QRCode图片   
            ImageIO.write(bufImg, imgType, output); 
            byte[] bytes = output.toByteArray();
	        BASE64Encoder encoder = new BASE64Encoder();  //将字节数组转为二进制
	        result = encoder.encodeBuffer(bytes).trim();  //二进制字节码
	        output.flush();
	        output.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return result;   
    }  
    
    /**
     * 生成不带logo图标的二维码图片
     * @param content
     * @param output
     * @param imgType
     * @param size
     * @return
     */
    public static String encoderQRCode(String content, ByteArrayOutputStream output, String imgType, int size) {  
    	String result=""; 
        try {  
            BufferedImage bufImg = qrCodeCommon(content, imgType, size); // 生成二维码QRCode图片   
            ImageIO.write(bufImg, imgType, output); 
            byte[] bytes = output.toByteArray();
	        BASE64Encoder encoder = new BASE64Encoder();  //将字节数组转为二进制
	        result = encoder.encodeBuffer(bytes).trim();  //二进制字节码
	        output.flush();
	        output.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return result;   
    } 
    
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgPath 图片路径 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */ 
    public void encoderQRCode(String content, String imgPath, String imgType, int size,String ccbPath) {  
        try {  
            BufferedImage bufImg = this.qrCodeCommon(content, imgType, size,ccbPath);  
               
            File imgFile = new File(imgPath);  
            // 生成二维码QRCode图片  
            ImageIO.write(bufImg, imgType, imgFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
	/**解析二维码*/
	public static String decode(String qrcodePicfilePath) {
		System.out.println("开始解析二维码！！");
		/* 读取二维码图像数据 */
		File imageFile = new File(qrcodePicfilePath);
		BufferedImage image = null;
		try {
		image = ImageIO.read(imageFile);
		} catch (IOException e) {
	    logger.error("读取二维码图片失败： " + e.getMessage());
		return null;
		}
		/* 解二维码 */
		QRCodeDecoder decoder = new QRCodeDecoder();
		String decodedData = new String(decoder.decode(new J2SEImageGucas(image)));
		logger.info("解析内容如下："+decodedData);
		return decodedData;
		}
}

class J2SEImageGucas implements QRCodeImage {    
    BufferedImage image;    
    
    public J2SEImageGucas(BufferedImage image) {    
        this.image = image;    
    }    
    
    public int getWidth() {    
        return image.getWidth();    
    }    
    
    public int getHeight() {    
        return image.getHeight();    
    }    
    
    public int getPixel(int x, int y) {    
        return image.getRGB(x, y);    
    }    
 }    
