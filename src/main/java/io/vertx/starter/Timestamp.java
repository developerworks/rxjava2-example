package io.vertx.starter;

import java.util.concurrent.TimeUnit;
import io.vertx.reactivex.core.AbstractVerticle;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Timestamp extends AbstractVerticle {
  public String result;
  @Override
  public void start() throws Exception {
    super.start();
    Observable.range(1, 3)
      // Timestamp 操作符附加到每个从源Observable生成每个数据项当中
      .timestamp()
      .map(o -> o.time(TimeUnit.SECONDS))
      // .last("default")
      .observeOn(Schedulers.io())
      .subscribe( item -> {
        System.out.println("Received " + item + " on thread " + Thread.currentThread().getName());
      });
  }
}
