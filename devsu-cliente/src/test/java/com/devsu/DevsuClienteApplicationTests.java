package com.devsu;

import com.devsu.models.entities.ClienteEntity;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.repositories.ClienteRepository;
import com.devsu.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class DevsuClienteApplicationTests {

	@MockBean
	private ClienteRepository clienteRepository;

	@Autowired
	ClienteService clienteService;

	@Test
	void obtenerCliente() {
		when(clienteRepository.findById(1L)).thenReturn(crearCliente());
		Optional<ClienteResponse> clienteEntity = clienteService.obtener(1L).blockOptional();
		assertTrue("Cliente no encontrado", clienteEntity.isPresent());
		if(clienteEntity.isPresent()){
			assertEquals(true, clienteEntity.get().getEstado());
			assertEquals("Juan", clienteEntity.get().getNombre());
		}
		verify(clienteRepository, times(1)).findById(1L);

	}

	private Optional<ClienteEntity> crearCliente(){
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setId(1L);
		clienteEntity.setEstado(true);
		clienteEntity.setNombre("Juan");
		clienteEntity.setContrasenia("123456");
		clienteEntity.setGenero("M");
		clienteEntity.setDireccion("Calle 123");
		clienteEntity.setTelefono("938254678");
		clienteEntity.setEdad(25);
		clienteEntity.setIdentificacion("1234567890");
		return Optional.of(clienteEntity);
	}

}
