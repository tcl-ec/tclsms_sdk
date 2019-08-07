package com.github.tclec.tclsms.enums;

/**
 * 短信类型
 *
 * Created by zzhicheng on 2019/8/7 0007
 */
public enum  MessageType {
    GENERAL("1012888","普通短信"),

    MARKET("1012812","营销短信");

    private String code;

    private String desc;

    public static MessageType getByCode(String code) {
        MessageType result = null;
        if (code != null) {
            for (MessageType i : MessageType.values()) {
                if (i.getCode() == code) {
                    result = i;
                    break;
                }
            }
        }
        return result;
    }

    MessageType(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }

}
