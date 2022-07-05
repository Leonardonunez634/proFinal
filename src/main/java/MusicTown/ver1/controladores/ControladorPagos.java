package MusicTown.ver1.controladores;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Payer;

import MusicTown.ver1.modelos.PagoMPModel;
//
@Controller
public class ControladorPagos {
	
	private final static String ACCES_TOKEN_MP = "TEST-3356654149714766-061319-8aefc3cf3e8049c5ef121fd583a152cf-165700243";
	
	@PostMapping(value = "/pagos/process_payment")

	public String procesarPago(@ModelAttribute PagoMPModel pagoMPModel) {
		// Ver de sacar idAtencion, queda ademtro de la solicitud de pagoMPModel, pero
		// llega concatenado dos veces con coma.
		try {
			/*
			 * PagoModel pagoModel = pagoService.setPago(pagoMPModel);
			 * pagoService.guardar(pagoModel);
			 */

			MercadoPago.SDK.setAccessToken(ACCES_TOKEN_MP);
			Double total= pagoMPModel.getTransactionAmount();
			pagoMPModel.setTransactionAmount(total);

			Payment payment = new Payment();
			//TODO:aca esta tirando error
			payment.setTransactionAmount(Float.valueOf(pagoMPModel.getTransactionAmount().toString()))
					.setToken(pagoMPModel.getToken()).setDescription(pagoMPModel.getDescription())//que es descripción?
					.setInstallments(pagoMPModel.getInstallments())
					.setPaymentMethodId(pagoMPModel.getPaymentMethodId());

			Identification identification = new Identification();
			identification.setType(pagoMPModel.getDocType()).setNumber(pagoMPModel.getDocNumber());

			Payer payer = new Payer();
			payer.setEmail(pagoMPModel.getEmail()).setIdentification(identification);

			payment.setPayer(payer);
			payment.save();
			System.out.println("Pago ID: " + payment.getId() + " COLLECTOR-ID : " + payment.getCollectorId()
					+ " OPERATION TYPE : " + payment.getOperationType() + " STATUS : " + payment.getStatus()
					+ " STATUS DETAIL : " + payment.getStatusDetail());
			Boolean isAdministrador = false;

		} catch (MPConfException e) {
			e.printStackTrace();

		} catch (MPException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "index";

	}
	@GetMapping("/pagos/formulario")
	public ModelAndView pago() {
		ModelAndView modelo = null;
		modelo = new ModelAndView("/auth/Pagos");
		modelo.addObject("pagoMPModel",new PagoMPModel());
		 
		return modelo;
	}

//	
//
//	// SDK de Mercado Pago
//	
//	// Agrega credenciales
//	MercadoPagoConfig.setAccessToken("PROD_ACCESS_TOKEN");
//
//
//	// Crea un objeto de preferencia
//	PreferenceClient client = new PreferenceClient();
//
//	// Crea un ítem en la preferencia
//	List<PreferenceItemRequest> items = new ArrayList<>();
//	PreferenceItemRequest item =
//	   PreferenceItemRequest.builder()
//	       .title("Meu produto")
//	       .quantity(1)
//	       .unitPrice(new BigDecimal("100"))
//	       .build();
//	items.add(item);
//
//	PreferenceRequest request = PreferenceRequest.builder().items(items).build();
//
//	client.create(request);

}
