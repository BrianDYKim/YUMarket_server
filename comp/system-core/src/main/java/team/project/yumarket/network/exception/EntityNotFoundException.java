package team.project.yumarket.network.exception;

import team.project.yumarket.network.errorCode.ErrorCode;

/**
 * Entity를 못 찾은 경우에 발생시키는 Exception
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/24
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
