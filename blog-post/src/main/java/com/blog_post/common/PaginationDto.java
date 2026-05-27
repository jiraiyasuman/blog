package com.blog_post.common;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {

    private Integer page;

    private Integer size;

    private Long totalElements;

    private Integer totalPages;

    private Boolean hasNext;

    private Boolean hasPrevious;
}
