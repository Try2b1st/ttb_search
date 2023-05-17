package com.yupi.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索-门面
 *
 * @author 下水道的小老鼠
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String searchType = searchRequest.getSearchType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);
        ThrowUtils.throwIf(StringUtils.isBlank(searchType), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();

        if (searchTypeEnum == null) {
            //多线程优化（不一定会更快）
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(
                    () -> pictureService.searchPictures(searchText, 1, 10));

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVoPage = postService.listPostVOByPage(postQueryRequest, request);
                return postVoPage;
            });

            //等待全部多线程执行完后才能继续执行后面的程序
            CompletableFuture.allOf(pictureTask, userTask, postTask).join();

            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询错误", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询错误");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            switch (searchTypeEnum) {
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVoPage = postService.listPostVOByPage(postQueryRequest, request);
                    searchVO.setPostList(postVoPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureService.searchPictures(searchText, 1, 10);
                    searchVO.setPictureList(picturePage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                    searchVO.setUserList(userVOPage.getRecords());
                    break;
                default:
            }
            return searchVO;
        }
    }
}
