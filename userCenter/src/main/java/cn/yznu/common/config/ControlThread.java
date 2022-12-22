package cn.yznu.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class ControlThread {

    public static ThreadPoolExecutor threadPoolExecutor;

    private BlockingQueue<Runnable> blockingQueue;

    public ControlThread() {
        blockingQueue = new LinkedBlockingDeque<Runnable>(1000);
        threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.SECONDS, blockingQueue);
        log.info("======== 线程池初始化完成 ========");
    }

}
