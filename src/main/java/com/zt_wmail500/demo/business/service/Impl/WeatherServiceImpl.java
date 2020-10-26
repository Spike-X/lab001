package com.zt_wmail500.demo.business.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zt_wmail500.demo.business.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 天气测试
 * @author: tao.zhang
 * @create: 2020-07-04 15:20
 **/
@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Override
    public void getWeather() {
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl");

            // WSDL里面描述的接口名称(要调用的方法)
            call.setOperationName(new QName("http://WebXml.com.cn/", "getWeather"));

            // 接口方法的参数名, 参数类型,参数模式 IN(输入), OUT(输出) or INOUT(输入输出)
//            call.addParameter("theCityCode", XMLType.XSD_STRING, ParameterMode.IN);
//            call.addParameter("theUserID", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName("http://WebXml.com.cn/", "theCityCode"), XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName("http://WebXml.com.cn/", "theUserID"), XMLType.XSD_STRING, ParameterMode.IN);

            // 设置被调用方法的返回值类型（自定义类型，提供方返回的是json字符串，用string类型接收）
//            call.setReturnType(XMLType.XSD_SCHEMA);
//            call.setReturnType(XMLType.XSD_STRING);
            call.setReturnType(XMLType.SOAP_VECTOR);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI("http://WebXml.com.cn/getWeather");
//            SOAPService soap = new SOAPService();
//            soap.setName("getWeatherbyCityName");
//            call.setSOAPService(soap);

            // 设置方法中参数的值
            Object[] paramValues = new Object[]{"1977", ""};

            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(call.invoke(paramValues));
            log.info("返回结果：{}", json);

            // 解析json串
            List<String> nodeList = objectMapper.readValue(json, new TypeReference<List<String>>() {
            });
            Map<String, Object> map = new HashMap<>();
            map.put("city", nodeList.get(0));
            map.put("today", nodeList.get(7));
            map.put("tomorrow", nodeList.get(12));
            log.info("map:{}", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
