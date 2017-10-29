/**
 * SchedulerWorker
 * http://blog.csdn.net/shangmingchao/article/details/78020756
 */
package io.vertx.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import java.util.concurrent.TimeUnit;

public class SchedulerWorker extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerWorker.class);

  @Override
  public void start() throws Exception {
    super.start();

    // Schedulers.trampoline() 会在当前线程执行
    // 如果要调度到其他线程, 比如Schedulers.io(), Schedulers.computation(), 在这里创建不同的线程即可.
    // Scheduler.Worker worker = Schedulers.trampoline().createWorker();
    Scheduler.Worker worker = Schedulers.io().createWorker();

    Runnable task1 = new Runnable() {
      @Override
      public void run() {
        LOGGER.info("Action 1 is execute on: " + Thread.currentThread().getName());
      }
    };

    Runnable task2 = new Runnable() {
      @Override
      public void run() {
        LOGGER.info("Action 2 is execute on: " + Thread.currentThread().getName());
        // 在任务2中调度任务1
        // 注意: 它只是把任务排队到当前线程的任务队列中, 只有task2完成后才会执行task1
        // 并且我们传递了第二, 第三个参数标识延迟多少实现进行调度
        LOGGER.info("延迟3秒...");
        worker.schedule(task1, 3, TimeUnit.SECONDS);
      }
    };

    worker.schedule(task2);
  }

}
