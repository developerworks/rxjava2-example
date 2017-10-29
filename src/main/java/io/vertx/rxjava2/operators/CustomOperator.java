package io.vertx.rxjava2.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.vertx.reactivex.core.AbstractVerticle;
import com.google.common.collect.ImmutableList;

/**
 * CustomOperator
 */
public class CustomOperator extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();

    // 使用多个操作符
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
      .collect(ImmutableList::builder, ImmutableList.Builder::add)
      .map(ImmutableList.Builder::build)
      .subscribe(System.out::println);
    Observable.range(1,10)
      // 收集Observable产生的数据项到单个数据结构中并返回一个生成该数据结果的Single
      .collect(ImmutableList::builder, ImmutableList.Builder::add)
      .map(ImmutableList.Builder::build)
      .subscribe(System.out::println);

    // 合成操作符
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
      .compose(toImmutableList())
      .subscribe(System.out::println);
    Observable.range(1, 10)
      .compose(toImmutableList())
      .subscribe(System.out::println);
  }

  public static <T> ObservableTransformer<T, ImmutableList<T>> toImmutableList() {
    return upstream ->
      upstream
        .collect(ImmutableList::<T>builder, ImmutableList.Builder::add)
        .map(ImmutableList.Builder::build)
        .toObservable();
  }
}
