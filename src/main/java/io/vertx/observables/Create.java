/**
 * 如何使用 Observable.create 方法实现发布订阅
 * http://blog.csdn.net/maplejaw_/article/details/52442065
 */

package io.vertx.observables;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
// import io.reactivex.subscribers.DefaultSubscriber;
// import io.reactivex.subscribers.ResourceSubscriber;
// import io.reactivex.subscribers.TestSubscriber;
import io.vertx.reactivex.core.AbstractVerticle;

public class Create extends AbstractVerticle {
  private Disposable disposable;
  private Subscription subscription;

  @Override
  public void start() throws Exception {

    super.start();

    // Subscriber<String> s = new Subscriber<String>() {

    //   @Override
    //   public void onSubscribe(Subscription s) {
    //     s.request(2);
    //     // s.cancel();
    //     subscription = s;
    //   }

    //   @Override
    //   public void onNext(String t) {
    //   }

    //   @Override
    //   public void onError(Throwable t) {

    //   }

    //   @Override
    //   public void onComplete() {

    //   }
    // };

    // Observable.fromArray(items)

    // 传递一个 ObservableOnSubscribe 内部类实例, 创建一个Observable
    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
      /**
       * 在这个方法中定义如何生成数据项,
       * 比如数据库查询, 读取文件, 网络请求等操作
       *
       * 这些阻塞操作应该放到调度去的IO线程中
       */
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onNext(4);
        e.onComplete();
      }
    });
    // 创建观察者
    Observer<Integer> observer = new Observer<Integer>() {
      /**
       * Disposable 相当于RxJava1.x中的Subscription,用于解除订阅
       */
      @Override
      public void onSubscribe(Disposable d) {
        // 复制成员变量
        disposable = d;
      }

      @Override
      public void onNext(Integer value) {
        // 通过全局变量 Disposable 可以实现特定条件下解除订阅
        if (value > 3) {
          disposable.dispose();
        }
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
      }

    };

    // 建立订阅关系
    observable
        // 网络请求, 文件读写, 数据库查询等放到IO线程
        .subscribeOn(Schedulers.io())
        // 建立订阅关系
        .observeOn(Schedulers.trampoline()).subscribe(observer);

  }

}
