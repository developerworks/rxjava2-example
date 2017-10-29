package io.vertx.starter;

import io.vertx.reactivex.core.AbstractVerticle;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ObserveOn extends AbstractVerticle {

  public int emittedTotal;
  public int receivedTotal;

  @Override
  public void start() throws Exception {
    super.start();
    Observable.range(1, 5)
    .map(i -> i * 100)
    .doOnNext(i -> {
      emittedTotal += i;
      System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName());
    })
    .observeOn(Schedulers.computation())
    .map(i -> i * 10)
    .subscribe(i -> {
      receivedTotal += i;
      System.out.println("Received " + i + " on thread " + Thread.currentThread().getName());
    });
  }
}
