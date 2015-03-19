package cworg.web;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class RetryInterceptor {
	@AroundInvoke
	public Object retryOnRequestLimitExceeded(InvocationContext ic)
			throws Exception {
		Object result = null;
		// TODO put wait time and max tries into servlet context
		int count = 0;
		int maxTries = 10;
		while (true) {
			try {
				result = ic.proceed();
				break;
			} catch (WgApiError e) {
				if ("REQUEST_LIMIT_EXCEEDED".equalsIgnoreCase(e.getMessage())) {
					if (count == maxTries) {
						throw e;
					}
					count++;
					// wait a bit before we try again
					Thread.sleep(500);
				}
			}
		}
		return result;
	}
}
