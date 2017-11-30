package com.nowhealth.mobile.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;

import sun.misc.BASE64Encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * gogle二维码生成工具类
 * @Description: (这里用一句话描述这个类的作用) 
 * @author zhuhaidong
 * @date 2017-8-31 下午5:20:48
 */
public class QRCodeUtil {

	 public static final String QRCODE_DEFAULT_CHARSET = "UTF-8"; //默认编码  
	  
	    public static final int QRCODE_DEFAULT_HEIGHT = 400;  
	  
	    public static final int QRCODE_DEFAULT_WIDTH = 400;  
	  
	    private static final int BLACK = 0xFF000000;  
	    private static final int WHITE = 0xFFFFFFFF;  
	      
	    //测试用例
	    public static void main(String[] args) throws IOException, NotFoundException{  
	    	String result="";
	        String data = "http://www.baidu.com";   //二维码连接
	        // File logoFile = new File("C:/Users/haidong/Desktop/aaaa/logo5.png");  //logo 路径
	        BufferedImage image = QRCodeUtil.createQRCodeWithLogo(data, "logoFile");  
	        //ImageIO.write(image, "png", new File("C:/Users/haidong/Desktop/aaaa/michael_zxing_logo.png")); 
	        //读取文件转换为字节数组
	    	ByteArrayOutputStream output = new ByteArrayOutputStream(); 
	        ImageIO.write(image, "png",output );  //output:字节输出流
	        byte[] bytes = output.toByteArray();
	        BASE64Encoder encoder = new BASE64Encoder();  //将字节数组转为二进制
	        result = encoder.encodeBuffer(bytes).trim();  //二进制字节码
	        output.flush();
	        output.close();
	        System.out.println("done");  
	    }  
	  
	    /**  
	     * 创建带有默认设置的qrcode 
	     * @author zhangwenchao  
	     * @param data  
	     * @return  
	     */  
	    public static BufferedImage createQRCode(String data) {  
	        return createQRCode(data, QRCODE_DEFAULT_WIDTH, QRCODE_DEFAULT_HEIGHT);  
	    }  
	  
	    /**  
	     * 创建带有默认字符集的qrcode  
	     * @author zhangwenchao  
	     * @param data  
	     * @param width  
	     * @param height  
	     * @return  
	     */  
	    public static BufferedImage createQRCode(String data, int width, int height) {  
	        return createQRCode(data, QRCODE_DEFAULT_CHARSET, width, height);  
	    }  
	  
	    /**  
	     * 创建带有指定字符集的qrcode 
	     * @author zhangwenchao  
	     * @param data  
	     * @param charset  
	     * @param width  
	     * @param height  
	     * @return  
	     */  
	    @SuppressWarnings({ "unchecked", "rawtypes" })  
	    public static BufferedImage createQRCode(String data, String charset, int width, int height) {  
	        Hashtable hint = new Hashtable();  
	        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
	        hint.put(EncodeHintType.CHARACTER_SET, charset);  
	  
	        return createQRCode(data, charset, hint, width, height);  
	    }  
	  
	    /**  
	     * 创建带有指定提示的qrcode 
	     * @author zhangwenchao
		 * @param 数据
		 * @param 字符集
		 * @param 提示
		 * @param 宽度
		 * @param 高度  
	     * @return  
	     */  
	    public static BufferedImage createQRCode(String data, String charset, Hashtable<EncodeHintType, ?> hint, int width,  
	            int height) {  
	        BitMatrix matrix;  
	        try {  
	            matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE,  
	                    width, height, hint);  
	            return toBufferedImage(matrix);  
	        } catch (WriterException e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        } catch (Exception e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        }  
	    }  
	    public static BufferedImage toBufferedImage(BitMatrix matrix) {  
	        int width = matrix.getWidth();  
	        int height = matrix.getHeight();  
	        BufferedImage image = new BufferedImage(width, height,  
	                BufferedImage.TYPE_INT_RGB);  
	        for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
	            }  
	        }  
	        return image;  
	    }  
	    /**  
	     * 创建带有默认设置和标志的qrcode 
	     * @author zhangwenchao  
	     * @param data  
	     * @param logoFile  
	     * @return  
	     */  
	    public static BufferedImage createQRCodeWithLogo(String data, String logoFile) {  
	        return createQRCodeWithLogo(data, QRCODE_DEFAULT_WIDTH, QRCODE_DEFAULT_HEIGHT, logoFile);  
	    }  
	  
	    /**  
	    *创建带有默认字符集和徽标的qrcode
		* @author zhangwenchao
		* @param 数据
		* @param 宽度
		* @param 高度
		* @param logoFile 
	    * @return  
	    */  
	    public static BufferedImage createQRCodeWithLogo(String data, int width, int height, String logoFile) {  
	        return createQRCodeWithLogo(data, QRCODE_DEFAULT_CHARSET, width, height, logoFile);  
	    }  
	  
	    /**  
	     * Create qrcode with specified charset and logo  
	     * @author zhangwenchao
	     * @param 数据
	     * @param 字符集
	     * @param 宽度
	     * @param 高度
	     * @param logoFile
	     * @return  
	     */  
	    @SuppressWarnings({ "unchecked", "rawtypes" })  
	    public static BufferedImage createQRCodeWithLogo(String data, String charset, int width, int height, String logoFile) {  
	        Hashtable hint = new Hashtable();  
	        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
	        hint.put(EncodeHintType.CHARACTER_SET, charset);  
	        return createQRCodeWithLogo(data, charset, hint, width, height, logoFile);  
	    }  
	  
	    /**  
	     * 创建带有指定提示和标志的qrcode 
	     * @author zhangwenchao
		 * @param 数据
		 * @param 字符集
		 * @param 提示
		 * @param 宽度
		 * @param 高度
		 * @param logoFile
	     */  
	    public static BufferedImage createQRCodeWithLogo(String data, String charset, Hashtable<EncodeHintType, ?> hint,  
	            int width, int height, String logoFile) {  
	        try {  
	            BufferedImage qrcode = createQRCode(data, charset, hint, width, height);  
	            BufferedImage logo = ImageIO.read(new URL(logoFile));  
	            int deltaHeight = height - logo.getHeight();  
	            int deltaWidth = width - logo.getWidth();  
	  
	            BufferedImage combined = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);  
	            Graphics2D g = (Graphics2D) combined.getGraphics();  
	            g.drawImage(qrcode, 0, 0, null);  
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));  
	            g.drawImage(logo, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);  
	  
	            return combined;  
	        } catch (IOException e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        } catch (Exception e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        }  
	    }  
	  
	    /**  
	     *返回base64以获得图像
         * @author zhangwenchao
         * @param 形象
         * @return  
	     */  
	    public static String getImageBase64String(BufferedImage image) {  
	        String result = null;  
	        try {  
	            ByteArrayOutputStream os = new ByteArrayOutputStream();  
	            OutputStream b64 = new Base64OutputStream(os);  
	            ImageIO.write(image, "png", b64);  
	            result = os.toString("UTF-8");  
	        } catch (UnsupportedEncodingException e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        } catch (IOException e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        }  
	        return result;  
	    }  
	  
	    /**  
	     *将base64Image数据解码为图像
	 	 * @author zhangwenchao
	 	 * @param base64ImageString
		 * @param文件  
	     */  
	    public static void convertBase64StringToImage(String base64ImageString, File file) {  
	        FileOutputStream os;  
	        try {  
	            Base64 d = new Base64();  
	            byte[] bs = d.decode(base64ImageString);  
	            os = new FileOutputStream(file.getAbsolutePath());  
	            os.write(bs);  
	            os.close();  
	        } catch (FileNotFoundException e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        } catch (IOException e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        } catch (Exception e) {  
	            throw new RuntimeException(e.getMessage(), e);  
	        }  
	    }  
	  
 
}
