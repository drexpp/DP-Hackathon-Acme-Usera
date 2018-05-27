
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ExamQuestionRepository;
import domain.ExamQuestion;


@Component
@Transactional
public class StringToExamQuestionConverter implements Converter<String, ExamQuestion> {

	@Autowired
	ExamQuestionRepository	examQuestionRepository;


	@Override
	public ExamQuestion convert(final String text) {
		ExamQuestion result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.examQuestionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
