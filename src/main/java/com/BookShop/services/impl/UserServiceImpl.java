package com.BookShop.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.BookShop.entities.Role;
import com.BookShop.entities.RoleEnum;
import com.BookShop.entities.User;
import com.BookShop.exceptions.AppException;
import com.BookShop.exceptions.NotFoundException;
import com.BookShop.payload.UserInfoRequest;

import com.BookShop.repositories.RoleRepository;
import com.BookShop.repositories.UserRepository;
import com.BookShop.services.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;



@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User createUser(UserInfoRequest userRequest) {
		User newUser = new User();
		newUser.setUsername(userRequest.getEmail());
		newUser.setPassword( passwordEncoder.encode(userRequest.getCreatePassword()));
		newUser.setFullName(userRequest.getFullname());
		newUser.setEmail(userRequest.getEmail());
		newUser.setPhone(userRequest.getPhone());
		newUser.setAccountNonBlock(true);
		newUser.setCreatedDate(LocalDateTime.now());
		Role role = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(()->new RuntimeException(""));
		newUser.setRoles(Arrays.asList(role));
		return userRepository.save(newUser);
	}

	@Override
	public boolean checkUsernameAvailability(String username) {
		// TODO Auto-generated method stub
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean checkEmailAvailability(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean giveAdmin(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Username không tồn tại"));
		List<Role> roles = new ArrayList<Role>();
	
		Role roleAdmin = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(()->  new AppException("Không thể trao quyền Admin"));
		Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(()->  new AppException("Không thể trao quyền Admin"));
		roles.add(roleAdmin);
		roles.add(roleUser);
		user.setRoles(roles);
		user =	userRepository.save(user);
		return true;
	}

	@Override
	public boolean removeAdmin(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Username không tồn tại"));
		Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(()->new AppException("Có lỗi xảy ra"));
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleUser);
		user.setRoles(roles);
		userRepository.save(user);
		return true;
	}

	@Override
	@Transactional
	public boolean blockUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("Username không tồn tại"));
		user.setAccountNonBlock(false);
		userRepository.save(user);
		return true;
	}

	@Override
	@Transactional
	public boolean activeUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Username không tồn tại"));
		user.setAccountNonBlock(true);
		userRepository.save(user);
		return true;
	}

	@Override
	public List<User> findByKeywordAndStatus(String accountNonBlock, String keyword) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        if(accountNonBlock!= null) {
       	 	Path<Boolean> fieldPathAccountNonBlock = root.get("accountNonBlock");
       	 	
        	if(accountNonBlock.equals("active")) {
        		Predicate  predicate = criteriaBuilder.equal(fieldPathAccountNonBlock, true);
        		predicates.add(predicate);
        	}
        		
        	if(accountNonBlock.equals("block")) {
        		Predicate  predicate = criteriaBuilder.equal(fieldPathAccountNonBlock, false);
            	predicates.add(predicate);
        	}
        		
        }
        
        if(keyword != null) {
        	  Path<String> fieldPathPhone = root.get("phone");
        	  Path<String> fieldPathUsername = root.get("username");
        	  Predicate predicate1 = criteriaBuilder.like(fieldPathPhone, '%' +keyword+"%");
        	  Predicate predicate2 = criteriaBuilder.like(fieldPathUsername, '%' +keyword+"%");    
        	  predicates.add(criteriaBuilder.or(predicate1,predicate2));
        }
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	

}
