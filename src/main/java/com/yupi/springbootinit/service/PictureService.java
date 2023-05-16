package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;

import java.util.List;

/**
 * 图片搜索接口
 *
 * @author 下水道的小老鼠
 */
public interface PictureService {

    /**
     * 图片搜索方法
     * @param searchText 关键词
     * @param pageNumber 当前页码
     * @param pageSize 分页大小
     * @return 图片列表
     */
    Page<Picture> searchPictures(String searchText, long pageNumber, long pageSize);
}
