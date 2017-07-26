package com.dove.sprout.common.exception;

/**
 * 
* @Description: 公共异常
* @author bod
* @date 2016年12月14日 下午5:03:21 
*
 */
public class CommonException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommonException(COMMON_EXPECTION ex,Throwable cause){
		super(ex.getCode(),ex.getMessage(),cause);
	}
	
	public CommonException(COMMON_EXPECTION ex){
		super(ex.getCode(),ex.getMessage());
	}
	
	public CommonException(int code,String message){
		super(code,message);
	}
	
	public CommonException(String message,Throwable cause){
		super(BaseException.ERROR_CODE,message,cause);
	}
	
	public CommonException(String message){
		super(BaseException.ERROR_CODE,message);
	}
	
	public CommonException(){
		super();
	}
	
	public enum COMMON_EXPECTION{
		LOCK_OVER_TIME_REEOR(100001,"获取超时，无法执行任务!");
		private COMMON_EXPECTION(int code, String message) {
			this.code = code;
			this.message = message;
		}
		private int code;
		private String message;
		public int getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
	}
}
