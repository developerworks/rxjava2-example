/**
 * When an observer subscribes to the Observable returned from the using(),
 * it’ll use the Observable factory function to create the Observable the observer will… observe,
 * while at the same time using the resource factory function to create whichever resource we’ve designed it to make.
 * When the observer unsubscribes from the Observable, or when the Observable terminates,
 * using will call the third function to dispose of the created resource:
 */

/**
 * 当一个订阅者订阅到通过 using() 返回的 Observable, 他将会使用 Observable 工厂函数创建 Observable, 并且进行订阅.
 *
 * 同时, 使用资源工厂函数创建
 *
 * 当订阅者取消对 Observable 订阅, 或者 Observable 终止. **using** 会调用第三个函数处理先前创建的资源.
 */

package io.vertx.starter;

import io.vertx.reactivex.core.AbstractVerticle;
import io.reactivex.Observable;

/**
  * Using
  */
public class Using extends AbstractVerticle {
  public String result = "";

  @Override
  public void start() throws Exception {
    super.start();
    Observable<Character> values = Observable.using(
        // 第一个参数: 依赖于ObservableSource的创建资源的工厂函数
        () -> "resource",
        // 第二个参数: 创建 ObservableSource 的工厂函数
        r -> {
          return Observable.create(o -> {
            for (Character c : r.toCharArray()) {
              o.onNext(c);
            }
            o.onComplete();
          });
        },
        // 第三个参数: 资源处理函数
        r -> System.out.println("Disposed: " + r));
    values.subscribe(v -> result += v, e -> result += e);
  }
}
