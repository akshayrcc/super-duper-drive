package com.akshayram.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
  private MultipartFile file;

  /*getter and setters for the multipartFile Type*/

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }
}
