
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ExamPaper;


@Component
@Transactional
public class ExamPaperToStringConverter implements Converter<ExamPaper, String> {

	@Override
	public String convert(final ExamPaper examPaper) {
		String result;

		if (examPaper == null)
			result = null;
		else
			result = String.valueOf(examPaper.getId());

		return result;
	}

}
