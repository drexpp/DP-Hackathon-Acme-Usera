package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	// Services

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


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
		

		folders = (ArrayList<Folder>) this.folderService.findAllByPrincipal();
		currentFolder =   this.folderService.findOne(folders.get(0).getId());
		messages = currentFolder.getMessages();
		
		
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("currentFolder", currentFolder);
		result.addObject("messages", messages);
		

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

			try{
			currentFolder = this.folderService.findOne(folderId);
			messages = currentFolder.getMessages();
			folders = this.folderService.findAllByPrincipal();


			result = new ModelAndView("folder/list");
			result.addObject("folders", folders);
			result.addObject("currentFolder", currentFolder);
			result.addObject("messages", messages);

			} catch (Throwable oops){
				result = new ModelAndView("redirect:/folder/actor/list.do");
			}
			return result;

		}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String messageCode) {
		ModelAndView result;
		Collection<MailMessage> messages;
		String name;
		Actor principal;
		boolean permission = false;

		principal = this.actorService.findByPrincipal();

		if (folder.getId() == 0)
			permission = true;
		else
			for (final Folder fol : principal.getFolders())
				if (fol.getId() == folder.getId()) {
					permission = true;
					break;
				}

		messages = folder.getMessages();

		if (folder.getName() == null)
			name = null;
		else
			name = folder.getName();

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("name", name);
		result.addObject("messages", messages);
		result.addObject("permission", permission);

		result.addObject("message", messageCode);

		return result;

	}
}