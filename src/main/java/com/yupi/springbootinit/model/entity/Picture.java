package com.yupi.springbootinit.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 下水道的小老鼠
 */
@Data
public class Picture implements Serializable {
    private String url;
    private String title;
    private static final long serialVersionUID = 1L;
}
