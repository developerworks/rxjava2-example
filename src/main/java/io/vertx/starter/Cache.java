package io.vertx.starter;

import io.vertx.reactivex.core.AbstractVerticle;
import io.reactivex.Observable;

/**
 * 缓存操作符, 当第一个订阅者订阅上游的 Observable, 通过Cache操作符, 第一个数据项的结果被缓存.
 * 第二个订阅来获取数据项的时候, 直接从缓存中返回给 Subscriber, 而不再从底层(上游)的 Observable
 * 获取数据项.
 */
public class Cache extends AbstractVerticle {
  public int receivedTotal = 0;

  @Override
  public void start() throws Exception {
    super.start();
    Observable<Integer> source = Observable.<Integer>create(subscriber -> {
      System.out.println("Create");
      subscriber.onNext(receivedTotal += 5);
      subscriber.onComplete();
    }).cache();

    // 由于被缓存了, 下面两次获取到的变量 i 都是 5
    source.subscribe(i -> {
      System.out.println(i);
      receivedTotal += 1;
      System.out.println("element 1: " + receivedTotal);

    });
    source.subscribe(i -> {
      System.out.println(i);
      receivedTotal += 2;
      System.out.println("element 2: " + receivedTotal);

    });
  }

}
