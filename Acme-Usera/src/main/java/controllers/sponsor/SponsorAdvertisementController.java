package controllers.sponsor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Advertisement;
import domain.Course;
import domain.Sponsor;
import forms.AdvertisementForm;

import services.AdvertisementService;
import services.CourseService;
import services.SponsorService;

@Controller
@RequestMapping("advertisement/sponsor")
public class SponsorAdvertisementController extends AbstractController {
	
	@Autowired
	AdvertisementService 	advertisementService;

	@Autowired
	CourseService		courseService;
	
	@Autowired
	SponsorService			sponsorService;
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		AdvertisementForm advertisementForm;
		
		advertisementForm = new AdvertisementForm();
		
		result = this.createEditModelAndView(advertisementForm);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdvertisementForm advertisementForm, BindingResult binding){
		ModelAndView result;
		Advertisement advertisement;
		
		advertisement = this.advertisementService.reconstruct(advertisementForm, binding);
		if(binding.hasErrors()){
			result = this.createEditModelAndView(advertisementForm);
		}else{
			try{
				this.advertisementService.save(advertisement);
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = this.createEditModelAndView(advertisementForm, "advertisement.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPlacedAds(){
		ModelAndView result;
		Collection<Advertisement> advertisements;
		Sponsor principal;
		
		principal = this.sponsorService.findByPrincipal();
		
		advertisements = this.advertisementService.findBySponsorId(principal.getId());
		
		result = new ModelAndView("advertisement/list");
		result.addObject("advertisements", advertisements);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(AdvertisementForm advertisementForm) {
		ModelAndView result;
		
		result = this.createEditModelAndView(advertisementForm, null);
		
		return result;
	}

	protected ModelAndView createEditModelAndView(AdvertisementForm advertisementForm, String message) {
		ModelAndView result;
		Collection<Course> courses;

		courses = this.courseService.findAll();
		
		result = new ModelAndView("advertisement/edit");
		result.addObject("advertisementForm", advertisementForm);
		result.addObject("courses", courses);
		result.addObject("message", message);
		
		return result;
	}
}