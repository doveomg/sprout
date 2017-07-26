package com.dove.sprout.common.constant;

import com.dove.sprout.common.utils.VerificationUtil;

/**
 * 支付相关枚举 
* @author bod
* @date 2017年1月7日 下午6:08:12 
*
 */
public class PayConstants {
	/**
	 * 加密方式 
	* @author bod
	* @date 2017年1月7日 下午6:08:21 
	*
	 */
	public enum SIGN{
		MD5,
		RSA;
		public static SIGN getSING(String singType){
			VerificationUtil.isNotBlank(singType);
			for(SIGN sign:PayConstants.SIGN.values()){
				if(sign.name().equals(singType)){
					return sign;
				}
			}
			return null;
		}
	}

	/**
	 * 交易类型
	* @author bod
	* @date 2017年1月7日 下午6:08:21
	*
	 */
	public enum PAY_TYPE{
		AILPAY_PC(SIGN.MD5.name()),//MD5
		AILPAY_PC_R(SIGN.RSA.name()),//RSA
		AILPAY_APP(SIGN.RSA.name()),//RSA
		WEIXIN_JSAPI(WX_TRADE_TYPE.JSAPI.name()),//公众号配置
		WEIXIN_NATIVE(WX_TRADE_TYPE.NATIVE.name()),//公众号配置
		WEIXIN_APP(WX_TRADE_TYPE.APP.name()),//开放平台配置
		BACNK(null);
		private String value;

		private PAY_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static PAY_TYPE getPAY_TYPE(String paytype){
			VerificationUtil.isNotBlank(paytype);
			for(PAY_TYPE payType:PayConstants.PAY_TYPE.values()){
				if(payType.name().equals(paytype)){
					return payType;
				}
			}
			return null;
		}
	}

	/**
	 * 微信交易类型
	* @author bod
	* @date 2017年1月7日 下午6:08:21
	*
	 */
	public enum WX_TRADE_TYPE{
		//公众号提供
		JSAPI,//公众号支付
		NATIVE,//原生扫码支付
		MICROPAY,//刷卡支付
		//微信开放平台提供
		APP;//APP支付
		public static WX_TRADE_TYPE getTRADE_TYPE(String tradeType){
			VerificationUtil.isNotBlank(tradeType);
			for(WX_TRADE_TYPE wxTradeType:PayConstants.WX_TRADE_TYPE.values()){
				if(wxTradeType.name().equals(tradeType)){
					return wxTradeType;
				}
			}
			return null;
		}
	}
	
	/**
	 * 返回结果code
	* @author bod
	* @date 2017年1月7日 下午6:08:21 
	*
	 */
	public enum RESULT_CODE{
		SUCCESS,
		FAIL;
	}
	
	/**
	 * 支付宝回调结果status
	* @author bod
	* @date 2017年1月7日 下午6:09:26 
	*
	 */
	public enum ALI_TRADE_STATUS{
		TRADE_FINISHED,//交易完成
		TRADE_SUCCESS,//交易成功
		WAIT_BUYER_PAY,//等待交易
		TRADE_CLOSED;//交易关闭
	}
	
}
