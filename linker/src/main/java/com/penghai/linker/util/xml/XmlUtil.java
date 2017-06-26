package com.penghai.linker.util.xml;


import java.io.StringReader;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;


/**
 * 解析XML工具类
 * @author 徐超
 * @Date 2017年5月2日 下午5:57:49
 */
public class XmlUtil{
	// 多线程安全的Context.  
    private JAXBContext jaxbContext;  
  
    /**
     * 所有需要序列化的Root对象的类型 
     * @author 徐超
     * @Date 2017年5月2日 下午5:58:20
     * @param types
     */
    public XmlUtil(Class<?>... types) {  
        try {  
            jaxbContext = JAXBContext.newInstance(types);  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
  
    /**
     * 将xml文本转为JavaObject
     * @author 徐超
     * @Date 2017年5月2日 下午5:58:30
     * @param xml
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml) {  
        try {  
            StringReader reader = new StringReader(xml);  
            return (T) createUnmarshaller().unmarshal(reader);  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /**
     * 将xml文本转为JavaObject--支持大小写敏感
     * @author 徐超
     * @Date 2017年5月2日 下午5:58:30
     * @param xml
     * @param caseSensitive true--区分大小写  false--不区分大小写
     * @return
     */ 
    @SuppressWarnings("unchecked")  
    public <T> T fromXml(String xml, boolean caseSensitive) {  
        try {  
            String fromXml = xml;  
            if (!caseSensitive)  
                fromXml = xml.toLowerCase();  
            StringReader reader = new StringReader(fromXml);  
            return (T) createUnmarshaller().unmarshal(reader);  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
  
    /**
     * 创建UnMarshaller
     * @author 徐超
     * @Date 2017年5月2日 下午6:00:36
     * @return
     */
    public Unmarshaller createUnmarshaller() {  
        try {  
            return jaxbContext.createUnmarshaller();  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /**
     * 封装Root Element 是 Collection的情况
     * @author 徐超
     * @Date 2017年5月2日 下午6:01:11
     */
    public static class CollectionWrapper {  
        @SuppressWarnings("rawtypes")
		@XmlAnyElement
        protected Collection collection;  
    }  
	
}
