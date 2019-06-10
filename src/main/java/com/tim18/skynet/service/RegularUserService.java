package com.tim18.skynet.service;

import com.tim18.skynet.model.RegisteredUser;

public interface RegularUserService {
	RegisteredUser findById(Long id);
}
