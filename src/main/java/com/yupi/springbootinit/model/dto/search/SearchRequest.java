package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 图片请求参数
 *
 * @author 下水道的小老鼠
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索关键词
     */
    private String searchText;

    /**
     * 搜索类型
     */
    private String searchType;

    private static final long serialVersionUID = 1L;

}
