package dal.asd.catme.courses;

import dal.asd.catme.accesscontrol.IRoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import dal.asd.catme.accesscontrol.User;
import dal.asd.catme.config.CatmeSecurityConfig;
import dal.asd.catme.config.SystemConfig;
import dal.asd.catme.exception.CatmeException;
import dal.asd.catme.util.CatmeUtil;

@Controller
public class CourseController
{
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    CatmeSecurityConfig catmeSecurityConfig;


    @GetMapping("taEnrollment/{courseId}")
    public String enrollTa(@PathVariable("courseId") String courseId, Model model)
    {
        model.addAttribute("courseId", courseId);
        return CatmeUtil.TA_ENROLLMENT_PAGE;
    }

    @RequestMapping("taEnrollment/access")
    public String redirectHomePage()
    {
        log.info("Finding the Role of User and redirecting to respetive User's home page");
        return "redirect:/access";
    }

    @RequestMapping("taEnrollment/{courseId}")
    public String enrollTa(@PathVariable("courseId") String courseId, @RequestParam String bannerId, Model model)
    {
        IRoleDao roleDao = CourseAbstractFactoryImpl.instance().createRoleDao();
        IRoleService roleService = CourseAbstractFactoryImpl.instance().createRoleService(roleDao);

        Enrollment user = new Enrollment(bannerId, courseId);
        model.addAttribute("user", user);
        String message = roleService.assignTa(user);
        model.addAttribute("message", message);
        return CatmeUtil.TA_ENROLLED_PAGE;
    }

    public ModelAndView identifyAccess(ModelAndView modelAndView, String role)
    {
        log.info("Identifying the User's access to selected course as " + role);

        switch (role)
        {

            case CatmeUtil.TA_ROLE:
                log.info("Identified as TA for the selected course");
                modelAndView.addObject("isTa", true);
                modelAndView.addObject("isInstructor", false);
                modelAndView.addObject("isStudent",false);
                break;

            case CatmeUtil.INSTRUCTOR_ROLE:
                log.info("Identified as Instructor for the selected course");
                modelAndView.addObject("isInstructor", true);
                modelAndView.addObject("isTa", false);
                modelAndView.addObject("isStudent",false);
                break;

            default:
                log.info("User does not have TA/Instructor access to selected course");
                modelAndView.addObject("isInstructor", false);
                modelAndView.addObject("isTa", false);
                modelAndView.addObject("isStudent",true);
                break;

        }

        return modelAndView;
    }

    @RequestMapping("/courseDisplay")
    public ModelAndView diplayCoursePage(@RequestParam(name = "courseId") String courseId) throws CatmeException
    {
        ICourseDao courseDao = CourseAbstractFactoryImpl.instance().createCourseDao();
        ICourseService courseService = CourseAbstractFactoryImpl.instance().createCourseService(courseDao);

        log.info("Selected Course page ID: " + courseId);
        User currentUser = new User();

        currentUser.setBannerId(SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("User is: " + currentUser.getBannerId());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("course", courseService.displayCourseById(courseId));
        log.info("Checking Database to identify " + currentUser.getBannerId() + " access to Course :" + courseId);

        identifyAccess(modelAndView, courseService.findRoleByCourse(currentUser, courseId));

        modelAndView.setViewName(CatmeUtil.COURSE_PAGE);

        return modelAndView;
    }

   
}
