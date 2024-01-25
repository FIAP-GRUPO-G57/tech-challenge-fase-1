package br.com.fiap.lanchonete.infra.db.repositories.postgresql;

import br.com.fiap.lanchonete.data.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.data.protocols.db.pedido.CheckoutPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.ConfirmPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.FindAllPedidosRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.FindPedidoByStatusRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.PayPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.infra.adapters.MerchantOrderRestClient;
import br.com.fiap.lanchonete.infra.adapters.OrderRestClient;
import br.com.fiap.lanchonete.infra.adapters.PaymentsRestClient;
import br.com.fiap.lanchonete.infra.adapters.dto.merchantOrder.MerchantOrderResponse;
import br.com.fiap.lanchonete.infra.adapters.dto.order.CashOut;
import br.com.fiap.lanchonete.infra.adapters.dto.order.Item;
import br.com.fiap.lanchonete.infra.adapters.dto.order.Order;
import br.com.fiap.lanchonete.infra.adapters.dto.order.QuickResponse;
import br.com.fiap.lanchonete.infra.adapters.dto.payment.PaymentsResponse;
import br.com.fiap.lanchonete.infra.db.repositories.PedidoRepository;
import br.com.fiap.lanchonete.infra.db.schemas.PedidoSchema;
import br.com.fiap.lanchonete.main.configuration.GatewayPayment;

import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PedidoPostgresqlRepository
		implements GetPedidoRepository, FindAllPedidosRepository, FindPedidoByStatusRepository, SavePedidoRepository,
		CheckoutPedidoRepository, ConfirmPedidoRepository, PayPedidoRepository {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public PedidoDataMapper pedidoDataMapper;

	@Autowired
	public OrderRestClient orderRestClient;

	@Autowired
	public MerchantOrderRestClient merchantOrderRestClient;

	@Autowired
	public PaymentsRestClient paymentsRestClient;

	@Autowired
	private GatewayPayment gatewayPayment;

	@Override
	public Pedido get(Long id) {
		return pedidoRepository.findById(id).map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll().stream().map(pedidoData -> modelMapper.map(pedidoData, Pedido.class))
				.toList();
	}

	@Override
	public List<Pedido> findByStatus(List<String> statuss) {
		List<StatusEnum> enums = statuss.stream().map(ss -> StatusEnum.valueOf(ss)).collect(Collectors.toList());
		List<PedidoSchema> list = pedidoRepository.findByStatusIn(enums);
		if (!list.isEmpty()) {
			Collections.sort(list,
					Comparator.comparing(PedidoSchema::getSteps).reversed().thenComparing(PedidoSchema::getCriacao));
			return list.stream().map(pedidoSchema -> modelMapper.map(pedidoSchema, Pedido.class)).toList();
		}
		return null;
	}

	@Override
	public Pedido save(Pedido pedido) {
		PedidoSchema pedidoSchema = pedidoDataMapper.toData(pedido);
		pedidoSchema.setSteps(pedidoSchema.getStatus());
		return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
	}

	@Override
	public Pedido checkout(Pedido pedido) {
		ArrayList<Item> itens = new ArrayList<>();
		itens.add(buildItem(pedido));
		Order order = Order.builder()
				.cashOut(CashOut.builder().amount(new BigDecimal(NumberUtils.INTEGER_ZERO)).build())
				.description(DESCRIPTION).externalReference(pedido.getId().toString()).items(itens)
				.notificationUrl(gatewayPayment.getNotification()).title(TITLE).totalAmount(pedido.getPreco()).build();
		QuickResponse quickResponse = orderRestClient.post(order, pedido.getCollector(), pedido.getPos(),
				gatewayPayment.getToken());
		pedido.setQrData(quickResponse.getQrData());
		PedidoSchema pedidoSchema = modelMapper.map(pedido, PedidoSchema.class);
		pedidoSchema.setSteps(pedidoSchema.getStatus());
		pedidoRepository.save(pedidoSchema);
		return pedido;
	}

	@Override
	public Pedido confirm(Pedido pedido) {
		MerchantOrderResponse merchantOrder = merchantOrderRestClient.get(pedido.getOrderId(),
				gatewayPayment.getToken());
		Long pedidoId = Optional.ofNullable(merchantOrder).map(MerchantOrderResponse::getExternalReference)
				.map(Long::parseLong).orElse(null);
		if (Objects.nonNull(pedidoId)) {
			Pedido ped = pedidoRepository.findById(pedidoId)
					.map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
			ped.setExternalReference(pedido.getOrderId());
			ped.setStatus(StatusEnum.CONFIRMADO);
			PedidoSchema pedidoSchema = modelMapper.map(ped, PedidoSchema.class);
			pedidoSchema.setSteps(pedidoSchema.getStatus());
			return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
		}
		return null;
	}

	@Override
	public Pedido pay(Pedido pedido) {
		PaymentsResponse paymentOrder = paymentsRestClient.get(pedido.getPaymentId(), gatewayPayment.getToken());
		Long pedidoId = Optional.ofNullable(paymentOrder).map(PaymentsResponse::getExternalReference)
				.map(Long::parseLong).orElse(null);
		if (Objects.nonNull(pedidoId)) {
			Pedido ped = pedidoRepository.findById(pedidoId)
					.map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
			ped.setPaymentId(pedido.getPaymentId());
			if ("approved".equals(paymentOrder.getStatus())) {
				ped.setStatus(StatusEnum.RECEBIDO);
				ped.setStatusPagamento(StatusEnum.PAGO);
			} else {
				ped.setStatusPagamento(StatusEnum.REJEITADO);
			}
			PedidoSchema pedidoSchema = modelMapper.map(ped, PedidoSchema.class);
			pedidoSchema.setSteps(pedidoSchema.getStatus());
			return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
		}
		return null;
	}

	private Item buildItem(Pedido pedido) {
		return Item.builder().skuNumber(gatewayPayment.getSku()).category(CATEGORY).title(TITLE)
				.description(DESCRIPTION).unitPrice(pedido.getPreco()).quantity(NumberUtils.INTEGER_ONE)
				.unitMeasure(UNIT).totalAmount(pedido.getPreco()).build();
	}

	private static final String DESCRIPTION = "This is the Point Mini";
	private static final String TITLE = "Point Mini";
	private static final String CATEGORY = "marketplace";
	private static final String UNIT = "unit";
}
