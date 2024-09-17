package com.divin.urlshortener.shortener;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMapperRepository extends JpaRepository<UrlMapper, String> {

}
