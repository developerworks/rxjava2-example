package io.vertx.observables;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.BackpressureStrategy;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * FlowableDemo
 */
public class FlowableDemo extends AbstractVerticle {
  protected static final String Log = null;

  @Override
  public void start() throws Exception {
    super.start();
    Flowable.create(new FlowableOnSubscribe<Integer>() {
      // 实现 FlowableOnSubscribe 接口的订阅方法, 标识当订阅订阅到 Flowable 要做的事情
      // 要做的事情很简单: 就是生成1..10000的数字, 然后结束 onComplete()
      @Override
      public void subscribe(FlowableEmitter<Integer> e) throws Exception {
        for (int i = 0; i < 10000; i++) {
          e.onNext(i);
        }
        e.onComplete();
      }
    }, BackpressureStrategy.MISSING)
        // 指定背压处理策略，抛出异常
        .subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {
            // Log.d("JG", integer.toString());
            Thread.sleep(1000);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            // Log.d("JG", throwable.toString());
          }
        });

  }
}
