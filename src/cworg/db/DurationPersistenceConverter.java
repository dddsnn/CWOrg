package cworg.db;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DurationPersistenceConverter implements
		AttributeConverter<Duration, Long> {

	@Override
	public Long convertToDatabaseColumn(Duration duration) {
		return duration.getSeconds();
	}

	@Override
	public Duration convertToEntityAttribute(Long l) {
		return Duration.ofSeconds(l);
	}

}
