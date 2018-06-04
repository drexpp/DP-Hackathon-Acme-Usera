
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Student;

@Component
@Transactional
public class StudentToStringConverter implements Converter<Student, String> {

	@Override
	public String convert(final Student ranger) {
		String result;

		if (ranger == null)
			result = null;
		else
			result = String.valueOf(ranger.getId());

		return result;
	}

}
