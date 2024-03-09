package kz.library.system.aspects;

import kz.library.system.models.dto.BookDTO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorIdValidationAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthorIdValidationAspect.class);

    @Before("execution(* kz.library.system.services.impl.BookServiceImpl.saveBook(..)) && args(bookDTO)")
    public void validateAuthorId(BookDTO bookDTO) {
        Long authorId = bookDTO.getAuthor().getId();
        if (authorId == null || authorId <= 0) {
            logger.error("Invalid author_id in BookDTO: {}", authorId);
        }
    }
}
