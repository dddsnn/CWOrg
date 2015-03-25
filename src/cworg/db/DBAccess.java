package cworg.db;

import javax.ejb.Local;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.PlayerTankInformation;
import cworg.data.Tank;
import cworg.data.User;
import cworg.web.WebException;
import cworg.web.WgApiError;

@Local
public interface DBAccess {
	User findOrCreateUser(long accountId) throws WebException, WgApiError;

	Player findOrCreatePlayer(long accountId) throws WebException, WgApiError;

	Tank findOrGetUpdateForTank(long tankId) throws WebException, WgApiError;

	ClanMemberInformation createClanMemberInfo(Player player)
			throws WebException, WgApiError;

	Clan findOrCreateClan(long clanId) throws WebException, WgApiError;

	PlayerTankInformation createPlayerTankInfo(Player player, long tankId)
			throws WebException, WgApiError;
}
