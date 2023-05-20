package com.yupi.springbootinit.esdao;

import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * &#064;from  <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    /**
     * 根据用户ID查询文章
     *
     * @param userId 用户ID
     * @return ES文章 集合
     */
    List<PostEsDTO> findByUserId(Long userId);

    /**
     * 根据文章标题查文章
     *
     * @param title 文章标题
     * @return ES 文章 集合
     */
    List<PostEsDTO> findByTitle(String title);
}