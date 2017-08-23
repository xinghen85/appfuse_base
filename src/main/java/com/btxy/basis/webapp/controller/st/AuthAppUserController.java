package com.btxy.basis.webapp.controller.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.common.model.ServerValidataResult;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.service.st.AuthAppUserManager;
import com.btxy.basis.webapp.controller.BaseFormController;
import com.btxy.basis.webapp.controller.st.AuthAppRoleController.KeyValue;
import com.mongodb.MongoException;

@Controller
public class AuthAppUserController extends BaseFormController {
	private static final String DM_FORM_NAME = "authAppUser";
	private static final String LIST_NAME="base/st/AuthAppUserList";
	private static final String FORM_NAME="base/st/AuthAppUserForm";

	@Autowired
	private AuthAppUserManager dbManager;
	
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/list/php*")
	public ModelAndView listFirst(@PathVariable String libraryPath, HttpServletRequest request, final SearchConditionValue searchValue) {
		return list(libraryPath, request, searchValue, "mt");
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/list/{listFlag}/php*")
	public ModelAndView list(@PathVariable String libraryPath, HttpServletRequest request, final SearchConditionValue searchValue, @PathVariable String listFlag) {

		QueryContditionSet<AuthAppUser> qcs = new QueryContditionSet<AuthAppUser>() {
			@Override
			public void setContdition(Query<AuthAppUser> q) {
				if (searchValue.getTextValue() != null ) {
					q.or(q.criteria("email").contains(searchValue.getTextValue()),
							q.criteria("fullName").contains(searchValue.getTextValue()),
							q.criteria("password").contains(searchValue.getTextValue()),
							q.criteria("passwordHint").contains(searchValue.getTextValue()),
							q.criteria("userName").contains(searchValue.getTextValue()));
				}
			}
		};
		
		Model model = list(dbManager, DM_FORM_NAME, libraryPath, request, searchValue, listFlag, qcs);

		return new ModelAndView(LIST_NAME, model.asMap());
	}
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/view/{id}/php*", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable String libraryPath, @PathVariable Long id, HttpServletRequest request) {
        return super.view(dbManager, DM_FORM_NAME, FORM_NAME, libraryPath, id);
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/edit/{id}/php*", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String libraryPath, @PathVariable Long id) {
        return super.edit(dbManager, DM_FORM_NAME, FORM_NAME, libraryPath, id);
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/delete/{idList}/php*", method = RequestMethod.POST)
	public ModelAndView delete(@PathVariable String libraryPath, HttpServletRequest request, HttpServletResponse response, @PathVariable String idList) {
        return super.delete(dbManager, DM_FORM_NAME, FORM_NAME, libraryPath, request, idList);
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/add/php*", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable String libraryPath) {
		Model model = new ExtendedModelMap();
		super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
		AuthAppUser authAppUser = new AuthAppUser();
		authAppUser.setUserId(SequenceUtil.getNext(AuthAppUser.class));
		authAppUser.setLibrary(LibraryInfoCache.getInstance().getLibraryIdByPath(libraryPath));
		authAppUser.setOvert(false);

		model.addAttribute(DM_FORM_NAME, authAppUser);
		model.addAttribute("formEditFlag", true);
		model.addAttribute("addFlagOfAuthAppUserForm", "1");

		return new ModelAndView(FORM_NAME, model.asMap());
	}


	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/formSubmit/php*", method = RequestMethod.POST)
	public ModelAndView onSubmit(@PathVariable String libraryPath, AuthAppUser bean, HttpServletRequest request, HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		String success = "redirect:/lb/{libraryPath}/authAppUser/list/mt/php";
		model.addAttribute("formEditFlag", true);
		model.addAttribute("addFlagOfAuthAppUserForm", request.getParameter("addFlagOfAuthAppUserForm"));

		try {
			super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
			if (validator != null) { // validator is null during testing
				ServerValidataResult svr = new ServerValidataResult();
				validate(bean, svr);

				if (svr.hasErrors()) { // don't validate when deleting
					saveError(request, svr.getAllErrorMessage());
					return new ModelAndView(FORM_NAME, model.asMap());
				}
			}

			log.debug("entering 'onSubmit' method...");
			boolean isNew = ("1".equals(request.getParameter("addFlagOfAuthAppUserForm")));
			Locale locale = request.getLocale();

			if (bean.getUserName() == null || bean.getUserName().equals("")) {
				bean.setUserName(null);
			}
			if (isNew) {
				dbManager.save(bean, true);
			} else {
				dbManager.save(bean, false);
			}

			String key = (isNew) ? "authAppUser.added" : "authAppUser.updated";
			saveMessage(request, getText(key, locale));
			if (!isNew) {
				success = "redirect:/lb/{libraryPath}/authAppUser/view/" + bean.getUserId() + "/php";

			}

		} catch (MongoException e) {
			e.printStackTrace();
			saveMessage(request, "添加用户失败，" + e.getMessage());

		}
		return new ModelAndView(success, model.asMap());

	}
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/list/select/php*")
    @ResponseBody
    public Object getSelect2Json() {
    	List<AuthAppUser> list=dbManager.getAll();
    	List<KeyValue> out=new ArrayList<KeyValue>();
    	for (AuthAppUser object : list) {
			out.add(new KeyValue(object.getUserId(),object.getFullName()));
		}
    	return out;
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/view/idx_user_username/validate/php*", method = RequestMethod.POST)
    @ResponseBody
	public Boolean validateIndex1(@PathVariable String libraryPath, @RequestParam("userName") String userName, @RequestParam("userId") Long userId) {
		Boolean resultb = dbManager.checkUniqueIndexForUserName(userId, userName);
		return resultb;
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/view/idx_user_email/validate/php*", method = RequestMethod.POST)
    @ResponseBody
	public Boolean validateIndex2(@PathVariable String libraryPath, @RequestParam("email") String email, @RequestParam("userId") Long userId) {
		Boolean resultb = dbManager.checkUniqueIndexForEmail(userId, email);
		return resultb;
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/view/idx_user_phonenumber/validate/php*", method = RequestMethod.POST)
    @ResponseBody
	public Boolean validateIndex3(@PathVariable String libraryPath, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("userId") Long userId) {
		Boolean resultb = dbManager.checkUniqueIndexForPhoneNumber(userId, phoneNumber);
		return resultb;
	}

	// /////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/edit/resetPassword/{userId}/php*", method = RequestMethod.GET)
	public ModelAndView changePassword(@PathVariable String libraryPath, @PathVariable Long userId) {
		Model model = new ExtendedModelMap();
		super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
		model.addAttribute("formEditFlag", true);
		AuthAppUser user = dbManager.get(userId);
		user.setPassword("");
		model.addAttribute(DM_FORM_NAME, user);
		return new ModelAndView("base/st/AuthAppUserFormChangePasswordForAdmin", model.asMap());
	}

	@RequestMapping(value = "/lb/{libraryPath}/authAppUser/edit/resetPassword/formSubmit/php*", method = RequestMethod.POST)
	public ModelAndView onSubmitChangePassword(@PathVariable String libraryPath, AuthAppUser authAppUser, HttpServletRequest request,HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		String success = "redirect:/lb/{libraryPath}/authAppUser/list/mt/php?pageGroupType=back";
		try {
			super.libraryAndPropertyPass(model, DM_FORM_NAME, libraryPath);
			AuthAppUser olduser = dbManager.get(authAppUser.getUserId());
			olduser.setPassword(authAppUser.getPassword());
			olduser.setPasswordHint(authAppUser.getPassword());
			dbManager.saveForChangePassword(olduser);
			saveMessage(request, "修改密码成功！");

		} catch (MongoException e) {
			e.printStackTrace();
			saveMessage(request, "修改用户密码失败，" + e.getMessage());
		}
		return new ModelAndView(success, model.asMap());

	}
}
