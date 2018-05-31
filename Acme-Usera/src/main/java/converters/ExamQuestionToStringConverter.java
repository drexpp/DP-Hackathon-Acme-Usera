
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ExamQuestion;


@Component
@Transactional
public class ExamQuestionToStringConverter implements Converter<ExamQuestion, String> {

	@Override
	public String convert(final ExamQuestion examQuestion) {
		String result;

		if (examQuestion == null)
			result = null;
		else
			result = String.valueOf(examQuestion.getId());

		return result;
	}

}
