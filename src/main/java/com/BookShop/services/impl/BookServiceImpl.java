package com.BookShop.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.BookShop.controller.BookController;
import com.BookShop.entities.Author;
import com.BookShop.entities.Book;
import com.BookShop.entities.BookStatusEnum;
import com.BookShop.payload.PagedResponse;
import com.BookShop.repositories.BookRepository;
import com.BookShop.services.BookService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {
	 public static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
	@Autowired
	private BookRepository bookRepository;

	@Override
	public PagedResponse<Book> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findAll(pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}
	@Override
	public List<Book> findByBookStatusAndKeyword(String status, String keyword){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);

        
        
        List<Predicate> predicates = new ArrayList<>();
        if(status!= null) {
        	if(status.equals("ACTIVE")) {
        		 Path<BookStatusEnum> fieldPathStatus = root.get("status");
        		 Predicate predicate1 = criteriaBuilder.equal(fieldPathStatus, BookStatusEnum.ACTIVE);
        		 predicates.add(predicate1);
        	}
        	if(status.equals("BLOCK")) {
       		 	Path<BookStatusEnum> fieldPathStatus = root.get("status");
       		 	Predicate predicate1 = criteriaBuilder.equal(fieldPathStatus, BookStatusEnum.BLOCK);
       		 	predicates.add(predicate1);
        	}
        }
        
        if(keyword != null) {
        	  Path<String> fieldPathTitle = root.get("title");
        	  Predicate predicate1 = criteriaBuilder.like(fieldPathTitle, '%' +keyword+"%");
        	  predicates.add(predicate1);
        }
 
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        
//        int page = Integer.parseInt((String) filters.getOrDefault("page",  "0"));	
//        int size =   Integer.parseInt((String) filters.getOrDefault("size",  "9"));
        return typedQuery.getResultList();

        
//        typedQuery.setFirstResult(page * size);
//        typedQuery.setMaxResults(size);
//        List<Book> books = typedQuery.getResultList();
	}

	@Override
	public PagedResponse<Book> findByCategoryIdAndSearch(int page, int size, Long categoryId, 
			String keywork,String sortType, String authorName,
			double minPrice, double maxPrice) {
		// validate page and size

		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = null;
		if (categoryId == (long)0) // all category
		{	
			LOGGER.info(categoryId + " search");
			bookPages = bookRepository.search(keywork, authorName, minPrice, maxPrice, pageRequest);
			LOGGER.info("Books "+bookPages.getContent().size());
		} else {
			LOGGER.info(categoryId + "hahah");
			bookPages = bookRepository.searchByCategoryId(categoryId, keywork, authorName, minPrice, maxPrice, sortType, pageRequest);
		}
		
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByKeywordAndSortAndPrice(int page, int size, String keywork, String sort,
			double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeywordAndPriceAndSort(keywork,sort,minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}
	
	@Override
	public PagedResponse<Book> findByKeywordAndPrice(int page, int size, String keywork, 
			double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeywordAndPrice(keywork,minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByKeyword(int page, int size, String keyword) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeyword(keyword, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByKeywordAndSort(int page, int size, String keyword, String sort) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeywordAndSort(keyword, sort, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findBySortAndPrice(int page, int size, String sort, double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByPriceAndSort(sort,minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByPrice(int page, int size, double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByprice(minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> sort(int page, int size, String sort) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.sort(sort, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public Book findById(long id) {
		return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy sách có id: " +id));
		
	}

	@Override
	public PagedResponse<Book> findByCategoryId(int page, int size, Long id) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = null;
		if(id != (long)0)
			bookPages = bookRepository.findByCategoryId(id, pageRequest);
		else{
			bookPages = bookRepository.findAll(pageRequest);
		}
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByAuthorId(int page, int size, Long id) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByAuthorId(id, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public List<String> getformality() {
		
		return bookRepository.getFormality();
	}
	
	 	@PersistenceContext
	    private EntityManager entityManager;

	    public PagedResponse<Book> filterBooks(Map<String, Object> filters) {
	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
	        Root<Book> root = criteriaQuery.from(Book.class);

	        List<Predicate> predicates = new ArrayList<>();
	        for (Map.Entry<String, Object> entry : filters.entrySet()) {
	            String fieldName = entry.getKey();
	            Object value = entry.getValue();
	            if(fieldName.equals("keyword")) {
	            	Path<String> fieldPathTitle = root.get("title");
	            	Path<String> fieldPathAuthorName = root.get("author").get("fullname");
	            	Path<String> fieldPathSupplier = root.get("supplier");
	            	Predicate predicate1 = criteriaBuilder.like(fieldPathTitle, '%' +value.toString()+"%");
	            	Predicate predicate2 = criteriaBuilder.like(fieldPathAuthorName, '%' +value.toString()+"%");
	            	Predicate predicate3 = criteriaBuilder.like(fieldPathSupplier, '%' +value.toString()+"%");
		            predicates.add(criteriaBuilder.or(predicate1, predicate2, predicate3));
	            }
	            if(fieldName.equals("maxPrice")) {
	            	Path<Double> fieldPathPrice= root.get("price");
	            	Double maxPrice = Double.valueOf((String)value);
	            	Predicate predicate = criteriaBuilder.lessThanOrEqualTo(fieldPathPrice, maxPrice);
	            	predicates.add(predicate);
	            }
	            if(fieldName.equals("minPrice")) {
	            	Path<Double> fieldPathPrice= root.get("price");
	            	Double minPrice = Double.valueOf((String)value);
	            	Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(fieldPathPrice, minPrice);
	            	predicates.add(predicate);
	            }
	            if (fieldName.equals("sort")) {
	                Path<Double> sortPath = root.get("price");
	                Order order = value.equals("asc") == true ? criteriaBuilder.asc(sortPath) : criteriaBuilder.desc(sortPath);
	                criteriaQuery.orderBy(order);
	            }
	            if (fieldName.equals("categoryId")) {try {
	                Path<Long> fieldPathCategoryId = root.get("category").get("id");
	                Long id = Long.parseLong((String)value);
	                if(id != 0) {
	                	 Predicate predicate = criteriaBuilder.equal(fieldPathCategoryId, id);
	                	 predicates.add(predicate);
	                }
	               
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
	            	
	            }
	        }

	        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
	        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
	        
	        int page = Integer.parseInt((String) filters.getOrDefault("page",  "0"));	
	        int size =   Integer.parseInt((String) filters.getOrDefault("size",  "9"));
	        int totalResults = typedQuery.getResultList().size();

	        
	        typedQuery.setFirstResult(page * size);
	        typedQuery.setMaxResults(size);
	        List<Book> books = typedQuery.getResultList();

	        PageRequest pageRequest = PageRequest.of(page, size);
	        Page<Book> pagedResponse = new PageImpl<>(books,pageRequest, totalResults);
			return new PagedResponse<Book>(pagedResponse.getContent(), pagedResponse.getNumber(), pagedResponse.getSize(),
					pagedResponse.getTotalElements(), pagedResponse.getTotalPages(), pagedResponse.isLast());
	        
	     
	    }

		@Override
		public List<Book> findTop5ByCategoryId(long id) {
		    
			return bookRepository.findTop5ByCategotyId(id);
		}

		@Override
		public Book saveBook(Book newBook) {
			
			return bookRepository.save(newBook);
		}

		@Override
		public Book updateBookStatus(long bookId, Boolean isBlock) {
			Book book = bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("k"));
			if(isBlock) {
				book.setStatus(BookStatusEnum.BLOCK);
			}
			else {
				book.setStatus(BookStatusEnum.ACTIVE);
			}
			return bookRepository.save(book);
		}
		@Override
		public List<Book> top5Sale() {
			
			return bookRepository.top5Sale();
		}

		
		
	
	
}
