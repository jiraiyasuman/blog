package com.blog_post.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CachedFeedDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CachedPostDto> posts;

    private Integer currentPage;

    private Boolean hasNext;
}