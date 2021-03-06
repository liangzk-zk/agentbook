/*
 * Copyright 2013-2019 Smartdot Technologies Co., Ltd. All rights reserved.
 * SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.ab.app.agentbook.exception.mvc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ab.app.agentbook.exception.mvc.message.ValidationError;


/**
 * Restful服务异常response处理接口，实现缺省实现
 * 
 * @author <a href="mailto:zouwt@smartdot.com.cn">Wayne Zou</a>
 * @version 1.0, 2019年4月3日
 */
public interface ResponseExceptionHandler {
    Logger log = LoggerFactory.getLogger(ResponseExceptionHandler.class);
    /**
     * 资源不存在时的异常包装处理
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(NotFoundResourceException.class)
    default ResponseEntity<ValidationError> rulesNotFoundResourceException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * 创建指定的Rest资源失败，该Rest资源已存在时的异常包装处理
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    default ResponseEntity<ValidationError> rulesForResourceAlreadyExistsException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 对资源进行该操作不支持的异常包装处理，请求数据格式错误或者该操作没有实现
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(UnspportedOperationException.class)
    default ResponseEntity<ValidationError> rulesForUnspportedOperationException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 认证失败，请求中没有认证信息
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(UnauthorizedException.class)
    default ResponseEntity<ValidationError> rulesForUnauthorizedException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 不允许在指特定的资源上执行某种请求操作
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(OperationNotAllowedException.class)
    default ResponseEntity<ValidationError> rulesOperationNotAllowedException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 没有权限执行该请求操作
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(OperationForbiddenException.class)
    default ResponseEntity<ValidationError> rulesOperationForbiddenException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * 操作冲突，多个操作同时操作同一个资源，引起冲突
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(OperationConflictException.class)
    default ResponseEntity<ValidationError> rulesOperationConflictException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.CONFLICT);
    }

    /**
     * 发生系统内部异常
     * 
     * @param req http servlet request
     * @param e 抛出的异常对象
     * @return 包含http status code和错误信息的Response返回
     */
    @ExceptionHandler(InternalServerErrorException.class)
    default ResponseEntity<ValidationError> rulesInternalServerErrorException(HttpServletRequest req, Exception e) {
        ValidationError error = new ValidationError(e.getMessage());
        log.error(e.getMessage(), e);
        return new ResponseEntity<ValidationError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
