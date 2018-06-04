
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Teacher;

@Component
@Transactional
public class TeacherToStringConverter implements Converter<Teacher, String> {

	@Override
	public String convert(final Teacher ranger) {
		String result;

		if (ranger == null)
			result = null;
		else
			result = String.valueOf(ranger.getId());

		return result;
	}

}
