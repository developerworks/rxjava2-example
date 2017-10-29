package io.vertx.schedulers;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * TestScheduler是一种特殊的、非线程安全的调度器，用于测试一些不引入真实并发性、允许手动推进虚拟时间的调度器
 *
 * TestSchedulerDemo
 * http://www.jianshu.com/p/ae7701871498
 *
 * advanceTimeTo 和 advanceTimeBy 的差异:
 *
 * advanceTimeTo 是直接移动到指定的虚拟时间点.
 * advanceTimeBy 是以累加的方式向前移动多少单位的时间.
 */
public class TestSchedulerDemo extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestSchedulerDemo.class);

  @Override
  public void start() throws Exception {
    super.start();
    TestScheduler scheduler = new TestScheduler();

    // 立即调度
    scheduler.createWorker().schedule(new Runnable() {
      @Override
      public void run() {
        LOGGER.info("任务 >>> 虚拟时间点: 0s 立即调度...");
      }
    });

    // 延迟10秒
    scheduler.createWorker().schedule(new Runnable() {
      @Override
      public void run() {
        LOGGER.info("任务 >>> 虚拟时间点: 10s");
      }
    }, 10, TimeUnit.SECONDS);

    // 延迟15秒
    scheduler.createWorker().schedule(new Runnable() {
      @Override
      public void run() {
        LOGGER.info("任务 >>> 虚拟时间点: 15s");
      }
    }, 15, TimeUnit.SECONDS);

    // 把调度器计时器往前推1毫秒 immediate 调度的调度去得不到执行了.
    scheduler.advanceTimeTo(1, TimeUnit.SECONDS);
    LOGGER.info("    移动到虚拟时间点: " + scheduler.now(TimeUnit.SECONDS));

    scheduler.advanceTimeTo(9, TimeUnit.SECONDS);
    LOGGER.info("    移动到虚拟时间点: " + scheduler.now(TimeUnit.SECONDS));

    scheduler.advanceTimeTo(14, TimeUnit.SECONDS);
    LOGGER.info("    移动到虚拟时间点: " + scheduler.now(TimeUnit.SECONDS));

    scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
    LOGGER.info("    虚拟时间前移: " + scheduler.now(TimeUnit.SECONDS));
    scheduler.advanceTimeBy(-1, TimeUnit.SECONDS);
    LOGGER.info("    虚拟时间后移: " + scheduler.now(TimeUnit.SECONDS));
    scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
    LOGGER.info("    虚拟时间前移: " + scheduler.now(TimeUnit.SECONDS));


  }

}
