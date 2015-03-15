package cworg.web;

import java.time.Duration;

import javax.ejb.Remote;

@Remote
public interface WgAccess {
	String getLoginUrl(String redirectUrl) throws WgApiException, WebException;

	ProlongateResponse prolongate(String accessToken, Duration duration)
			throws WebException, WgApiException;
}
