# Tech Challenge - Grupo

Bem-vindo ao desafio técnico do Grupo. Este projeto foi projetado para rodar na AWS usando Amazon API Gateway e Amazon Elastic Kubernetes Service (EKS).

## Arquitetura

A aplicação é hospedada no EKS, um serviço gerenciado do Kubernetes fornecido pela AWS. O API Gateway atua como o ponto de entrada para a aplicação, direcionando as solicitações recebidas para os serviços apropriados dentro do cluster EKS.

![Arquitetura do Projeto](/img/arquitetura.drawio.png)

## Video Arquitetura

https://youtu.be/oop_8-O3Iws


## Executando a Aplicação no EKS

Para implantar a aplicação no EKS, você precisa aplicar o arquivo de configuração do Kubernetes YAML. Siga estas etapas:

1. Certifique-se de ter o `kubectl` instalado e configurado para interagir com seu cluster EKS. Você pode fazer isso instalando o AWS CLI e EKS CLI (`eksctl`), e executando `aws eks update-kubeconfig --region <região> --name <nome-do-cluster>`.

2. Aplique o arquivo de configuração do Kubernetes:

Secret postgress:

```bash
kubectl apply -f /k8s/secret-postgress.yaml

```

Secret payment:

```bash
kubectl apply -f /k8s/secret-payment.yaml

```

Service postgress:

```bash
kubectl apply -f /k8s/service-postgress.yaml

```

Statefulset postgress:

```bash
kubectl apply -f /k8s/statefulset-postgress.yaml

```


Service APP:

```bash
kubectl apply -f /k8s/service-app.yaml

```

Deployment APP:

```bash
kubectl apply -f /k8s/deployment-app.yaml

```

HPA:

```bash
kubectl apply -f /k8s/hpa.yaml

```

## Executando a Aplicação Localmente com Minikube

Para implantar a aplicação localmente usando Minikube, você precisa aplicar os arquivos de configuração do Kubernetes YAML. Siga estas etapas:

1. Certifique-se de ter o `minikube` e o `kubectl` instalados na sua máquina.

2. Inicie o Minikube:

```bash
minikube start
```
3. Aplique o arquivo de configuração do Kubernetes:

Secret postgress:

```bash
kubectl apply -f /k8s/secret-postgress.yaml

```

Secret payment:

```bash
kubectl apply -f /k8s/secret-payment.yaml

```

Service postgress:

```bash
kubectl apply -f /k8s/service-postgress.yaml

```

Statefulset postgress:

```bash
kubectl apply -f /k8s/statefulset-postgress.yaml

```


Service APP:

```bash
kubectl apply -f /k8s/service-app.yaml

```

Deployment APP:

```bash
kubectl apply -f /k8s/deployment-app.yaml

```


HPA:

```bash
kubectl apply -f /k8s/hpa.yaml

```

## Executando o Teste de Stress com K6

Para executar o teste de stress localmente usando K6, siga estas etapas:

1. Certifique-se de ter o K6 instalado na sua máquina. Se não tiver, você pode instalá-lo seguindo as instruções na [página de instalação do K6](https://k6.io/docs/getting-started/installation/).

2. Inicie a aplicação localmente. Certifique-se de que a aplicação está rodando na porta 8080.

3. Execute o script de teste de stress com o comando `k6 run` seguido do caminho para o seu script. No seu caso, o comando seria:

```bash
k6 run /k6/script/load-test-k6.js
```
## Validando o Horizontal Pod Autoscaler (HPA)

Durante ou após a execução do teste de stress, você pode querer verificar se o HPA está funcionando corretamente. Para fazer isso, siga estas etapas:

1. Certifique-se de que o `kubectl` está instalado e configurado para interagir com seu cluster Kubernetes.

2. Execute o seguinte comando para obter informações sobre o HPA:

```bash
kubectl get hpa
```
3. Se você quiser ver as mudanças em tempo real, pode usar o comando watch:

```bash
watch kubectl get hpa
```


## Executando Localmente com Docker

Para construir e executar os contêineres Docker, use os seguintes comandos:

```bash
docker-compose build
docker-compose up -d
```
## Swagger UI

Para acessar o Swagger UI, navegue até:

http://localhost:8080/swagger-ui/index.html

## Coleção Postman

Para importar a coleção do Postman, use o arquivo `clientes - Fase 2.postman_collection.json`.



