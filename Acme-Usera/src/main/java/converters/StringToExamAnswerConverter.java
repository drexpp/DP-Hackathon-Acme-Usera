
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ExamAnswerRepository;
import domain.ExamAnswer;


@Component
@Transactional
public class StringToExamAnswerConverter implements Converter<String, ExamAnswer> {

	@Autowired
	ExamAnswerRepository	examAnswerRepository;


	@Override
	public ExamAnswer convert(final String text) {
		ExamAnswer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.examAnswerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
