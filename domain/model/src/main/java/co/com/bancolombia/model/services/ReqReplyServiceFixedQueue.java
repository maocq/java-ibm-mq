package co.com.bancolombia.model.services;

import reactor.core.publisher.Mono;

public interface ReqReplyServiceFixedQueue {

    Mono<String> requestReply(String message);
}
