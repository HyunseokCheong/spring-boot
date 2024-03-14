package org.hyunseokcheong.userservice.service;

import java.util.*;

import org.hyunseokcheong.userservice.dto.UserDto;
import org.hyunseokcheong.userservice.jpa.UserEntity;
import org.hyunseokcheong.userservice.jpa.UserRepository;
import org.hyunseokcheong.userservice.vo.ResponsePurchase;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
		
		userRepository.save(userEntity);
		
		return modelMapper.map(userEntity, UserDto.class);
	}
	
	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		List<ResponsePurchase> purchases = new ArrayList<>();
		userDto.setPurchases(purchases);
		
		return userDto;
	}
	
	@Override
	public Iterable<UserDto> getUserByAll() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);
		
		Iterable<UserEntity> userEntities = userRepository.findAll();
		
		List<UserDto> result = new ArrayList<>();
		userEntities.forEach(v -> {
			UserDto userDto = modelMapper.map(v, UserDto.class);
			userDto.setPurchases(new ArrayList<>());
			result.add(userDto);
		});
		
		return result;
	}
}
