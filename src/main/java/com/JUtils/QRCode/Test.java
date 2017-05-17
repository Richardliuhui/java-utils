package com.JUtils.QRCode;

import com.google.zxing.common.BitMatrix;

import java.io.IOException;

/**
 * a
 *
 * @author liuhui
 * @date 2017-05-17 下午2:53
 */
public class Test {

    public static void main(String[] args) {
        BitMatrix bitMatrix=MatrixToImageWriterEx.createQRCode("https://www.baidu.com/",400,400);
        try {
            MatrixToImageWriterEx.writeToFile(bitMatrix,"jpg","/Users/thejoyrun/Documents/picture/bc.jpg","/Users/thejoyrun/Documents/picture/3.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
