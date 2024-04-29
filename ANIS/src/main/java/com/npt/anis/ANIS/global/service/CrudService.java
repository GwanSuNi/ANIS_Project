package com.npt.anis.ANIS.global.service;

import java.util.List;

/**
 * 모든 도메인이 사용하는 기본적인 crud 인터페이스
 *
 * @param <T>  반환 타입
 * @param <ID> id 타입
 */
public interface CrudService<T, ID> {
    T save(T dto);

    T findById(ID id);

    List<T> findAll();

    T update(ID id, T dto);

    void delete(ID id);
}
