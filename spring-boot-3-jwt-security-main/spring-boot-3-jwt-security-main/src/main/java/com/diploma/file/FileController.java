package com.diploma.file;

import com.diploma.auth.AuthenticationService;
import com.diploma.auth.RegisterRequest;
import com.diploma.news.News;
import com.diploma.news.NewsRequest;
import com.diploma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

  private final AuthenticationService service;

  @Autowired
  FilesStorageService storageService;


  @GetMapping("/admin/files/new")
  public String newFile(Model model, Principal principal) {
    String email = principal.getName();
    User user = service.getByEmail(email);
    model.addAttribute("information", user);
    model.addAttribute("proRequest",new NewsRequest());
    return "newsForm";
  }

  @PostMapping("/admin/files/upload")
  public String uploadFile(Model model, @RequestParam("file") MultipartFile file, @ModelAttribute("proRequest")NewsRequest newsRequest, Principal principal) {
    String message = "";
    String error = "";
    String email = principal.getName();
    User user = service.getByEmail(email);
    model.addAttribute("information", user);
    try {
      storageService.save(file,newsRequest, user);
      System.out.println("smth");

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      model.addAttribute("message", message);
    } catch (Exception e) {
      error = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
      model.addAttribute("error", error);

    }

    return "newsForm";
  }
  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @GetMapping("/files")
  public String getListFiles(Model model) {
    List<News> newsList = storageService.findAllNews(); // Fetch the News entities
    model.addAttribute("news", newsList);

    return "news";
  }


  @GetMapping("/files/delete/{filename:.+}")
  public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
    try {
      boolean existed = storageService.delete(filename);

      if (existed) {
        redirectAttributes.addFlashAttribute("message", "Delete the file successfully: " + filename);
      } else {
        redirectAttributes.addFlashAttribute("message", "The file does not exist!");
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message",
          "Could not delete the file: " + filename + ". Error: " + e.getMessage());
    }

    return "redirect:/files";
  }

  @GetMapping("/admin/news/deleteNews/{id}")
  public String deleteNews(@PathVariable("id") Integer id) {
    News newsRequest = storageService.getNewsId(id);
    if (newsRequest == null) {
      return "redirect:/file/admin/news/0";
    }
    storageService.deleteNewsById(id);
    storageService.delete(newsRequest.getFileName());
    return "redirect:/file/admin/news/0";

  }

  @GetMapping("/user/allnews/{pageNo}")
  public String newsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

    Page<News> news = storageService.pageNews(pageNo);
    String email = principal.getName();
    User user = service.getByEmail(email);
    service.authenticate(email);
    model.addAttribute("information", user);
    model.addAttribute("title", "Manage Product");
    model.addAttribute("size", news.getSize());
    model.addAttribute("totalPages", news.getTotalPages());
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("news", news);
    return "userNews";
  }


  @GetMapping("/user/statement")
  public String newsState(Model model, Principal principal){

   List<News> newss= storageService.findAllNews();
    List<News> filteredList = new ArrayList<>();
    for (News news : newss) {
      String fileName = news.getFileName();

      if (fileName != null && fileName.contains("statement")) {
        // If the fileName contains the word "statement", add the entity to the filteredList
        filteredList.add(news);
      }
    }

    String email = principal.getName();
    User user = service.getByEmail(email);
    service.authenticate(email);
    model.addAttribute("information", user);
    model.addAttribute("news", filteredList);
    return "stateExamples";
  }

  @GetMapping("/admin/news/{pageNo}")
  public String Admin(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

    Page<News> news = storageService.pageNews(pageNo);
    String email = principal.getName();
    User user = service.getByEmail(email);
    service.authenticate(email);
    model.addAttribute("information", user);
    model.addAttribute("title", "Manage Product");
    model.addAttribute("size", news.getSize());
    model.addAttribute("totalPages", news.getTotalPages());
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("news", news);
    return "allNewsDelete";
  }

  @GetMapping("/user/allnews/detailedNews/{id}")
  public String detailedNews(@PathVariable("id") Integer id, Model model,Principal principal) throws ChangeSetPersister.NotFoundException {
    String email = principal.getName();
    User user = service.getByEmail(email);
    News news= storageService.getNewsId(id);
    if (news == null) {
      return "redirect:user/allnews/{pageNo}";
    }
    model.addAttribute("information", user);
    model.addAttribute("news", news);
    return "detailedNews";

  }

}
