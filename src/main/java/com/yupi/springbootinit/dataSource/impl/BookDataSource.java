package com.yupi.springbootinit.dataSource.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.dataSource.DataSource;

/**
 * 搜索图书
 *
 * @author 下水道的小老鼠
 */
public class BookDataSource implements DataSource<Object> {
    @Override
    public Page<Object> doSearch(String searchText, long pageNumber, long pageSize) {
        return null;
    }
}
