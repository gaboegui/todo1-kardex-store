package com.todo1.app.model.service;

import com.todo1.app.model.entity.Cliente;

public interface IUsuarioService {
	
	public Cliente findByUsername(String username);
}
