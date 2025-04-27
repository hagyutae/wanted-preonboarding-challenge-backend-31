package com.mkhwang.wantedcqrs.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenericMapper {
  private final ModelMapper modelMapper;

  // DTO -> Entity 변환
  public <D, E> E toEntity(D dto, Class<E> entityClass) {
    return this.modelMapper.map(dto, entityClass);
  }

  // 기존 Entity 업데이트
  public <D, E> E updateEntity(D dto, E entity) {
    this.modelMapper.map(dto, entity);
    return entity;
  }

  // Entity -> DTO 변환
  public <E, D> D toDto(E entity, Class<D> dtoClass) {
    return this.modelMapper.map(entity, dtoClass);
  }

  // DTO 리스트 -> Entity 리스트 변환
  public <D, E> List<E> toEntityList(List<D> dtoList, Class<E> entityClass) {
    return dtoList.stream()
            .map(dto -> toEntity(dto, entityClass))
            .collect(Collectors.toList());
  }

  // Entity 리스트 -> DTO 리스트 변환
  public <E, D> List<D> toDtoList(List<E> entityList, Class<D> dtoClass) {
    return entityList.stream()
      .map(entity -> modelMapper.map(entity, dtoClass))
      .collect(Collectors.toList());
  }
}
