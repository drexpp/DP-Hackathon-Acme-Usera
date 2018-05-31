package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Course;
import domain.Forum;
import domain.Question;
import domain.Teacher;

import repositories.ForumRepository;

@Service
@Transactional
public class ForumService {

	// Managed Repository
		@Autowired
		private ForumRepository			forumRepository;
		
		@Autowired
		private TeacherService			teacherService;
		
		@Autowired
		private AdminService			adminService;
		@Autowired
		private QuestionService			questionService;


	
		public Forum create() {
			Teacher principal;
			Forum forum = new Forum();
			principal = this.teacherService.findByPrincipal();
			Assert.notNull(principal);
			
			forum.setQuestions(new ArrayList<Question>());
			
			return forum;
		}
		
		
		public Collection<Forum> findAll() {
			final Collection<Forum> result = this.forumRepository.findAll();
		
			return result;
		}
		
		public Forum findOne(final int forumId) {


			Forum result = this.forumRepository.findOne(forumId);
			Assert.notNull(result);

			return result;

		}
		
		public Forum save(final Forum forumToSave) {
			Teacher principal;
			Forum result;
			Assert.notNull(forumToSave);
			principal = this.teacherService.findByPrincipal();

			Assert.notNull(principal);
			Assert.isTrue(forumToSave.getCourse().getCreator().equals(principal));
			
			result = this.forumRepository.save(forumToSave);
			
			Course course = result.getCourse();
			course.setForum(result);
			
			return result;
		}
			
		public Forum delete(final Forum forumToDelete) {
			Teacher principal;
			Forum result;
			Assert.notNull(forumToDelete);
			principal = this.teacherService.findByPrincipal();

			Assert.notNull(principal);
			Assert.isTrue(forumToDelete.getCourse().getCreator().equals(principal));
			
			result = this.forumRepository.save(forumToDelete);
			
			Course course = result.getCourse();
			course.setForum(result);
			
			return result;
		}	
		
		public void deleteByAdmin(final Forum forumToDelete) {
			Admin principal;
			Assert.notNull(forumToDelete);
			principal = this.adminService.findByPrincipal();

			Assert.notNull(principal);
			
			Course course = forumToDelete.getCourse();
			course.setForum(null);
			
			Collection<Question> questionsToDelete = new ArrayList<Question>(forumToDelete.getQuestions());
			for (Question q : questionsToDelete){
				this.questionService.deleteByAdmin(q);
			}
			
			this.forumRepository.delete(forumToDelete);
	
		}
			
			
			
			
			
			
		
}
