package vip.onno.breaker.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vip.onno.breaker.common.dict.ResponseStatus;
import vip.onno.breaker.pojo.BaseResponse;
import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.ListResponse;
import vip.onno.breaker.service.IWeiboCommentService;

@RestController
@RequestMapping(value = "/weibo/comment")
public class ApiWeiboCommentController {
    @Resource
    private IWeiboCommentService WeiboService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiWeiboCommentController.class);

    @RequestMapping(value = "/name/{weiboId}/{userName}", method = RequestMethod.GET)
    public BaseResponse findCommentByName(@PathVariable("weiboId") Long weiboId,
        @PathVariable("userName") String userName) {
        LOGGER.info("find comment by {weibo: {}, name: {}}", weiboId, userName);
        List<Comment> result = WeiboService.findComment(weiboId, userName);
        LOGGER.debug("find result: {}", result);
        return new ListResponse(result, ResponseStatus.OPERATION_SUCCEED);
    }

    @RequestMapping(value = "/id/{weiboId}/{userId}", method = RequestMethod.GET)
    public BaseResponse findCommentByUserId(@PathVariable("weiboId") Long weiboId,
        @PathVariable("userId") Long userId) {
        LOGGER.info("find comment by {weibo: {}, id: {}}", weiboId, userId);
        List<Comment> result = WeiboService.findComment(weiboId, userId);
        LOGGER.debug("find result: {}", result);
        return new ListResponse(result, ResponseStatus.OPERATION_SUCCEED);
    }

}
