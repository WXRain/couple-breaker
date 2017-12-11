package vip.onno.breaker.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vip.onno.breaker.common.dict.ResponseStatus;
import vip.onno.breaker.pojo.BaseResponse;
import vip.onno.breaker.pojo.Commit;
import vip.onno.breaker.pojo.ListResponse;
import vip.onno.breaker.service.WeiboService;

@RestController
@RequestMapping(value = "/weibo")
public class ApiCommitController {
    @Resource
    private WeiboService WeiboService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiCommitController.class);

    @RequestMapping(value = "/commit/name/{weiboId}/{userName}", method = RequestMethod.GET)
    public BaseResponse findCommitByName(@PathVariable("weiboId") Long weiboId,
        @PathVariable("userName") String userName) {
        LOGGER.info("find commit by {}-{}", weiboId, userName);
        List<Commit> result = WeiboService.findCommit(weiboId, userName);
        LOGGER.debug("find result: {}", result);
        return new ListResponse(result, ResponseStatus.OPERATION_SUCCEED);
    }

    @RequestMapping(value = "/commit/id/{weiboId}/{userId}", method = RequestMethod.GET)
    public BaseResponse findCommitByUserId(@PathVariable("weiboId") Long weiboId,
        @PathVariable("userId") Long userId) {
        LOGGER.info("find commit by {}-{}", weiboId, userId);
        List<Commit> result = WeiboService.findCommit(weiboId, userId);
        LOGGER.debug("find result: {}", result);
        return new ListResponse(result, ResponseStatus.OPERATION_SUCCEED);
    }

}
