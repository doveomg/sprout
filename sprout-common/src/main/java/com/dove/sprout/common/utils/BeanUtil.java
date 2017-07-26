package com.dove.sprout.common.utils;

import com.dove.sprout.common.logger.Logger;
import com.dove.sprout.common.logger.LoggerFactory;
import org.apache.commons.beanutils.PropertyUtils;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @Description: 对象工具类
* @author bod
* @date 2016年12月20日 下午1:24:25 
*
 */
public class BeanUtil {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * 包含对应字段对象
	* @author bod     
	 */
	@SuppressWarnings("unchecked")
	public static <T> T include(T source, String... fieldNames) {
		VerificationUtil.notEmpty(fieldNames);
		T desc = null;
		try {
			desc = (T) source.getClass().newInstance();
			copy( source , desc , fieldNames) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}
	
	/**
	 * 不包含对应字段对象
	* @author bod     
	 */
	@SuppressWarnings("unchecked")
//	public static <T> T notInclude(T source, String... fieldNames) {
//		VerificationUtil.notEmpty(fieldNames);
//		T desc = null;
//		try {
//			desc = (T) source.getClass().newInstance();
//			BeanUtils.copyProperties(source, desc, fieldNames);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return desc;
//	}
	
	/**
	 * 拷贝全属性
	* @author bod     
	 */
	public static <T, O> void copy(O source, T desc){
		Field [] fields = source.getClass().getDeclaredFields()  ;
		String [] properties = new String [ fields.length ] ;
		for( int i = 0 ; i < fields.length ; i ++ ){
			properties[ i ] = fields[ i ].getName() ;
		}
		copy( source , desc , properties ) ; 
	}
	
	/**
	 * 
	* 拷贝对象某些属性 
	* @author bod     
	 */
	public static <T, O> void copy(O source, T desc, String[] fieldNames) {
		copy(source,desc,fieldNames,fieldNames);
	}
	
	/**
	 * 拷贝对象某些属性到另一对象某些属性中
	* @author bod     
	 */
	public static <T, O> void copy(O source, T desc, String[] sourceFieldNames,String[] descFieldNames) {
		VerificationUtil.notEmpty(sourceFieldNames);
		VerificationUtil.notEmpty(descFieldNames);
		int length = sourceFieldNames.length>descFieldNames.length?descFieldNames.length:sourceFieldNames.length;
		for (int i =0;i<length;i++) {
			try {
				Object temp = Reflections.getFieldValue(source, sourceFieldNames[i]);
				Reflections.setFieldValue(desc, descFieldNames[i], temp);
			} catch (Exception e) {
				// ...
			}
		}
	}

	/**
	 * 获取list中某个字段值转为list 
	* @author bod     
	* @throws
	 */
	public static <T> List<T> getPropertyList(List list, String property){
		List<T> plist = new ArrayList<T>();
		for(int i=0;i<list.size();i++){
			Object bean = list.get(i);
			try {
				T pv = (T) getProperty(bean, property);
				plist.add(pv);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return plist;
	}
	
	/**
	 * 获取对象字段值 
	* @author bod     
	* @throws
	 */
	public static Object getProperty(Object bean, String property){
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	  /**
	   * 将Bean --> Map  
	  * @author bod     
	  * @throws
	   */
    @SuppressWarnings("unchecked")
	public static <T> Map<String, T> bean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<String, T> map = new HashMap<String, T>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    T value = (T) getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
        	logger.error("bean2Map Error ", e);
        }  
  
        return map;  
  
    }
    
    public static Map<String, String> bean2MapStr(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, String> map = new HashMap<String, String>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    String value = String.valueOf(getter.invoke(obj));  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
        	logger.error("bean2MapStr Error ", e);
        }  
  
        return map;  
  
    }
	
    /**
     * 序列化对象
     * @author bod
     * 2017年4月26日上午11:19:54
     */
    public static byte[] serialize(Object obj){
    	ObjectOutputStream oos = null;
    	ByteArrayOutputStream baos = null;
    	try{
    		if(obj!=null){
    			baos = new ByteArrayOutputStream();
    			oos = new ObjectOutputStream(baos);
    			oos.writeObject(obj);
    			return baos.toByteArray();
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 反序列化对象
     * @author bod
     * 2017年4月26日上午11:24:29
     */
    public static Object unserialize(byte[] bytes){
    	ByteArrayInputStream bais = null;
    	try{
    		if(bytes!=null && bytes.length>0){
    			bais  = new ByteArrayInputStream(bytes);
    			ObjectInputStream ois = new ObjectInputStream(bais);
    			return ois.readObject();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
}
