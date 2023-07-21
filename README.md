# Java Concurrency (A Guide to Understand Java Concurrency and Multithreading)

This repository contains source code for my udemy course
[https://www.udemy.com/course/a-guide-to-java-concurrency/?kw=A+Guide+to+Java&src=sac](https://www.udemy.com/course/a-guide-to-java-concurrency/?kw=A+Guide+to+Java&src=sac).

#### Synopsis
This is a course on Java concurrency and multithreading. It explains about the concepts that are required to build java
concurrent application. This repository contains source code for the course, and you can clone the repository and run the 
examples. This course explains the following concepts such as understanding java memory model, immutability, classes(AtomicInteger, AtomicBoolean, AtomicLong..etc) that
provides atomicity by implementing non-blocking algorithms. It covers executors which runs multiple threads to parallelize the execution of java application.
This course explains about the concepts of high level concurrency, locks and how it affects the throughput of collections.
It also contains exercise problems to practice the knowledge of this course.


#### Target Audience
If you already know java and wants to get good grasp on java concurrency. If you are working on a project which requires
the knowledge of understanding the concepts of multithreading.

#### Prerequisites
You should already know java and it's syntax.
you should be familiar with java8 syntax.

---

#### Table of contents

> Basics of Java Threads

1. [Java_Memory_Model](#Java_Memory_Model)

2. [Atomic](#Atomic)

3. [Thread_Safety](#Thread_Safety)

5. [Inter_Thread_Communication](#Inter_Thread_Communication)

6. [Immutable](#Immutable)


> Atomic classes

1. [Non_blocking_CAS](#Non_blocking_CAS)


> Executors 

1. [Executors](Executors)

2. [ThreadPools](ThreadPools)

3. [Runnable_Callable_Future_ExecutorService](#Runnable_Callable_Future_ExecutorService)

5. [ForkJoinPool](#ForkJoinPool)

6. [Producer_Consumer](#Producer_Consumer)


> High Level Concurrency

1. [CountDownLatch](CountDownLatch)
2. [CyclicBarrier](CyclicBarrier)
3. [Semaphore](Semaphore)
4. [Exchanger](Exchanger)
5. [Phaser](Phaser)

> Locks

1. [Lock](Lock)
2. [ReentrantLock](ReentrantLock)
3. [DeadLock](DeadLock)

> Collections and Concurrency

1. [Collections_and_Concurrency](Collections_and_Concurrency)
2. [AbstractQueuedSynchronizer](AbstractQueuedSynchronizer)


> Problems

1. [Print_Zero_Odd_Even](Print_Zero_Odd_Even)
2. [Print_Water_Molecule_(H2O)](Print_Water_Molecule)
3. [Print_CO2_Molecule](Print_CO2_Molecule)

> Practice

#### Prerequisites
Basic understanding of Java Language and It'_s_ syntax.

#### How to Build

```
git clone https://github.com/vetriselvan1187/java-concurrency-examples.git

cd java-concurrency-examples

mvn clean package

```

#### How to Run

```

java -cp target/java-concurrency-samples-1.0-SNAPSHOT.jar ThreadExample

```

---

`>>>`  A Guide to Java Concurrency and Multithreading

`...`  course by Vetriselvan

![](https://i.creativecommons.org/l/by-sa/4.0/88x31.png). This work is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/)
