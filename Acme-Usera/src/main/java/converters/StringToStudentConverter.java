package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.StudentRepository;
import domain.Student;


@Component
@Transactional
public class StringToStudentConverter implements Converter<String, Student> {

	@Autowired
	StudentRepository	StudentRepository;


	@Override
	public Student convert(final String text) {
		Student result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.StudentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
