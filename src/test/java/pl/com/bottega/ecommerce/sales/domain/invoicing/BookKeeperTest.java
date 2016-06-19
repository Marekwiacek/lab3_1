package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;


import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import static org.mockito.Mockito.*;

public class BookKeeperTest {
	
	ClientData clientData;
	InvoiceFactory invoiceFactory;
	InvoiceRequest invoiceRequest;
	BookKeeper bookKeeper;
	Invoice invoice;
	ProductData productData;
	
	@Before
	public void init(){
		bookKeeper = new BookKeeper(new InvoiceFactory());
	}

	@Test
	public void stateTest(){
		TaxPolicy taxPolicyMock = mock(TaxPolicy.class);
		ProductData productDataMock = mock(ProductData.class);
		
		clientData = new ClientData(new Id("1"), "test");
		invoiceRequest = new InvoiceRequest(clientData);
		Money money= new Money(11);
		Tax tax = new Tax(money, "opis");
		
		when(taxPolicyMock.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(tax);
		
		RequestItem requestItem = new RequestItem(productDataMock, 2, money);
		invoiceRequest.add(requestItem);
		
		invoice = bookKeeper.issuance(invoiceRequest, taxPolicyMock);
		
		assertThat(invoice.getItems().size(), equalTo(1));
		
	}
	
	@Test
	public void behaviourTest(){
		
		TaxPolicy taxPolicyMock = mock(TaxPolicy.class);
		ProductData productDataMock = mock(ProductData.class);
		
		clientData = new ClientData(new Id("1"), "test");
		invoiceRequest = new InvoiceRequest(clientData);
		Money money= new Money(11);
		Tax tax = new Tax(money, "opis");
		
		when(taxPolicyMock.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(tax);
		
		RequestItem requestItem = new RequestItem(productDataMock, 2, money);
		invoiceRequest.add(requestItem);
		invoiceRequest.add(requestItem);
		
		invoice = bookKeeper.issuance(invoiceRequest, taxPolicyMock);
		
		assertThat(invoice.getItems().size(), equalTo(2));
		verify(taxPolicyMock, times(2)).calculateTax(any(ProductType.class), any(Money.class));
	}

	@Test
	public void invoiceWithoutPositionReturnEmptyTest(){
		
		TaxPolicy taxPolicyMock = mock(TaxPolicy.class);
		
		clientData = new ClientData(new Id("1"), "test");
		invoiceRequest = new InvoiceRequest(clientData);
		Money money= new Money(11);
        invoiceFactory = Mockito.mock(InvoiceFactory.class);
        when(invoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
	
        Invoice invoice = bookKeeper.issuance(invoiceRequest,taxPolicyMock);

        Assert.assertThat(invoice.getItems().size(),equalTo(0));
	}

	@Test
	public void callInvoiceFactoryEvenWithoutRequestInlcudesPositionTest(){
		
		ProductData productDataMock = mock(ProductData.class);
		TaxPolicy taxPolicyMock = mock(TaxPolicy.class);

		Money money= new Money(11);
        invoiceFactory = Mockito.mock(InvoiceFactory.class);
		clientData = new ClientData(new Id("1"), "test"); 
        when(invoiceFactory.create(clientData)).thenReturn(new Invoice(Id.generate(), clientData));
        bookKeeper = new BookKeeper(invoiceFactory);
        productDataMock = new ProductData(Id.generate(),money,"Test", ProductType.STANDARD,new Date());
		RequestItem requestItem = new RequestItem(productDataMock, 2, money);
        when(taxPolicyMock.calculateTax(productDataMock.getType(),productDataMock.getPrice())).thenReturn(new Tax(money, ""));
		invoiceRequest = new InvoiceRequest(clientData);

	        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        Invoice invoice = bookKeeper.issuance(invoiceRequest,taxPolicyMock);

        Mockito.verify(invoiceFactory, Mockito.atLeastOnce()).create(clientData);
        
	}

	
	
}
