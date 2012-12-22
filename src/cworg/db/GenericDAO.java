package cworg.db;

import java.io.Serializable;
import java.sql.SQLException;

public interface GenericDAO<T, PK extends Serializable> {
	public PK create(T newObject) throws SQLException;

	public T read(PK id) throws SQLException;

	public void update(T object) throws SQLException;

	public void delete(T object) throws SQLException;
}
