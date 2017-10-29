/**
 * 如何使用 Observable.create 方法实现发布订阅
 * http://blog.csdn.net/maplejaw_/article/details/52442065
 */

package io.vertx.observables;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
// import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.vertx.reactivex.core.AbstractVerticle;

public class Simplfied extends AbstractVerticle {

  private Disposable disposable;

  @Override
  public void start() throws Exception {
    super.start();
    // 创建一个Observable
    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> oe) throws Exception {
        oe.onNext(1);
        oe.onNext(2);
        oe.onComplete();
      }
    });

    //////////////////////////
    // RxJava 2中的简化写法
    //////////////////////////
    disposable = observable.subscribe(new Consumer<Integer>() {
      @Override
      public void accept(Integer integer) throws Exception {
        //这里接收数据项
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
        //这里接收onError
      }
    }, new Action() {
      @Override
      public void run() throws Exception {
        //这里接收onComplete。
      }
    });
  }

  /**
   * @return the disposable
   */
  public Disposable getDisposable() {
    return disposable;
  }

  /**
   * @param disposable the disposable to set
   */
  public void setDisposable(Disposable disposable) {
    this.disposable = disposable;
  }

}
