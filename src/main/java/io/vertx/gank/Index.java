/**
 * Index
 * http://gank.io/post/560e15be2dca930e00da1083
 *
 * 在 RxJava 的默认规则中，事件的发出和消费都是在同一个线程的。也就是说，如果只用上面的方法，实现出来的只是一个同步的观察者模式。
 * 观察者模式本身的目的就是『后台处理，前台回调』的异步机制，因此异步对于 RxJava 是至关重要的。
 * 而要实现异步，则需要用到 RxJava 的另一个概念： Scheduler 。
 *
 *
 * Schedulers.immediate():
 *    直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
 * Schedulers.newThread():
 *    总是启用新线程，并在新线程执行操作。
 * Schedulers.io():
 *    I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
 *    区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
 *    不要把计算工作放在 io() 中，可以避免创建不必要的线程。
 * Schedulers.computation():
 *    计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
 *    这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
 *
 * 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
 */

package io.vertx.gank;


import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;


public class Index extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(Index.class);

  @Override
  public void start() throws Exception {
    super.start();
    LOGGER.trace("Current thread: " + Thread.currentThread().getName());
  }

}
