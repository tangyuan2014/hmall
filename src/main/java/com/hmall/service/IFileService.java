package com.hmall.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String upload(MultipartFile multipartFile, String path);
}
