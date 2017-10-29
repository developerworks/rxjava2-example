package io.vertx.starter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * Delay
 */
public class Delay extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();

    // 每一秒生成一个数据项
    Observable<Timed<Long>> source = Observable.interval(1000, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .doOnNext( item -> {
          System.out.println("\n------------------------------------------------------------------------\n" + item + " 执行线程: " + Thread.currentThread().getName());
        })
        // 返回一个新的只生成5个数据项的 Observable
        .take(3)
        .observeOn(Schedulers.io())
        .timestamp();

    source.subscribe(
      // onNext
      value -> {
        System.out.println("获取值: " + value +", 执行线程 : " + Thread.currentThread().getName());
      },
      // onError
      t -> System.out.println("delay error"),
      // onComplete
      () -> System.out.println(">>> 数据处理完成!")
    );

    // 组合两个Observable, 延迟2秒, 从上游Observable获取数据项
    // Observable<Timed<Long>> delayedObservable = source.delay(200, TimeUnit.MILLISECONDS);
    // delayedObservable.subscribeOn(Schedulers.io());

    // delayedObservable.subscribe(
    //   // onNext
    //   value -> {
    //     System.out.println("获取值: " + value +", 执行线程 : " + Thread.currentThread().getName());
    //   },
    //   // onError
    //   t -> System.out.println("delay error"),
    //   // onComplete
    //   () -> System.out.println("delay completed")
    // );
  }

}
