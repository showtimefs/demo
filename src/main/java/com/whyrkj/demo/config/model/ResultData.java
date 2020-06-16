package com.whyrkj.demo.config.model;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * ResultData 用于返回值封装，也用于服务端与客户端的 json 数据通信
 *
 * <pre>
 * 一、主要应用场景：
 * 1：业务层需要返回多个返回值，例如要返回业务状态以及数据
 * 2：renderJson(ret) 响应 json 数据给客户端
 *
 * 二、工作模式：
 * 1：当调用 ok()、fail()、setOk()、setFail() 方法之后，ResultData 生成的 json 数据的状态属性为：status:ok 以及 status:fail
 *
 * 三、实例
 * 1：服务端
 *    ResultData ret = service.justDoIt(paras);
 *    renderJson(ret);
 *
 *    判断：
 *    if (ret.isOk()) {
 *      do something...
 *    } else {
 *        do other something...
 *    }
 *
 * 2：javascript 客户端 ajax 回调函数通常这么用：
 *    success: function(ret) {
 *       if(ret.status == "ok") {
 *       	...
 *       }
 *
 *       if (ret.status == "fail") {
 *       	...
 *       }
 *    }
 */
@SuppressWarnings("rawtypes")
public class ResultData extends HashMap {

    private static final String DATA = "data";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String STATE_OK = "ok";
    private static final String STATE_FAIL = "fail";

    public ResultData() {

    }

    public static ResultData by(Object key, Object value) {
        return new ResultData().set(key, value);
    }

    public static ResultData create(Object key, Object value) {
        return new ResultData().set(key, value);
    }

    public static ResultData create() {
        return new ResultData();
    }

    public static ResultData ok200() {
        return new ResultData().setStatus(200).setMessage("");
    }

    public static ResultData ok() {
        return new ResultData().setOk();
    }

    public static ResultData ok(Object key, Object value) {
        return ok().set(key, value);
    }

    public static ResultData fail500() {
        return new ResultData().setStatus(500);
    }

    public static ResultData fail() {
        return new ResultData().setFail();
    }

    public static ResultData fail(Object key, Object value) {
        return fail().set(key, value);
    }

    public ResultData setData(Object data) {
        super.put(DATA, data);
        return this;
    }

    public ResultData setMessage(Object data) {
        super.put(MESSAGE, data);
        return this;
    }

    public ResultData setStatus(int status) {
        super.put(STATUS, status);
        return this;
    }

    public ResultData setError(String error) {
        super.put(ERROR, error);
        return this;
    }

    public ResultData setOk() {
        super.put(STATUS, STATE_OK);
        return this;
    }

    public ResultData setFail() {
        super.put(STATUS, STATE_FAIL);
        return this;
    }

    public boolean isOk() {
        return STATE_OK.equals(getString(STATUS));
    }

    public boolean isFail() {
        return STATE_FAIL.equals(getString(STATUS));
    }

    public ResultData set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public ResultData set(Map map) {
        super.putAll(map);
        return this;
    }

    public ResultData set(ResultData ret) {
        super.putAll(ret);
        return this;
    }

    public ResultData delete(Object key) {
        super.remove(key);
        return this;
    }

    public <T> T getAs(Object key) {
        return (T) get(key);
    }

    public String getString(Object key) {
        Object s = get(key);
        return s != null ? s.toString() : null;
    }

    public Integer getInteger(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.intValue() : null;
    }

    public Long getLong(Object key) {
        Number n = (Number) get(key);
        return n != null ? n.longValue() : null;
    }

    public Number getNumber(Object key) {
        return (Number) get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean) get(key);
    }

    /**
     * key 存在，并且 value 不为 null
     */
    public boolean notNull(Object key) {
        return get(key) != null;
    }

    /**
     * key 不存在，或者 key 存在但 value 为null
     */
    public boolean isNull(Object key) {
        return get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == true));
    }

    /**
     * key 存在，并且 value 为 false，则返回 true
     */
    public boolean isFalse(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean) value == false));
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object ret) {
        return ret instanceof ResultData && super.equals(ret);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
