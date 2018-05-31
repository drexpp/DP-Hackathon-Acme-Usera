
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ExamPaperRepository;
import domain.ExamPaper;


@Component
@Transactional
public class StringToExamPaperConverter implements Converter<String, ExamPaper> {

	@Autowired
	ExamPaperRepository	examPaperRepository;


	@Override
	public ExamPaper convert(final String text) {
		ExamPaper result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.examPaperRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
