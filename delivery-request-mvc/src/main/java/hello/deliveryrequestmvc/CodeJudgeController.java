package hello.deliveryrequestmvc;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CodeJudgeController {
	private final CodeExecution codeExecution;

	@PostMapping(
		value = "/code",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<String> executeCode(@RequestBody String code) {
		String id = UUID.randomUUID().toString();
		codeExecution.execute(id, code);
		return ResponseEntity.ok(id);
	}

	@GetMapping(
		value = "/code",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Boolean> getResult(@RequestParam String requestId) {
		return ResponseEntity.ok(codeExecution.findResult(requestId));
	}
}
