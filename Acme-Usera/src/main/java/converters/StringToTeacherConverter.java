
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TeacherRepository;
import domain.Teacher;


@Component
@Transactional
public class StringToTeacherConverter implements Converter<String, Teacher> {

	@Autowired
	TeacherRepository	teacherRepository;


	@Override
	public Teacher convert(final String text) {
		Teacher result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.teacherRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
