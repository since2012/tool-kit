package org.mark.mybatis.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

/**
 * 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */
@Data
public class GlobalResult<T> {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // 响应业务状态
    private Integer code;
    // 响应消息
    private String msg;
    // 响应中的数据
    private T data;

    /**
     * 成功
     *
     * @param data
     */
    public GlobalResult(T data) {
        this.code = 200;
        this.msg = "操作成功";
        this.data = data;
    }

    //失败
    public GlobalResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //失败
    public GlobalResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //成功带数据
    public static <T> GlobalResult ok(T data) {
        return new GlobalResult(data);
    }

    //成功但无数据
    public static GlobalResult ok() {
        return new GlobalResult(null);
    }

    //失败信息
    public static GlobalResult errorMsg(String msg) {
        return new GlobalResult(500, msg);
    }

    //根据布尔值返回数据
    public static GlobalResult tip(boolean result) {
        if (result) {
            return GlobalResult.ok();
        }
        return GlobalResult.errorMsg("操作失败");
    }


    public static <T> GlobalResult errorMap(T data) {
        return new GlobalResult(501, "error", data);
    }

    public static GlobalResult errorTokenMsg(String msg) {
        return new GlobalResult(502, msg);
    }

    public static GlobalResult errorException(String msg) {
        return new GlobalResult(555, msg);
    }

    public static GlobalResult build(Integer code, String msg, Object data) {
        return new GlobalResult(code, msg, data);
    }

    public static GlobalResult build(Integer code, String msg) {
        return new GlobalResult(code, msg, null);
    }

    public Boolean isOK() {
        return this.code == 200;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: 将json结果集转化为LeeJSONResult对象
     * 需要转换的对象是一个类
     * @author leechenxiang
     * @date 2016年4月22日 下午8:34:58
     */
    public static GlobalResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, GlobalResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @return
     * @Description: 没有object对象的转化
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:21
     */
    public static GlobalResult format(String json) {
        try {
            return MAPPER.readValue(json, GlobalResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: Object是集合转化
     * 需要转换的对象是一个list
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:31
     */
    public static GlobalResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}