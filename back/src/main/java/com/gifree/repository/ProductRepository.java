package com.gifree.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gifree.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    @EntityGraph(attributePaths = "imageList")
  @Query("select p from Product p where p.pno = :pno")
  Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
  @Query("update Product p set p.delFlag = :flag where p.pno = :pno")
  void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);

  // 상품하나당 이미지 하나의 목록이 보이게 하기 위함. 
    @Query("select p, pi  from Product p left join p.imageList pi  where pi.ord = 0 and p.delFlag = false ")
  Page<Object[]> selectList(Pageable pageable);
  
}