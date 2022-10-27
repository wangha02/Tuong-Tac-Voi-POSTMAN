package rikkei.academy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rikkei.academy.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> findAll();

    void save(Student student);

    Optional<Student> findById(Long id);

    void deleteById(Long id);

    Boolean existsByName(String name);

    Page<Student> findAll(Pageable pageable);

    List<Student> findAllByNameContaining(String name);

    Page<Student> findAllByNameContaining(String name, Pageable pageable);


}
