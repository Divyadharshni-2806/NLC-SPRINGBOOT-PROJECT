
package com.student_management.student.Controller;

import com.student_management.student.Entity.Student;
import com.student_management.student.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Display the dashboard with list of students
    @GetMapping
    public String viewDashboard(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "dashboard";
    }

    // Show form to add new student
    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // Save student (used for both add and update)
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/dashboard";
    }

    // Show form to edit student
    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        return "edit-student";
    }

    // Update student (just reuse save endpoint or create separate if needed)
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student); // save handles update as well
        return "redirect:/dashboard";
    }

    // Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/dashboard";
    }
}
