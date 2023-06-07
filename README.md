# Java Concurrency (java-concurrency)

#### Introduction
In this course, We will look at Java Concurrency. What is Java Concurrency ?.
Concurrency refers to the ability of the program to be executed in overlapping time periods.
CPU can execute billions of instructions at time. How can we effectively utilize single core
CPU to execute programs concurrently.

Concurrency is not parallelism. Here, We will only discuss concurrency and How to make program
concurrent so that It can be run efficiently.

How to make Java program concurrent ?.

Threads are basic building blocks of concurrent programs. Each java process starts with main thread,
Which could create multiple threads. Thread is an independent path of execution in java. When multiple threads are created,
all of them could share time slice of the cpu to provide the illusion that they are running in parallel.

When we have multiple cpu core's, We will see how can we write concurrent programs in java. Java multithreading provides
classes and interfaces to build concurrent java application. Concurrent Java application would definitely improve the
performance when compared to single threaded application.

Understanding the followings are the building blocks to understand how to build concurrent application.

---

#### Basics of Java Threads
- Java Memory Model
- Atomic
- Thread Safety
- Inter Thread Communication
- Immutable

---
#### Atomic classes
- Non-blocking CAS (AtomicInteger, AtomicLong, AtomicBoolean ...etc.)
---

#### Executors
- Executors
- ThreadPool, ThreadPool Configuration
  Runnable, Callable, Future, ExecutorService ..etc
- ForkJoinPool - work stealing algorithm
- Producer Consumer

---
#### High Level Concurrency
- CountDown Latch
- Cyclic Barrier
- Semaphore
- Producer-Consumer
- Exchanger
- Phaser

---

#### Locks
- Lock
- ReentrantLock
- DeadLock

---

#### Collections and Concurrency
- Collections and Concurrency
- Building Custom Synchronizer using AbstractQueuedSynchronizer

#### Problems
- Print Zero Odd Even
- Print Water Molecule (H2O)
- Print CO2 Molecule

#### Practice
- Print FizzBuzz
- Dining Philosophers

---
#### Prerequisites
- Basic understanding of Java Language and It'_s_ syntax.

### How to Build
```

mvn clean package

```

### How to Run
```

java -cp target/java-concurrency-samples-1.0-SNAPSHOT.jar ThreadExample

```

---

#### Summary
We have successfully completed this course, We have seen the building blocks which are used to
build multithreading application. These are the core features to understand java concurrency
and it's related classes.

---

##### References
[https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/package-summary.html](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/package-summary.html)
[https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/package-summary.html](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/package-summary.html)
[https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/atomic/package-summary.html](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/atomic/package-summary.html)
[https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/locks/AbstractQueuedSynchronizer.html#compareAndSetState(int,int)](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/locks/AbstractQueuedSynchronizer.html#compareAndSetState(int,int))

---
