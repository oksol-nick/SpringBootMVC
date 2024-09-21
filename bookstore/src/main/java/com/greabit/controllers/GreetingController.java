package com.greabit.controllers;

import com.greabit.models.Book;
import com.greabit.models.BooksStorage;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/create-form")
    public  String createForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String create(Book book) {
        book.setId(UUID.randomUUID().toString());
        BooksStorage.getBooks().add(book);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        Book bookToDelete  = BooksStorage.getBooks().stream().
                filter(book -> book.getId().equals(id)).findFirst().
                orElseThrow(RuntimeException::new);
        BooksStorage.getBooks().remove(bookToDelete);
        return "redirect:/";
    }

    @GetMapping("/edit-form/{id}")
    public String editForm(@PathVariable("id") String id, Model model) {
        Book bookToEdit  = BooksStorage.getBooks().stream().
                filter(book -> book.getId().equals(id)).findFirst().
                orElseThrow(RuntimeException::new);
        model.addAttribute("book", bookToEdit);
        return "edit-form";
    }

    @PostMapping("/update")
    public String update(Book book) {
        delete(book.getId());
        BooksStorage.getBooks().add(book);
        return "redirect:/"; }

}
