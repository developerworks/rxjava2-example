package io.vertx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * MyDefaultObserver
 */
public class MyFactory {

  private static final Logger logger = LoggerFactory.getLogger(MyFactory.class);

  public static <T> Observer<T> getDefaultObserver() {
    // 绑定, 订阅, 监听, 观察
    Observer<T> observer = new Observer<T>() {
      @Override
      public void onSubscribe(Disposable d) {
        logger.info("on subscribe");
      }

      @Override
      public void onNext(T t) {
        logger.info("on next item: " + t);
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

    return observer;
  }
}
