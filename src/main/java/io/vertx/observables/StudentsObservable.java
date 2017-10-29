package io.vertx.observables;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/**
 * StudentsObservable
 *
 * http://gank.io/post/560e15be2dca930e00da1083
 */
public class StudentsObservable extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(StudentsObservable.class);

  class Course {

    private String name;

    /**
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
      this.name = name;
    }

  }

  class Student {

    public List<Course> getCourses() {
      return null;
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    ArrayList<Student> students = null;
    students.add(0, new Student());

    Subscriber<Student> subscriber = new Subscriber<Student>() {
      @Override
      public void onSubscribe(Subscription s) {

      }

      @Override
      public void onNext(Student student) {
        List<Course> courses = student.getCourses();
        for (int i = 0; i < courses.size(); i++) {
          Course course = courses.get(i);
          LOGGER.debug(course.getName());
        }
      }

      @Override
      public void onComplete() {

      }

      @Override
      public void onError(Throwable t) {

      }
    };
    // Subject.just(students).subscribe(subscriber);
  }
}
