package com.dataartschool.newsportal.service;

import com.dataartschool.newsportal.component.NewsMapper;
import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
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
        if (file.getContentType() == null || !file.getContentType().contains("zip"))
            throw new NotAcceptableFileReceived("File is not a zip");

        try(InputStream inputStream = file.getInputStream()) {
            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
                ZipEntry entry = zipInputStream.getNextEntry();
                if (entry == null)
                    throw new ZipIsEmpty("The received zip file is empty");

                NewsCreateRequestDto dto;
                do {
                    dto = ExtractCreationRequestDtoFromZipEntry(zipInputStream, entry);
                } while ((entry = zipInputStream.getNextEntry()) != null);

                dto.setSectionId(sectionId);
                NewsEntity newsEntity = newsMapper.fromCreateRequestDtoToEntity(dto);
                return newsMapper.toDto(newsRepository.save(newsEntity));
            }

        }
        catch (IOException e) {
            throw new NotAcceptableFileReceived("Invalid file received");
        }
    }

    private NewsCreateRequestDto ExtractCreationRequestDtoFromZipEntry(ZipInputStream zipInputStream, ZipEntry entry) {
        Scanner scanner;
        NewsCreateRequestDto dto = new NewsCreateRequestDto();
        if (!entry.getName().equals("article.txt"))
            throw new UnexpectedFileInZip("Unsupported file in the received zip file");

        scanner = new Scanner(zipInputStream);
        if (!scanner.hasNext())
            throw new NoTitleInArticleFileFound("No title in article.txt");
        dto.setTitle(scanner.nextLine());

        if (!scanner.hasNext())
            throw new NoInnerTextInArticleFileFound("No inner text in article.txt");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scanner.nextLine());
        do {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(scanner.nextLine());
        } while (scanner.hasNext());
        dto.setInnerText(stringBuilder.toString());
        return dto;
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
