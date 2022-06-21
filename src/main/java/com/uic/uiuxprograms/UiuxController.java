package com.uic.uiuxprograms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
//@controller lets spring know that this is a controller for the web application
//this follows REST patterns in the program were using GIT and POST
//every single method has to contain a model parameter that maps between back end and front end
//when returning , we return the initial part of the html page, spring adds the .html to know which file to reference
@Controller
public class UiuxController {

    private SchoolDatasetRepository schoolDatasetRepository;
    private UserRepository userRepository;

    //gets all schools
    //http://localhost:8080/allschoolsforstudents
    @GetMapping("/allschoolsforstudents")
    public String getAllSchools(
            Model model) {
        List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getAllSchools();
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }

    //gets by GRE
    //http://localhost:8080/getByGRE?required=N
    //http://localhost:8080/getByGRE?required=Y
    //the reason the link has a question mark is because you're passing in parameter that can change
    @GetMapping("/getByGRE")
    public String getByGRE(
            @RequestParam(name="required") String required,
            Model model) {
        List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getSchoolsByGRE(required);
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }

    //http://localhost:8080/toefl?lower=70&upper=80
    @GetMapping("/toefl")
    public String toefl(
            @RequestParam(name="lower") String lower,
            @RequestParam(name="upper", required = false) String upper,
            Model model) {
        List<SchoolDataset> schoolDatasets = null;
        if(upper == null) {
            schoolDatasets = schoolDatasetRepository.getSchoolsByToefl(Integer.parseInt(lower), 200);
        } else {
            schoolDatasets = schoolDatasetRepository.getSchoolsByToefl(Integer.parseInt(lower), Integer.parseInt(upper));
        }
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }

    //http://localhost:8081/program?program=MHCI
    @GetMapping("/program")
    public String program(
            @RequestParam(name="program") String program,
            Model model) {
        List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getSchoolsByProgram(program.toString());
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }


    //http://localhost:8081/getByPortfolio?portfolio=Optional
    //http://localhost:8081/getByPortfolio?portfolio=Required
    @GetMapping("/getByPortfolio")
    public String portfolio(
            @RequestParam(name="portfolio") String portfolio,
            Model model) {
        List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getSchoolsByPortfolio(portfolio.toString());
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }



    //http://localhost:8080/location?location=IL
    //http://localhost:8080/location?location=PA
    @GetMapping("/location")
    public String location(
            @RequestParam(name="location") String location,
            Model model) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(", ").append(location);
        List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getSchoolsByLocation(stringBuilder.toString());
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }


    //http://localhost:8080/deadline?deadline
    //http://localhost:8080/deadline?deadline=dec
    //http://localhost:8080/deadline?deadline=jan
    //http://localhost:8080/deadline?deadline=feb
    //http://localhost:8080/deadline?deadline=mar
    //http://localhost:8080/deadline?deadline=apr
    //http://localhost:8080/deadline?deadline=may
    //http://localhost:8080/deadline?deadline=jun
    @GetMapping("/deadline")
    public String deadline(
            @RequestParam(name="deadline") String deadline,
            Model model) {
        List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getSchoolsByDeadline(deadline.toString());
        model.addAttribute("schools", schoolDatasets);
        return "studentpage";
    }


    //Employee mapping
    //http://localhost:8080/allschoolsforemployees
    @GetMapping("/allschoolsforemployees")
    public String getAllSchoolsEmployees(
            @RequestParam(name="validated", required = false) String validated,
            Model model) {
        if (validated != null && validated.equals("true")) {
            List<SchoolDataset> schoolDatasets = schoolDatasetRepository.getAllSchools();
            model.addAttribute("schools", schoolDatasets);
            model.addAttribute("validated", validated);
            return "employeepage";
        } else {
            return "redirect:login";
        }
    }
    //you cant trigger url directly, you have to process the form and trigger the post
    @PostMapping("/update")
    public String update(@ModelAttribute SchoolDataset school, Model model) {
        schoolDatasetRepository.save(school);
        return "redirect:/allschoolsforemployees?validated=true";
    }

    //http://localhost:8080/getbyid/1
    @GetMapping("/getbyid/{id}")
    public String getByID(
            @PathVariable(name="id") Integer id,
            @RequestParam(name="validated", required = false) String validated, Model model) {
        if (validated != null && validated.equals("true")) {
            SchoolDataset schoolDatasets = schoolDatasetRepository.findById(id).orElse(new SchoolDataset());
            model.addAttribute("school", schoolDatasets);
            return "updateform";
        } else {
            return "redirect:login";
        }
    }
    //http://localhost:8080/addnewschool
    @GetMapping("/addnewschool")
    public String addNewSchool(@RequestParam(name="validated", required = false) String validated, Model model) {
        if (validated != null && validated.equals("true")) {
            model.addAttribute("school", new SchoolDataset());
            model.addAttribute("validated", validated);
            return "updateform";
        } else {
            return "redirect:login";
        }

    }
    //path variables allow you to specify variables without the ?mark
    @GetMapping("/deleteschool/{id}")
    public String deleteSchool(@PathVariable(name="id") Integer id,
                               @RequestParam(name="validated", required = false) String validated, Model model) {
        if (validated != null && validated.equals("true")) {
            schoolDatasetRepository.deleteById(id);
            return "redirect:/allschoolsforemployees?validated=true";
        } else {
            return "redirect:login";
        }
    }

    //login
    //http://localhost:8080/login
    @GetMapping("login")
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "employeeloginpage";
    }

    //it fetches user id and compares it with password. If the passwords match then it will redirect if its true, otherwise it will redirect to the loginpage
    //findbyid returns optional so I used orElse to return blank user model when its null
    @PostMapping("/loginuser")
    public String loginUser (@ModelAttribute User user, Model model) {
        User foundUser = userRepository.findById(user.getUsername()).orElse(new User());
        if (user.getPassword().equals(foundUser.getPassword())) {
            return "redirect:/allschoolsforemployees?validated=true";
        }
        model.addAttribute("user", new User());
        return "employeeloginpage";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        return "redirect:selectionpage.html";
    }


    //leverages 2 rest methods, first is the get mapping and 2nd is the post mapping
    //when you do get mapping with getprogram it will return null for schools so that table doesnt load and it will also return true for search to make search input visible
    @GetMapping("/getprogram")
    public String getSearch(Model model) {
        model.addAttribute("schools", null);
        model.addAttribute("search", true);
        return "studentpage";
    }

    //gets called when you click submit in the search and it also gets schools by program name
    @PostMapping("/getprogram")
    public String getProgram(Model model, @RequestParam String programName) {
        List<SchoolDataset> schoolDatasetList = schoolDatasetRepository.getSchoolsByProgram(programName);
        model.addAttribute("schools", schoolDatasetList);
        model.addAttribute("search", true);
        return "studentpage";
    }


    //do not delete
    //this is constructor for the class requiring schooldatasetrepository
    //spring will see this constructor and automatically pass in a new instance of schooldatasetrepository
    //schooldatasetrepository is used within each method to perform CRUD operations
    public UiuxController(SchoolDatasetRepository schoolDatasetRepository, UserRepository userRepository) {
        this.schoolDatasetRepository = schoolDatasetRepository;
        this.userRepository = userRepository;
    }



}