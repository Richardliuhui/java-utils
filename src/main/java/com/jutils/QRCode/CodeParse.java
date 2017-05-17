package com.jutils.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码解析
 * @author liuhui
 * @date 2017-05-17 下午4:51
 */
public class CodeParse {

    public static Result parseCode(String imgPath){
        Result result=null;
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File(imgPath);
            BufferedImage image = ImageIO.read(file);;
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            result = formatReader.decode(binaryBitmap,hints);

            System.out.println("result = "+ result.toString());
            System.out.println("resultFormat = "+ result.getBarcodeFormat());
            System.out.println("resultText = "+ result.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
