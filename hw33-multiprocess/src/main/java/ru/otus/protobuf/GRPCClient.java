package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.MessageRequest;
import ru.otus.protobuf.generated.MessageResponse;


import java.util.ArrayDeque;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


public class GRPCClient {

    private static final Logger log = LoggerFactory.getLogger(GRPCClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var counter = new AtomicInteger();
        var currentValue = 0;
        var oldValue = 0;

        var latch = new CountDownLatch(1);
        var newStub = RemoteServiceGrpc.newStub(channel);
        newStub.sendMessage(MessageRequest.newBuilder().setFirst(0).setLast(30).build(), new StreamObserver<MessageResponse>() {
            @Override
            public void onNext(MessageResponse response) {
                synchronized (lock) {
                    counter.set(response.getVal());
                    log.info("new value:{}", response.getVal());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                log.info("request completed");
                latch.countDown();
            }
        });

        for (int i = 0; i < 50; i++) {
            synchronized (lock) {
                var serverVal = counter.get();
                var result = oldValue == serverVal ? 0 : serverVal;
                oldValue = serverVal;
                currentValue += result + 1;
                log.info("currentValue:{}", currentValue);
            }
            sleep();
        }

        latch.await();

        channel.shutdown();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
