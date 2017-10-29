package io.vertx.observables;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * FromArray
 */
public class FromCallable extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(FromCallable.class);

  @Override
  public void start() throws Exception {
    super.start();

    // 数据源

    // fromCallable

    Observable<Integer[]> observable = Observable.fromCallable(new Callable<Integer[]>() {
      private Integer[] items = new Integer[] { 1, 2, 3 };

      public Integer[] getItems() {
        return items;
      }

      @Override
      public Integer[] call() throws Exception {
        return this.getItems();
      }
    });

    // 绑定, 订阅, 监听, 观察

    Observer<Integer[]> observer = new Observer<Integer[]>() {
      @Override
      public void onSubscribe(Disposable d) {
        logger.info("on subscribe");
      }

      @Override
      public void onNext(Integer[] t) {
        logger.info("on next item: " + t);
        // 使用索引
        for (int i = 0; i < t.length; i++) {
          logger.info("integer: " + t[i]);
        }
        // 不使用索引
        for (int i : t) {
          logger.info("integer >>> " + i);
        }
      }

      @Override
      public void onError(Throwable e) {
        logger.error("on error: " + e);
      }

      @Override
      public void onComplete() {
        logger.info("on complete");
      }
    };

    observable.subscribe(observer);

  }
}
