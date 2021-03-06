package cworg.web;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import cworg.data.Tank;

@Local
public interface WgAccess {
	String getLoginUrl(String redirectUrl) throws WgApiError, WebException;

	ProlongateResponse prolongate(String accessToken, Duration duration)
			throws WebException, WgApiError;

	void logout(String accessToken) throws WebException, WgApiError;

	// TODO specific, just for testing
	List<String> getVehiclesInGarage(long accountId, String accessToken)
			throws WebException, WgApiError;

	Set<Tank> getAllTankInfo() throws WebException, WgApiError;

	GetPlayerResponse getPlayer(long accountId) throws WebException, WgApiError;

	GetClanMemberInfoResponse getClanMemberInfo(long accountId)
			throws WebException, WgApiError;

	GetClanResponse getClan(long clanId) throws WebException, WgApiError;
}
