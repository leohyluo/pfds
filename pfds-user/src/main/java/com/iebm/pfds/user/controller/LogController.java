package com.iebm.pfds.user.controller;

import com.iebm.pfds.commons.core.web.ResponseMessage;
import com.iebm.pfds.commons.core.web.WebUtils;
import com.iebm.pfds.commons.log.annotation.FwCallLog;
import com.iebm.pfds.commons.log.type.CallLogLv;
import com.iebm.pfds.commons.log.type.CallLogType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    //@RequestMapping(value = "/test", method = RequestMethod.GET)
    @GetMapping("/test")
    @FwCallLog(description = "aspectj日志", restType = CallLogType.WRITE, logLv = CallLogLv.NORMAL)
    public ResponseMessage logTest() {
        System.out.println(1/0);
        return WebUtils.buildSuccessResponseMessage();
    }
}
