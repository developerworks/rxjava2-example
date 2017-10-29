/**
 * Single
 * http://blog.csdn.net/androidxiaogang/article/details/56486034
 */

package io.vertx.schedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

public class Single extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(Single.class);

  @Override
  public void start() throws Exception {
    super.start();

    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        LOGGER.info("执行线程: " + Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
          e.onNext(i);
        }
        e.onComplete();
      }
    });

    Observer<Integer> observer = new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {
        LOGGER.info("接收订阅通知: " + Thread.currentThread().getName());
      }

      @Override
      public void onNext(Integer t) {
        LOGGER.debug("onNext: " + t.toString() + ", 执行线程: " + Thread.currentThread().getName());
      }

      @Override
      public void onError(Throwable e) {
        LOGGER.error(e);
      }

      @Override
      public void onComplete() {
        LOGGER.debug("completed");
      }
    };

    observable
        // .doOnNext( item -> {
        //   LOGGER.info("执行线程: " + Thread.currentThread().getName());
        // })
        // 生产操作从 vert.x-eventloop-thread-${n} 线程调度到RxJava 的计算线程池 RxComputationThreadPool-${n}

        .subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).subscribe(observer);
  }

}
