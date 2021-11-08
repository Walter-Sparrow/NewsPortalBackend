package com.dataartschool.newsportal.service;

import com.dataartschool.newsportal.component.NewsMapper;
import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.controller.dto.PageDto;
import com.dataartschool.newsportal.exception.*;
import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import com.dataartschool.newsportal.persistence.entity.NewsSectionEntity;
import com.dataartschool.newsportal.persistence.repository.NewsRepository;
import com.dataartschool.newsportal.persistence.repository.NewsSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsSectionRepository newsSectionRepository;
    private final NewsMapper newsMapper;

    public Page<NewsDto> fetchAll(PageDto pageDto) {
        return newsRepository.findAll(pageDto.getPageable()).map(newsMapper::toDto);
    }

    public NewsDto getById(Long id) {
        return newsMapper.toDto(getExistingById(id));
    }

    public NewsDto add(MultipartFile file, Long sectionId) {
        try(InputStream inputStream = file.getInputStream()) {

            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry entry = zipInputStream.getNextEntry();
                if (entry == null)
                    throw new ZipIsEmpty("The received zip file is empty");

                String title;
                String innerText;
                Scanner scanner;
                do {
                    if (!entry.getName().equals("article.txt"))
                        throw new UnexpectedFileInZip("Unsupported file in the received zip file");

                        scanner = new Scanner(zipInputStream);
                        if (!scanner.hasNext())
                            throw new NoTitleInArticleFileFound("No title in article.txt");
                        title = scanner.nextLine();

                        if (!scanner.hasNext())
                            throw new NoInnerTextInArticleFileFound("No inner text in article.txt");

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(scanner.nextLine());
                        do {
                            stringBuilder.append(System.lineSeparator());
                            stringBuilder.append(scanner.nextLine());
                        } while (scanner.hasNext());
                        innerText = stringBuilder.toString();
                } while ((entry = zipInputStream.getNextEntry()) != null);
                NewsEntity newsEntity = newsMapper.fromCreateRequestToEntity(title, innerText, sectionId);
                return newsMapper.toDto(newsRepository.save(newsEntity));
            }

        }
        catch (IOException e) {
            throw new NotAcceptableFileReceived("File is not a zip");
        }
    }

    public Page<NewsDto> getAllBySectionId(Long id, PageDto pageDto) {
        NewsSectionEntity sectionEntity = newsSectionRepository.findById(id)
                .orElseThrow(()->new NoNewsSectionFound("No news section with this ID was found"));

        return newsRepository.findAllBySection(sectionEntity, pageDto.getPageable()).map(newsMapper::toDto);
    }

    private NewsEntity getExistingById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(()->new NoNewsFound("No news with this ID was found"));
    }

}
