package com.wsz.mywiki.job;

import com.wsz.mywiki.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DocJob {

    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Resource
    private DocService docService;
    /**
     * 每30秒更新电子数信息
     */
    @Scheduled(cron = "1/5 * * * * ?")
    public void cron() throws InterruptedException {
        docService.updateEbookInfo();
    }

}
