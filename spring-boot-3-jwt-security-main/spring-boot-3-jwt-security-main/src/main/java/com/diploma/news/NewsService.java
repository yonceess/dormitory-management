package com.diploma.news;

import com.diploma.extras.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public String uploadImage(MultipartFile file, NewsRequest newsRequest) throws IOException {

        News newsData = newsRepository.save(News.builder()
                        .name(newsRequest.getName())
                        .description(newsRequest.getDescription())
                        .date(newsRequest.getDate())
                .fileName(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (newsData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<News> dbImageData = newsRepository.findByFileName(fileName);
        System.out.println(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

 /*   public News create(NewsRequest newsRequest){

            var news = News.builder().
            name(newsRequest.getName()).
            description(newsRequest.getDescription()).
            date(newsRequest.getDate()).
            linkPicture(newsRequest.getLinkPicture()).build();

            return newsRepository.save(news);

    }



    public News updateNews(NewsRequest newsRequest){
        System.out.println(newsRequest.getDescription());
        News news = newsRepository.findByName(newsRequest.getName());
        String helpName = news.getName();
        if(helpName.equals(newsRequest.getName())){
            news.setDescription(newsRequest.getDescription());
            news.setDate(newsRequest.getDate());
            news.setLinkPicture(newsRequest.getLinkPicture());
            return newsRepository.save(news);
        }
        else {
            return null;
        }
    }
*/

    public List<News> findAllNews() {
        return (List<News>) newsRepository.findAll();
    }
    public void deleteNewsById(Integer id){
        newsRepository.deleteById(id);
    }

    public News getNews(Integer id){
        return newsRepository.findById((int) id.longValue()).orElse(null);
    }

    public Optional<News> getNext() {
        return newsRepository.findById(newsRepository.getNextSeriesId());
    }

    public Page<News> pageNews(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 4);
        Page<News> newsPages = newsRepository.pageNews(pageable);
        return newsPages;
    }
}
