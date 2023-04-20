package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.pioneer.pojo.dto.OutMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 2023-04-20
 * 公众号开发
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "公众号接口测试")
@RequestMapping("/wechat")
public class WechatController {
    @GetMapping(value = "/message", produces = MediaType.TEXT_PLAIN_VALUE)
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        return echostr;
    }

    // 明文模式
    /*@PostMapping(value = "/message", produces = MediaType.APPLICATION_XML_VALUE)
    public String validate(@RequestBody String requestBody,
                           @RequestParam(name = "signature", required = false) String signature,
                           @RequestParam(name = "timestamp", required = false) String timestamp,
                           @RequestParam(name = "nonce", required = false) String nonce,
                           @RequestParam(name = "openid", required = false) String openid,
                           @RequestParam(name = "encrypt_type", required = false) String encType,
                           @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        return "";
    }*/


    // 兼容模式和安全模式(openid为空) 测试公众号类似明文模式(encrypt_type、msg_signature为空)
    @PostMapping(value = "/message", produces = MediaType.APPLICATION_XML_VALUE)
    public Object validate1(@RequestBody String requestBody,
                            @RequestParam(name = "signature") String signature,
                            @RequestParam(name = "timestamp") String timestamp,
                            @RequestParam(name = "nonce") String nonce,
//                            @RequestParam(name = "openid", required = false) String openid,
                            @RequestParam(name = "encrypt_type", required = false) String encType,
                            @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        // <?xml+version="1.0"+encoding="UTF-8"+standalone="yes"?>
        //<xml>
        //    <FromUserName>ovwa95xsQy65S1ypuZ6ucRz4_5bU</FromUserName>
        //    <ToUserName>gh_fb93485cc23f</ToUserName>
        //    <CreateTime>1682009515737</CreateTime>
        //    <MsgType>event</MsgType>
        //    <Content>12341gsrhva</Content>
        //    <Image>
        //        <MediaId>qetrej</MediaId>
        //    </Image>
        //</xml>
        OutMessage outMessage = new OutMessage();
        // 把原来的接收方设置为发送方
        outMessage.setFromUserName("ovwa95xsQy65S1ypuZ6ucRz4_5bU");
        // 把原来的发送方设置为接收方
        outMessage.setToUserName("gh_fb93485cc23f");
        // 设置消息类型
        outMessage.setMsgType("event");
        // 设置消息时间
        outMessage.setCreateTime(System.currentTimeMillis());
        // 根据接收到消息类型，响应对应的消息内容
        // 文本
        outMessage.setContent("12341gsrhva");
        outMessage.setMediaId(new String[]{"qetrej"});
        return outMessage;
//        return "success";
    }
}
