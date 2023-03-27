package hello.deliveryrequestwebflux;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
public class CodeJudgeController {
	private final CodeExecution codeExecution;

	@PostMapping(
		value = "/code",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Mono<Boolean> executeCode(@RequestBody String code) {
		String id = UUID.randomUUID().toString();
		return Mono.fromCallable(() -> codeExecution.execute(id, code))
			.subscribeOn(Schedulers.boundedElastic());
	}
}
