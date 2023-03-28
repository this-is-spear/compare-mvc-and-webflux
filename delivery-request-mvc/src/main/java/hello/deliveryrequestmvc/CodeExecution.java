package hello.deliveryrequestmvc;

import java.util.HashSet;
import java.util.Set;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CodeExecution {

	private final Set<String> tmpStoredCodeResult = new HashSet<>();

	@Async
	@SneakyThrows
	public void execute(String requestId, String code) {
		log.info("Start execution request id is {}", requestId);

		// 파일에 작성된다고 가정해서 print를 찍는다.
		System.out.println(code);

		// 코드가 실행되는 시간을 가정한다.
		Thread.sleep(1_500);

		tmpStoredCodeResult.add(requestId);
		log.info("End execution request id is {}", requestId);
	}

	public boolean findResult(String requestId) {
		if (tmpStoredCodeResult.contains(requestId)) {
			return tmpStoredCodeResult.remove(requestId);
		}
		return false;
	}
}
