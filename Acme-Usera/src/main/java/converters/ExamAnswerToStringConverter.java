
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ExamAnswer;


@Component
@Transactional
public class ExamAnswerToStringConverter implements Converter<ExamAnswer, String> {

	@Override
	public String convert(final ExamAnswer examAnswer) {
		String result;

		if (examAnswer == null)
			result = null;
		else
			result = String.valueOf(examAnswer.getId());

		return result;
	}

}
