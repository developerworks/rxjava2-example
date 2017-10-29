package io.vertx.starter;
import io.vertx.core.DeploymentOptions;
import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    DeploymentOptions options = new DeploymentOptions().setInstances(1);

    // vertx.deployVerticle("io.vertx.starter.ObserveOn", options);
    // vertx.deployVerticle("io.vertx.starter.Timestamp", options);
    // vertx.deployVerticle("io.vertx.starter.Delay", options);
    // vertx.deployVerticle("io.vertx.starter.Repeat", options);
    // vertx.deployVerticle("io.vertx.starter.Cache", options);
    // vertx.deployVerticle("io.vertx.starter.Using", options);
    // vertx.deployVerticle("io.vertx.rxjava2.operators.CustomOperator", options);
    // vertx.deployVerticle("io.vertx.gank.Index", options);
    // vertx.deployVerticle("io.vertx.schedulers.Single", options);
    // vertx.deployVerticle("io.vertx.schedulers.SchedulerWorker", options);
    // vertx.deployVerticle("io.vertx.schedulers.TestSchedulerDemo", options);
    // vertx.deployVerticle("io.vertx.rxjava2.RxJavaTutorial", options);
    // vertx.deployVerticle("io.vertx.databases.Db", options);
    // vertx.deployVerticle("io.vertx.databases.Streaming", options);
    // vertx.deployVerticle("io.vertx.observables.FromArray", options);
    vertx.deployVerticle("io.vertx.observables.FromCallable", options);
  }
}
