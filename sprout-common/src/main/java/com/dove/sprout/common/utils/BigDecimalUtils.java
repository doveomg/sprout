package com.dove.sprout.common.utils;

import java.math.BigDecimal;

/**
 * 
* BigDecimal 相关计算
* @author bod
* @date 2017年1月7日 下午3:40:01 
*
 */
public class BigDecimalUtils {
	
    /**
     * 修改精度
     * 
     * @param value
     * @param num
     * @return
     */
    public static BigDecimal changeDecimal(Object value, int num) {
        BigDecimal b = new BigDecimal(String.valueOf(value));
        return b.setScale(num, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal multiply( Object a , Object b , int scale ){
    	if( a == null || b == null ){
    		return null ;
    	}
    	BigDecimal bia = new BigDecimal( a.toString() ) ;
    	BigDecimal bib = new BigDecimal( b.toString() ) ;
    	return bia.multiply( bib ).setScale( scale , BigDecimal.ROUND_HALF_UP );
    }
    
    /**
     * 相加方法
     * 
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(Double a, Double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString( b));
        return b1.add(b2);
    }

    /**
     * 两个double相加方法,并保留指定精度
     * 
     * @param a
     * @param b
     * @param num
     * @return
     */
    public static BigDecimal add(Double a, Double b, int num) {
        return changeDecimal(add(a, b), num);
    }

    /**
     * 两个double相减方法
     * 
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal sub(Object a, Object b) {
        BigDecimal b1 = new BigDecimal(a.toString());
        BigDecimal b2 = new BigDecimal(b.toString());
        return b1.subtract(b2);
    }

    /**
     * 两个double相减方法,并保留指定精度
     * 
     * @param a
     * @param b
     * @param num
     * @return
     */
    public static BigDecimal sub(Object a, Object b, int num) {
        return changeDecimal(sub(a, b), num);
    }
    
    /**
     * 两个double相乘方法
     * 
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul(Object a, Object b) {
        BigDecimal b1 = new BigDecimal(a.toString());
        BigDecimal b2 = new BigDecimal(b.toString());
        return b1.multiply(b2);
    }

    /**
     * 两个double相乘方法,并保留指定精度
     * 
     * @param a
     * @param b
     * @param num
     * @return
     */
    public static BigDecimal mul(Object a, Object b, int num) {
        return changeDecimal(mul(a, b), num);
    }

    /**
     * 两个double相除方法,并保留指定精度
     * 
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static BigDecimal div(Object a, Object b, int scale) {
        BigDecimal b1 = new BigDecimal(a.toString());
        BigDecimal b2 = new BigDecimal(b.toString());
        return b1.divide(b2, scale, 4);
    }
    
    /**
     * 四舍五入并保留指定位数小数
     * @param dbl
     * @param num
     * @return
     */
    public static String formatByDecimalNum(Object dbl, int num) {
		String valueStr = "";
		try{
			if(dbl != null) {
				BigDecimal bd = new BigDecimal(dbl.toString());
				valueStr = bd.setScale(num, BigDecimal.ROUND_HALF_UP).toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return valueStr;
	}
}
