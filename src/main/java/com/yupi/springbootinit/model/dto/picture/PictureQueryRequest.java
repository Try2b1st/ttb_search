package com.yupi.springbootinit.model.dto.picture;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 图片请求参数
 *
 * @author 下水道的小老鼠
 */
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索关键词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;

}
