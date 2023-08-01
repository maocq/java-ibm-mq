package co.com.bancolombia.api;

import co.com.bancolombia.model.services.ReqReplyServiceFixedQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ReqReplyServiceFixedQueue reqReplyServiceFixedQueue;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("Hello");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        return reqReplyServiceFixedQueue.requestReply("Hello")
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
