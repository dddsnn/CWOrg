package cworg.beans;

import java.time.Duration;

import javax.ejb.Remote;

import cworg.web.ProlongateResponse;

@Remote
public interface WgAccesssdfg {
	String getLoginUrl(String redirectUrl);// throws WgApiException, WebException;

	ProlongateResponse prolongate(String accessToken, Duration duration);
//			throws WebException, WgApiException;
}
