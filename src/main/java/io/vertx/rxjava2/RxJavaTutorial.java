package io.vertx.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * ObservableDemo
 * 给初学者的RxJava2.0教程(一)
 * http://www.jianshu.com/p/464fa025229e
 */
public class RxJavaTutorial extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(RxJavaTutorial.class);
  private static final String TAG = "4166-4166/zlc.season.rxjava2demo D/TAG-";

  @Override
  public void start() throws Exception {
    super.start();
    logger.debug("Starting verticle: " + RxJavaTutorial.class);

    //创建一个上游 Observable：
    // Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
    //   @Override
    //   public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
    //     emitter.onNext(1);
    //     emitter.onNext(2);
    //     emitter.onNext(3);
    //     emitter.onComplete();
    //   }
    // });
    // //创建一个下游 Observer
    // Observer<Integer> observer = new Observer<Integer>() {
    //   @Override
    //   public void onSubscribe(Disposable d) {
    //     logger.info(TAG, "subscribe");
    //   }

    //   @Override
    //   public void onNext(Integer value) {
    //     logger.info(TAG, "" + value);
    //   }

    //   @Override
    //   public void onError(Throwable e) {
    //     logger.info(TAG, "error");
    //   }

    //   @Override
    //   public void onComplete() {
    //     logger.info(TAG, "complete");
    //   }
    // };
    // //建立连接
    // observable.subscribe(observer);

    ////////////////////////
    //      流式调用
    ////////////////////////
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        emitter.onNext(1);
        emitter.onNext(2);
        emitter.onNext(3);
        emitter.onComplete();
      }
    }).subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {
        logger.debug(TAG, "subscribe");
      }

      @Override
      public void onNext(Integer value) {
        logger.debug(TAG, "" + value);
      }

      @Override
      public void onError(Throwable e) {
        logger.debug(TAG, "error");
      }

      @Override
      public void onComplete() {
        logger.debug(TAG, "complete");
      }
    });

  }
}
