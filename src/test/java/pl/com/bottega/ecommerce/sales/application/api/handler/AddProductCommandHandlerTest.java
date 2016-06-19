package pl.com.bottega.ecommerce.sales.application.api.handler;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import java.util.Date;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;
import pl.com.bottega.ecommerce.system.application.SystemUser;

public class AddProductCommandHandlerTest {
	
	AddProductCommand addProductCommand;
	AddProductCommandHandler addProductCommandHandler;
	ReservationRepository reservationRepository;
	ProductRepository productRepository;
    SuggestionService suggestionService;
    ClientRepository clientRepository;
    SystemContext systemContext;
    Product product;
    Product equivalent;
    ClientData clientData;
    Client client;
    Reservation reservation;
	
	@Before
	public void init(){
		
		addProductCommand = mock(AddProductCommand.class);
		when(addProductCommand.getOrderId()).thenReturn(Id.generate());
		
	}

	@Test(expected = NullPointerException.class)
	public void test1(){
		addProductCommandHandler = new AddProductCommandHandler();
		addProductCommandHandler.handle(addProductCommand);
	}

}

