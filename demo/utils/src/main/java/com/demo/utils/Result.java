package com.demo.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
/**
 * Created by wq on 2017/1/18.
 * 返回的统一转换
 */
public class Result implements Serializable{
	private static final long serialVersionUID = 7194371581571698528L;
	
	private static final String EMPTY_DATA = "{}";
	
	private static final String EMPTY = "";

	public Result () {
		super();
	}
	/**
	 * 
	 * @param code  1成功
	 * @param msg  失败信息
	 * @param data  具体数据
	 */
	public Result (int code, String msg, Object data) {
		if(msg==null){
			msg = EMPTY;
		}
		if(data==null){
			data = EMPTY_DATA;
		}
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 
	 * @param code  1成功
	 * @param msg  失败信息
	 */
	public Result (int code, String msg) {
		if(msg==null){
			msg = EMPTY;
		}
		if(data==null){
			data = EMPTY_DATA;
		}
		this.code = code;
		this.msg = msg;
	}

	private int code;
	
	private String msg;
	
	private Object data;

	/** 
	 * @return code 
	 */
	
	public int getCode() {
		return code;
	}

	/** 
	 * @param code code 
	 */
	
	public void setCode(int code) {
		this.code = code;
	}

	/** 
	 * @return msg 
	 */
	
	public String getMsg() {
		return msg;
	}

	/** 
	 * @param msg msg 
	 */
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/** 
	 * @return data 
	 */
	
	public Object getData() {
		return data;
	}

	/** 
	 * @param data data 
	 */
	
	public void setData(Object data) {
		this.data = data;
	}

	/* (非 Javadoc)  
	 * <p>Title: toString</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see java.lang.Object#toString() 
	 */ 
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
