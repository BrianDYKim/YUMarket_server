package team.project.yumarket.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.project.yumarket.controller.MarketReviewApiController;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/24
 */
@RestControllerAdvice(basePackageClasses = MarketReviewApiController.class)
public class MarketReviewBadResponseHandler extends BaseBadResponseHandler {

}
