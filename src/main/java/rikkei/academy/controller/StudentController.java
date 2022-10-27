package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.reponse.ResponseMessage;
import rikkei.academy.model.Student;
import rikkei.academy.service.IStudentService;

import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    public IStudentService studentService;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getName().trim().equals("") || student.getName().length() < 3 || student.getName().length() > 10) {
            return new ResponseEntity<>(new ResponseMessage("The name is invalid"), HttpStatus.OK);
        }
        if (studentService.existsByName(student.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name is existed"), HttpStatus.OK);
        }
        studentService.save(student);
        return new ResponseEntity<>(new ResponseMessage("Create success"), HttpStatus.OK);
    }

    //    HAM THEM
    @GetMapping
    public ResponseEntity<?> showListStudent() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> pageStudent(@PageableDefault(sort = "name", size = 2) Pageable pageable) {
        return new ResponseEntity<>(studentService.findAll(pageable), HttpStatus.OK);
    }

    //    HAM SUA
    @PutMapping("{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> student1 = studentService.findById(id);
        if (!student1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (student.getName().trim().equals("") || student.getName().length() < 3 || student.getName().length() > 20) {
            return new ResponseEntity<>(new ResponseMessage("The name is invalid"), HttpStatus.OK);
        }
        if (studentService.existsByName(student.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name is existed"), HttpStatus.OK);
        }
        student1.get().setName(student.getName());
        studentService.save(student1.get());
        return new ResponseEntity<>(new ResponseMessage("Update Success"), HttpStatus.OK);
    }

    //Ham Show Thong Tin
    @GetMapping("{id}")
    public ResponseEntity<?> detailStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }
// Ham Xoa
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("Delete success!"), HttpStatus.OK);
    }

    //Cach 1: Tim kiem bang @PathVariable
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name) {
        return new ResponseEntity<>(studentService.findAllByNameContaining(name), HttpStatus.OK);
    }

    //Cach 2: Tim kiem bang @RequestParam
    @GetMapping("/search/page")
    public ResponseEntity<?> searchPageStudent(@RequestParam String name, Pageable pageable) {
        return new ResponseEntity<>(studentService.findAllByNameContaining(name, pageable), HttpStatus.OK);
//        http://localhost:8081/students/search/page?name=t
    }

}

