package com.aircraft.codelab.pioneer.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 2022-11-27
 * 加签验签工具类
 *
 * @author tao.zhang
 * @since 1.0
 */
public class SignUtil {
    /**
     * 加签
     *
     * @param content    加密base64密文
     * @param privateKey rsa私钥
     * @param charset    字符集
     * @return 签名
     */
    public static String signBySha256WithRsa(String content, String privateKey, String charset) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(spec);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes(charset));

            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            String errorMessage = "签名遭遇异常，content=" + content + " privateKeySize=" + privateKey.length() + " reason=" + e.getMessage();
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * 验签
     *
     * @param content   加密base64密文
     * @param sign      签名
     * @param publicKey rsa公钥
     * @param charset   字符集
     * @return boolean
     */
    public static boolean verifyBySha256WithRsa(String content, String sign, String publicKey, String charset) {
//		1、使用正则表达式把应答报文分为两部分：JSON格式的报文（request/response）部分和签名（sign）部分，注意为保原文顺序，不能转为JSON对象去操作
//		2、使用SHA256算法对JSON格式的报文（request/response）部分获取信息摘要
//		3、使用公钥对签名解码为消息摘要
//		4、比较第2、3步的消息摘要，如果相同，说明原文没有变化，验证通过
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes(charset));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            String errorMessage = "验签遭遇异常，content=" + content + " sign=" + sign + " publicKey=" + publicKey + " reason=" + e.getMessage();
            throw new RuntimeException(errorMessage, e);
        }
    }

    // 公钥
    static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1BTldqCfx+tNdteXb9JrGFS0ptEvN2TVng16ut0X/mlJWhwvUF3oSiVh4Wr/0B2xQsVSYEd8ay24REEk1xS9abuo8oVdx+0jqvgCdkfaeUAuAt37mjSDM8y1MAy1S+H7hYffG5WmrCUODr8wFikgQEW4fr3+V2ymS6907Oxv5SfBds2NsWWDRq+3LxI8Mmxb8vFv6haYnGSOFeasNtztVYlXxBpgwz5zRMeLLmVt5DZQyZEnEsuBGTP7HZTYM2A33+gDWQqfolbfr/lCLJi4I+moMOeb20tArNVGkEo+GRjfygzJrAiFvSIstbqMJEMfDzZdM6Yc0WNZtJQmVEd1HwIDAQAB";

    // 私钥
    static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDUFOV2oJ/H601215dv0msYVLSm0S83ZNWeDXq63Rf+aUlaHC9QXehKJWHhav/QHbFCxVJgR3xrLbhEQSTXFL1pu6jyhV3H7SOq+AJ2R9p5QC4C3fuaNIMzzLUwDLVL4fuFh98blaasJQ4OvzAWKSBARbh+vf5XbKZLr3Ts7G/lJ8F2zY2xZYNGr7cvEjwybFvy8W/qFpicZI4V5qw23O1ViVfEGmDDPnNEx4suZW3kNlDJkScSy4EZM/sdlNgzYDff6ANZCp+iVt+v+UIsmLgj6agw55vbS0Cs1UaQSj4ZGN/KDMmsCIW9Iiy1uowkQx8PNl0zphzRY1m0lCZUR3UfAgMBAAECggEAb5efNdBGwrb5R7MhJ+GcIG1RsQUYxxjznfqRvYlgeqmDwzpZWvxOodURr77RY/x7rRqOwcJboTmpX6C3dcSf9eacex/ZvLz2q5tcEEi2mF5A15leLkG7V6I7JFFLg+Ygpp5bsvQHg0TsJK5M6xwwsCD/dxEZp36v8dsctx2hsv1F+gFU1UtBbtEQ5mNEfDs8pbNA8/LUjEcTnE65m5hghT68xBhrnLnRd9hOjhGMfe2Be+9zqB7yIhigw83Ash8jW493VBxJdSuBowSaF9Gyro2pcoQakxvzsqlS9SgWkXp9z8mIOfE6SMko7shS7oBszDlZ0pUbkC9J6JyJwob0QQKBgQD/g6lVCqUCuH7ZjeXuK/MaFS9eXByQWotCannWNjOE2QL+o6jG3+0q5/9l+Exa8IGP3d5IlHVtYihwxkrW31xqqkJ0TB/ND6U9ikr44DNNjvmlx+c2A/AS6LzMzRk/OoVyVQmsW1PjjrlnMnv/4meGdarhg3F+1gJUo1kYrrVu+QKBgQDUfBmCkn/WCCEhKO/iS7lr6EVUhchRuqaMOyGavnT+6K3jCeWAqx/D0lS0DlXt/FUcT5195ij7vlRcXrlMTw6ZfZs0GRhVTxnfF5a64N9TPV1YnPxhgvlfXehjNKITb7eDHhH917AeQu6+B6yk2nb+r6bRJptvSrt8FdhM0JfS1wKBgDDbCN6k2WZKxp5sfmFq7+G12UfOjvufBG+fkko+NN2a7PKpclUMbfm9QF1L+7WLtQpAHwd7s3RFx3vAvoCVVsNiaLB5+b6o1iyNK8aof8a9pshnD7OJTzceXzM9qC0p0GhLED1vfPvOx26O/ZywuqDAEPtruaDJ5MowLZIFndVpAoGAPkXsmAblbk3Nn9FGlJTKz+QvxXi737ALNZfy3k4pLbf1lk+FMnN0iMhwyKBW8Lt+GiD/MDGvGzCJutSMC+AzkCpuvRMJvZ7EH0zjgPt8mAGpBNplYRKokRmNjN2VDos0Rjoe3b1DwWquW4UWRO4956CFDFD7Rt+pGNGyJbkQddsCgYEAteEBkmTVJ5w3kctuqkYSKDZ5LaMlj9VvF9vjiIbniAlL2qNVKLIDRzUTIIkUNppIcKaxBfnL7ypz7qWvbSoTy4oHBsSx38Gypfu8Lvlo9G7ENNltx+KvlaMaP4PQYBKJT2kU1GvDcTEb4YK52yYSAlWf0877GiNpb4Sdaq0eEDY=";

    public static void main(String[] args) throws Exception {
        String src = "data=FFkrLNn0wqwS1FSCbqnPiCsU1DsX5HjXvP333v29cMsnOFlZUJqzDStXhUXc5XQYV6nj4UCFOdanxvQDXSgtKFM/IdK/PZz5S56fflgUXVA2iDr8S+2kAXhd7/M0jDaJXqsaiExMg08uee0boAj2enKRW2Q2Kq/zeB2e+5BCc+0Xn3sGdy90GIewhyrxbD1AueFZO9cS8NnvMJAxXz5/nGXWXeVTQtwTwzolastqb0qgi59HSbcssnyv9y4Lr3a1OGy4nI+uEIaryzKCUIUUVyh36VTKcK0IZATjsdb87WwWW7GwPH4sIGKHYF8r9puABL99bslgFsO0H1fA1Sa8xCqkxbubCZRWMlfuXh7ykeRgEtHDuIwU5lUIDLreUJMEvt15FNTaa3+XnJZsykT0bi/41bT4Y+8QXOY27wn2dNzDkyfJkU4Ur/OXeAHG0dXB3gzrLqrqDUbtdEDS/DWVNdRuT2Msg5H+vg/53GNyzQj+AS6/gDRlB+1hEO8ntF+hRpuWvpdONQi3/afOrv02nhFH97Hry0Zn585M2V0a0tdeG0aQgBmehPhyzEOoT9cH2DkwDr9Px0BgJx/yKiMNiZYPYxcvU1RHEvld4KhybJk=&mess=eLs47OgXy10LPi6Unnh6Ao58&timestamp=1592444447&key=40cM782hxNpwZoYH9kqxob75gtcmTH6X";
        String sign = signBySha256WithRsa(src, privateKey, StandardCharsets.UTF_8.name());
        System.out.println("签名= " + sign);
        System.out.println("验签结果= " + verifyBySha256WithRsa(src, sign, publicKey, StandardCharsets.UTF_8.name()));
    }
}
