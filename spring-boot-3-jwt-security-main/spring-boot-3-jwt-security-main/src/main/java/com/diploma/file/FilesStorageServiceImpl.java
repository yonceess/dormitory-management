package com.diploma.file;

import com.diploma.items.Items;
import com.diploma.news.News;
import com.diploma.news.NewsRepository;
import com.diploma.news.NewsRequest;
import com.diploma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {

private final NewsRepository newsRepository;
  private final Path root = Paths.get("./uploads");

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file, NewsRequest newsRequest, User user) {
    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
      News news = new News();
      news.setDescription(newsRequest.getDescription());
      news.setDate(newsRequest.getDate());
      news.setDame(newsRequest.getDame());
      news.setFileName(file.getOriginalFilename());
      Path file1 = root.resolve(file.getOriginalFilename());
      String url = MvcUriComponentsBuilder
              .fromMethodName(FileController.class, "getFile", file1.getFileName().toString()).build().toString();
       news.setFilePath(url);
       news.setUser(user);
      newsRepository.save(news);
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());
      System.out.println(resource);
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public boolean delete(String filename) {
    try {
      Path file = root.resolve(filename);
      return Files.deleteIfExists(file);
    } catch (IOException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

  @Override
  public List<News> findAllNews() {
    return newsRepository.findAll();
  }

  @Override
  public News getNewsId(Integer id){
    return newsRepository.findById((int) id.longValue()).orElse(null);
  }

  @Override
  public void deleteNewsById(Integer id){
    newsRepository.deleteById(id);
  }

  @Override

  public Page<News> pageNews(int pageNo){
    Pageable pageable = PageRequest.of(pageNo, 5);
    Page<News> newsPages = newsRepository.pageNews(pageable);
    return newsPages;
  }


}
