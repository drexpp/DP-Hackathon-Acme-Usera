
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Course;


@Component
@Transactional
public class CourseToStringConverter implements Converter<Course, String> {

	@Override
	public String convert(final Course course) {
		String result;

		if (course == null)
			result = null;
		else
			result = String.valueOf(course.getId());

		return result;
	}

}
