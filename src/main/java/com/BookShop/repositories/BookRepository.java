package com.BookShop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Book;
import com.BookShop.payload.PagedResponse;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	Page<Book> findByCategoryId(long id, Pageable pageable);
	
	Page<Book> findByAuthorId(long id, Pageable pageable);

	@Query("select b from Book b where b.category.id=?1 and  LOWER(b.title) LIKE LOWER(CONCAT('%', ?2,'%'))"
			+ " and  LOWER(b.author.fullname) LIKE LOWER(CONCAT('%', ?3,'%'))" 
			+ " and b.price > ?4 and b.price < ?5"
			+ " ORDER BY b.title asc")
	Page<Book> searchByCategoryId(Long categoryId, String keywork, String authorName, double minPrice, double maxPrice,
			String sortType, Pageable pageable);

	@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))" 
	        + " AND LOWER(b.author.fullname) LIKE LOWER(CONCAT('%', :authorName, '%'))" 
	        + " AND b.price > :minPrice AND b.price < :maxPrice"
	        + " ORDER BY b.title ASC")
	Page<Book> search(@Param("keyword") String keyword, @Param("authorName") String authorName,
	                  @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

	
	@Query("select b from Book b where LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword,'%'))"
			+ " or LOWER(b.author.fullname) LIKE LOWER(CONCAT('%', :keyword,'%')) "
			+ " or LOWER(b.supplier) LIKE LOWER(CONCAT('%',  :keyword,'%'))")
	Page<Book> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("select b from Book b where LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword,'%'))"
	        + " or LOWER(b.author.fullname) LIKE LOWER(CONCAT('%', :keyword,'%')) "
	        + " or LOWER(b.supplier) LIKE LOWER(CONCAT('%',  :keyword,'%'))"
	        + " order by "
	        + " CASE WHEN :sort = 'asc' THEN b.price END ASC,"
	        + " CASE WHEN :sort = 'desc' THEN b.price END DESC")
	Page<Book> findByKeywordAndSort(
	    @Param("keyword") String keyword,
	    @Param("sort") String sort,
	    Pageable pageable
	);

	@Query("select b from Book b where (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword,'%'))"
	        + " or LOWER(b.author.fullname) LIKE LOWER(CONCAT('%', :keyword,'%')) "
	        + " or LOWER(b.supplier) LIKE LOWER(CONCAT('%',  :keyword,'%')))"
	        + " and b.price <= :maxPrice and b.price >= :minPrice"
	        + " order by "
	        + " CASE WHEN :sort = 'asc' THEN b.price END ASC,"
	        + " CASE WHEN :sort = 'desc' THEN b.price END DESC")
	Page<Book> findByKeywordAndPriceAndSort(
		    @Param("keyword") String keyword,
		    @Param("sort") String sort, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
		    Pageable pageable
		);
	
	@Query("select b from Book b where (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword,'%'))"
	        + " or LOWER(b.author.fullname) LIKE LOWER(CONCAT('%', :keyword,'%')) "
	        + " or LOWER(b.supplier) LIKE LOWER(CONCAT('%',  :keyword,'%')))"
	        + " and b.price <= :maxPrice and b.price >= :minPrice")
	Page<Book> findByKeywordAndPrice(
		    @Param("keyword") String keyword,
		    @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
		    Pageable pageable
		);
	
	

	@Query("select b from Book b where"
	        + " b.price <= :maxPrice and b.price >= :minPrice"
	        + " order by "
	        + " CASE WHEN :sort = 'asc' THEN b.price END ASC,"
	        + " CASE WHEN :sort = 'desc' THEN b.price END DESC")
	Page<Book>  findByPriceAndSort(String sort, double minPrice, double maxPrice, Pageable pageable);
	
	@Query("select b from Book b where"
	        + " b.price <= :maxPrice and b.price >= :minPrice")
	Page<Book>  findByprice(double minPrice, double maxPrice, Pageable pageable);
	
	@Query("select b from Book b order by"
			 + " CASE WHEN :sort = 'asc' THEN b.price END ASC,"
		     + " CASE WHEN :sort = 'desc' THEN b.price END DESC")
	Page<Book>  sort(String sort, Pageable pageable);
}
