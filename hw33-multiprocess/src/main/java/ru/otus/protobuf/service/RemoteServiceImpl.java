package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.MessageRequest;
import ru.otus.protobuf.generated.MessageResponse;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        int first = request.getFirst();
        int last = request.getLast();
        for (int i = first; i < last; i++) {
            responseObserver.onNext(MessageResponse.newBuilder().setVal(i).build());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }

}
