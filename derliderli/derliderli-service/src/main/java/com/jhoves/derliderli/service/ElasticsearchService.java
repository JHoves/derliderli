package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.repository.UserInfoRepository;
import com.jhoves.derliderli.dao.repository.VideoRepository;
import com.jhoves.derliderli.domain.UserInfo;
import com.jhoves.derliderli.domain.Video;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author JHoves
 * @create 2023-02-15 15:52
 */
@Service
public class ElasticsearchService {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //保存用户信息到es
    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }

    //保存视频到es
    public void addVideo(Video video){
        videoRepository.save(video);
    }

    //根据关键词查询es
    public Video getVideos(String keyword){
        return videoRepository.findByTitleLike(keyword);
    }

    public void deleteAllVideos(){
        videoRepository.deleteAll();
    }

    //实现全文搜索
    public List<Map<String,Object>> getContents(String keyword,
                                                Integer pageNo,
                                                Integer pageSize) throws IOException {
        String[] indices = {"videos","user-infos"};
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //构建分页
        sourceBuilder.from(pageNo - 1);
        sourceBuilder.size(pageSize);
        //构建多条件查询的请求
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(keyword,"title","description","nick");
        sourceBuilder.query(matchQueryBuilder);
        searchRequest.source(sourceBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮显示
        String[] array = {"title","description","nick"};
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String key : array) {
            highlightBuilder.fields().add(new HighlightBuilder.Field(key));
        }
        //如果要多个字段进行高亮，要为false
        highlightBuilder.requireFieldMatch(false);
        //把高亮的内容变为红色
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //对结果进行处理
        List<Map<String,Object>> arrayList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()){
          //处理高亮字段
          Map<String, HighlightField> highlightBuilderFields = hit.getHighlightFields();
          Map<String,Object> sourceMap = hit.getSourceAsMap();
          for (String key : array){
              HighlightField field = highlightBuilderFields.get(key);
              if(field != null){
                  Text[] fragments = field.fragments();
                  String str = Arrays.toString(fragments);
                  str = str.substring(1,str.length()-1);
                  sourceMap.put(key,str);
              }
          }
          arrayList.add(sourceMap);
        }
        return arrayList;
    }
}
