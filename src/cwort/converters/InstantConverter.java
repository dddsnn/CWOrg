package cwort.converters;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("InstantConverter")
public class InstantConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String s) {
		DateTimeFormatter formatter = makeFormatter(comp);
		Instant instant = null;
		try {
			instant = formatter.parse(s, Instant::from);
		} catch (DateTimeParseException e) {
			throw new ConverterException(e);
		}
		return instant;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object o) {
		DateTimeFormatter formatter = makeFormatter(comp);
		String res = null;
		try {
			res = formatter.format((Instant) o);
		} catch (DateTimeException e) {
			throw new ConverterException(e);
		}
		return res;
	}

	private static DateTimeFormatter makeFormatter(UIComponent comp) {
		String pattern = (String) comp.getAttributes().get("pattern");
		DateTimeFormatter formatter;
		// TODO configurable zone
		if (pattern == null) {
			formatter =
					DateTimeFormatter.ISO_INSTANT.withZone(ZoneId
							.systemDefault());
		} else {
			formatter =
					DateTimeFormatter.ofPattern(pattern).withZone(
							ZoneId.systemDefault());
		}
		return formatter;
	}
}
