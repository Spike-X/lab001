@javax.xml.bind.annotation.XmlSchema(namespace = "http://WebXml.com.cn/", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
package com.aircraft.codelab.pioneer.service.cxf.weather;

/*
  生成客户端文件方法
  apache-cxf-3.5.7.zip 解压->bin目录->cmd

  wsdl2java -help
  1.wsdl2java -encoding utf-8  http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl
  2.wsdl2java -encoding utf-8 -d D:\workspace\cxf -autoNameResolution http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl
  3.wsdl2java -encoding utf-8 -d D:\workspace\cxf -autoNameResolution D:\workspace\cxf\webxml.com.cn_WebServices_WeatherWS.asmx_wsdl.xml
  4.wsdl2java -encoding utf-8 -d D:\workspace\cxf -p com.aircraft.codelab.pioneer.service.cxf.weather -autoNameResolution D:\workspace\cxf\webxml.com.cn_WebServices_WeatherWS.asmx_wsdl.xml

  报错: undefined element declaration 's:schema'
  直接在 wsdl 文档页面右键，选择查看源代码，然后 Ctrl + S 保存，打开文件，替换其中的内容
  <s:element ref="s:schema" /><s:any /> 替换成 <s:any minOccurs="2" maxOccurs="2"/>
 */