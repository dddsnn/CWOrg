package cworg.db;

import javax.ejb.Local;

import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.Tank;
import cworg.data.User;

@Local
public interface DBAccess {
	User findOrCreateUser(String accountId);

	Player findOrCreatePlayer(String accountId);

	ClanMemberInformation findOrCreateClanMemberInfo(String clanId,
			String accountId);

	Tank findOrGetUpdateForTank(String tankId);
}
