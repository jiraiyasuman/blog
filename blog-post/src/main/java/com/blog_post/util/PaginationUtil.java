package com.blog_post.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
	private PaginationUtil() {}

    public static PageRequest defaultPageRequest(

            int page,

            int size

    ) {

        if (size >
                AppConstants.MAX_PAGE_SIZE) {

            size =
                    AppConstants.MAX_PAGE_SIZE;
        }

        return PageRequest.of(

                page,

                size,

                Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                )
        );
    }
}