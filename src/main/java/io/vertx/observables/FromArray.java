package io.vertx.observables;

import io.reactivex.Observable;

import io.reactivex.Observer;
// import io.reactivex.disposables.Disposable;
import io.vertx.MyFactory;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * FromArray
 */
public class FromArray extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(FromArray.class);

  @Override
  public void start() throws Exception {
    super.start();

    // 数据源
    Integer[] items = new Integer[] { 1, 2, 3 };

    // fromArray
    Observable<Integer> observable = Observable.fromArray(items);

    // 绑定, 订阅, 监听, 观察

    Observer<Integer> observer = MyFactory.getDefaultObserver();
    observable.subscribe(observer);

  }
}
