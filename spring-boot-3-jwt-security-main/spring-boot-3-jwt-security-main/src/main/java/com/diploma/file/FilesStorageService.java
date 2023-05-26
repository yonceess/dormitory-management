package com.diploma.file;

import com.diploma.news.News;
import com.diploma.news.NewsRequest;
import com.diploma.user.User;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file, NewsRequest newsRequest, User user);

  public Resource load(String filename);

  public boolean delete(String filename);
  
  public void deleteAll();

  public Stream<Path> loadAll();

  public List<News> findAllNews();

  public News getNewsId(Integer id);

  public void deleteNewsById(Integer id);

  public Page<News> pageNews(int pageNo);
}
