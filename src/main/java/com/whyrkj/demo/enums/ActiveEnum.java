package com.whyrkj.demo.enums;

/**
 * @author zhangsanfeng
 * @date 2019/6/17 17:00
 */
public enum  ActiveEnum {

    ACTIVE(1,"0","启用"),
    DELETE(2, "1", "禁用"),
    FREEZE(3, "2", "冻结");

    private Integer sort;

    private String code;

    private String desc;

    ActiveEnum(Integer sort,String code,String desc){
        this.sort = sort;
        this.code = code;
        this.desc = desc;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
