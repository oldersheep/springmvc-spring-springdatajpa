package com.xxx.bos.dto;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
  * @className   类名 : EasyUIResult
  * @description 作用 : 自定义封装easyUI的返回结果
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午8:26:56 
  * @version 版本 : V1.0  
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyUIResult {

	// 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Integer total;

    private List<?> rows;

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total.intValue();
        this.rows = rows;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static EasyUIResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("rows");
            List<?> list = null;
            if (data.isArray() && data.size() > 0) {
                list = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return new EasyUIResult(jsonNode.get("total").intValue(), list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
