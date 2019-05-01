package com.ysc.graderank.enums;

public enum DevKindEnum {
    TECHNOLOGY("科技创新"),
    LITERARY("文体特长"),
    STUDENT_WORK("学生工作");

    private String desc;
    DevKindEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
