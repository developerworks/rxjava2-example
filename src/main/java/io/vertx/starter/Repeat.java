package io.vertx.starter;

import io.vertx.reactivex.core.AbstractVerticle;

import io.reactivex.Observable;

public class Repeat extends AbstractVerticle {

  public int receivedTotal;

  @Override
  public void start() throws Exception {
    super.start();
    Observable.range(1, 3)
        // Repeat simply intercepts completion notification from upstream and rather than passing it downstream it resubscribes.
      .repeat(3)
      .subscribe(i -> {
        receivedTotal += i;
        System.out.println("%%%%%%%%% item " + i);
      });
  }

}
