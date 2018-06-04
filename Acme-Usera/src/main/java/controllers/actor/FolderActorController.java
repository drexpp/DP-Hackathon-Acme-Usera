package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import controllers.AbstractController;
import domain.Folder;
import domain.MailMessage;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	// Services

	@Autowired
	private FolderService	folderService;



	// Constructors

	public FolderActorController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final ArrayList<Folder> folders;
		Folder currentFolder;
		Collection<MailMessage> messages;
		Integer currentFolderId;
		

		folders = (ArrayList<Folder>) this.folderService.findAllByPrincipal();
		currentFolder =   this.folderService.findOne(folders.get(0).getId());
		messages = currentFolder.getMessages();
		
		currentFolderId = findFolderId(currentFolder);
		
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("currentFolder", currentFolder);
		result.addObject("currentFolderId", currentFolderId);
		result.addObject("messages", messages);
		

		return result;
	}
	
	protected Integer findFolderId(Folder currentFolder) {
		Integer result;
		
		if(currentFolder.getName().contains("in"))
			result = 0;
		else if(currentFolder.getName().contains("out"))
			result = 1;
		else
			result = 2;
		
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
			"folderId"
		})
		public ModelAndView list(@RequestParam final int folderId) {
			ModelAndView result;
			Folder currentFolder;
			Collection<MailMessage> messages;
			final Collection<Folder> folders;
			Integer currentFolderId;

			try{
			currentFolder = this.folderService.findOne(folderId);
			messages = currentFolder.getMessages();
			folders = this.folderService.findAllByPrincipal();

			currentFolderId = findFolderId(currentFolder);
			
			result = new ModelAndView("folder/list");
			result.addObject("folders", folders);
			result.addObject("currentFolder", currentFolder);
			result.addObject("currentFolderId", currentFolderId);
			result.addObject("messages", messages);

			} catch (Throwable oops){
				result = new ModelAndView("redirect:/folder/actor/list.do");
			}
			return result;

		}
}