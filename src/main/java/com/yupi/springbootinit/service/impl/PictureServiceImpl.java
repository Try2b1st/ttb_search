package com.yupi.springbootinit.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片搜索接口实现类
 *
 * @author 下水道的小老鼠
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> searchPictures(String searchText, long pageNumber, long pageSize) {
        long current = (pageNumber - 1) * pageSize;
        String url = "https://cn.bing.com/images/search?q="+ searchText +"&first=" + current;
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "图片获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            Picture picture = new Picture();
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            String text = element.select(".inflnk").get(0).attr("aria-label");
            picture.setUrl(murl);
            picture.setTitle(text);
            pictureList.add(picture);
            if(pictureList.size() == pageSize){
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNumber,pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
