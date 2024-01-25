package br.com.fiap.lanchonete.adapters.driven.postgress;

import br.com.fiap.lanchonete.adapters.configuration.GatewayPayment;
import br.com.fiap.lanchonete.adapters.driven.data.PedidoData;
import br.com.fiap.lanchonete.adapters.driven.enums.Status;
import br.com.fiap.lanchonete.adapters.driven.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.adapters.driven.repository.PedidoRepository;
import br.com.fiap.lanchonete.adapters.driven.rest.merchantorder.MerchantOrderRestClient;
import br.com.fiap.lanchonete.adapters.driven.rest.merchantorder.dto.MerchantOrderResponse;
import br.com.fiap.lanchonete.adapters.driven.rest.order.OrderRestClient;
import br.com.fiap.lanchonete.adapters.driven.rest.order.dto.*;
import br.com.fiap.lanchonete.adapters.driven.rest.payments.PaymentsRestClient;
import br.com.fiap.lanchonete.adapters.driven.rest.payments.dto.PaymentsResponse;
import br.com.fiap.lanchonete.application.ports.output.PedidoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PedidoPersistencePostgreeAdapter implements PedidoOutputPort {

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
        return pedidoRepository.findAll().stream()
                .map(pedidoData-> modelMapper.map(pedidoData, Pedido.class))
                .toList();
    }

    @Override
    public List<Pedido> findByStatus(List<String> statuss) {
        List<Status> enums = statuss.stream().map(ss->Status.valueOf(ss)).collect(Collectors.toList());
        List<PedidoData> list = pedidoRepository.findByStatusIn(enums);
        if(!list.isEmpty()){
            Collections.sort(list, Comparator.comparing(PedidoData::getSteps).reversed()
                    .thenComparing(PedidoData::getCriacao));
            return list.stream()
                    .map(pedidoData-> modelMapper.map(pedidoData, Pedido.class))
                    .toList();
        }
        return null;
    }

    @Override
    public Pedido save(Pedido pedido) {
        PedidoData pedidoData = pedidoDataMapper.toData(pedido);
        pedidoData.setSteps(pedidoData.getStatus());
        return modelMapper.map(pedidoRepository.save(pedidoData), Pedido.class);
    }

    @Override
    public Pedido checkout(Pedido pedido) {
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(buildItem(pedido));
        Order order = Order.builder()
                .cashOut(CashOut.builder().amount(new BigDecimal(NumberUtils.INTEGER_ZERO)).build())
                .description(DESCRIPTION)
                .externalReference(pedido.getId().toString())
                .items(itens)
                .notificationUrl(gatewayPayment.getNotification())
                .title(TITLE)
                .totalAmount(pedido.getPreco())
                .build();
        QuickResponse quickResponse = orderRestClient.post(order, pedido.getCollector(), pedido.getPos(), gatewayPayment.getToken());
        pedido.setQrData(quickResponse.getQrData());
        PedidoData pedidoData = modelMapper.map(pedido, PedidoData.class);
        pedidoData.setSteps(pedidoData.getStatus());
        pedidoRepository.save(pedidoData);
        return pedido;
    }

    @Override
    public Pedido confirm(Pedido pedido) {
        MerchantOrderResponse merchantOrder = merchantOrderRestClient.get(pedido.getOrderId(), gatewayPayment.getToken());
        Long pedidoId = Optional.ofNullable(merchantOrder)
                .map(MerchantOrderResponse::getExternalReference)
                .map(Long::parseLong)
                .orElse(null);
        if(Objects.nonNull(pedidoId)){
            Pedido ped = pedidoRepository.findById(pedidoId).map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
            ped.setExternalReference(pedido.getOrderId());
            ped.setStatus(br.com.fiap.lanchonete.domain.vo.Status.CONFIRMADO);
            PedidoData pedidoData = modelMapper.map(ped, PedidoData.class);
            pedidoData.setSteps(pedidoData.getStatus());
            return modelMapper.map(pedidoRepository.save(pedidoData), Pedido.class);
        }
        return null;
    }

    @Override
    public Pedido pay(Pedido pedido) {
        PaymentsResponse paymentOrder = paymentsRestClient.get(pedido.getPaymentId(), gatewayPayment.getToken());
        Long pedidoId = Optional.ofNullable(paymentOrder)
                .map(PaymentsResponse::getExternalReference)
                .map(Long::parseLong)
                .orElse(null);
        if(Objects.nonNull(pedidoId)){
            Pedido ped = pedidoRepository.findById(pedidoId).map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
            ped.setPaymentId(pedido.getPaymentId());
            if("approved".equals(paymentOrder.getStatus())) {
                ped.setStatus(br.com.fiap.lanchonete.domain.vo.Status.RECEBIDO);
                ped.setStatusPagamento(br.com.fiap.lanchonete.domain.vo.Status.PAGO);
            }else{
                ped.setStatusPagamento(br.com.fiap.lanchonete.domain.vo.Status.REJEITADO);
            }
            PedidoData pedidoData = modelMapper.map(ped, PedidoData.class);
            pedidoData.setSteps(pedidoData.getStatus());
            return modelMapper.map(pedidoRepository.save(pedidoData), Pedido.class);
        }
        return null;
    }

    private Item buildItem(Pedido pedido) {
        return Item.builder()
                .skuNumber(gatewayPayment.getSku())
                .category(CATEGORY)
                .title(TITLE)
                .description(DESCRIPTION)
                .unitPrice(pedido.getPreco())
                .quantity(NumberUtils.INTEGER_ONE)
                .unitMeasure(UNIT)
                .totalAmount(pedido.getPreco())
                .build();
    }

    private static final String DESCRIPTION = "This is the Point Mini";
    private static final String TITLE = "Point Mini";
    private static final String CATEGORY = "marketplace";
    private static final String UNIT = "unit";
}
