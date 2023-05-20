package com.yupi.springbootinit.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口(新接入的数据源必须实现的接口)
 *
 * @author 下水道的小老鼠
 */
public interface DataSource<T> {

    /**
     * 每个数据源都要提供应该搜索方法
     *
     * @param searchText 搜索关键词
     * @param pageNumber 页码
     * @param pageSize   页的大小
     * @return 对应数据
     */
    Page<T> doSearch(String searchText, long pageNumber, long pageSize);
}
