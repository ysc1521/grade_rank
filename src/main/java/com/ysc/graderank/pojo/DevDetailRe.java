package com.ysc.graderank.pojo;

import com.ysc.graderank.enums.DevKindEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevDetailRe {
    private Integer id;

    private Integer yid;

    private String name;

    private Double score;

    private DevKindEnum kind;

    private String verify;
}