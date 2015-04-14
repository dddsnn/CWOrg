package cworg.db;

import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class InstantPersistenceConverter implements
		AttributeConverter<Instant, Long> {

	@Override
	public Long convertToDatabaseColumn(Instant instant) {
		return instant.getEpochSecond();
	}

	@Override
	public Instant convertToEntityAttribute(Long l) {
		return Instant.ofEpochSecond(l);
	}

}
